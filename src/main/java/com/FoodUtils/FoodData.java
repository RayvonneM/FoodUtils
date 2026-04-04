package com.FoodUtils;

import com.google.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import com.FoodUtils.FoodHealRules.*;
import net.runelite.api.Client;
import net.runelite.client.plugins.itemstats.Effect;
import net.runelite.client.plugins.itemstats.ItemStatChangesService;
import net.runelite.client.plugins.itemstats.StatChange;
import net.runelite.client.plugins.itemstats.StatsChanges;
import net.runelite.client.plugins.itemstats.stats.Stats;

@Singleton
public final class FoodData {

    public static final class FoodInfo {
        public final int allowedMaxHp;
        public final int baseHeal;
        public final int effectiveHeal;
        public final int missingToAllowedMax;
        public final int immediateHeal;
        public final int delayedHeal;

        private FoodInfo(int allowedMaxHp, int baseHeal, int effectiveHeal, int missingToAllowedMax, int immediateHeal, int delayedHeal) {
            this.allowedMaxHp = allowedMaxHp;
            this.baseHeal = baseHeal;
            this.effectiveHeal = effectiveHeal;
            this.missingToAllowedMax = missingToAllowedMax;
            this.immediateHeal = immediateHeal;
            this.delayedHeal = delayedHeal;
        }
    }

    private final FoodRuleData foodRuleData;
    private final ItemStatChangesService itemStatChangesService;
    private final Client client;

    @Inject
    public FoodData(FoodRuleData foodRuleData, ItemStatChangesService itemStatChangesService, Client client) {

        this.foodRuleData = foodRuleData;
        this.itemStatChangesService = itemStatChangesService;
        this.client = client;
    }
    // TOA items with mechanics too complex or unique to display correctly — blocked to avoid misleading values
    private static final Set<Integer> BLOCKED_ITEMS = new HashSet<>(Arrays.asList(
            27325, 27323, // Silk dressing (gradual heal over time)
            27351,        // Honey locust (overheal + prayer restore)
            27347, 27349, // Ambrosia (full restore + overheal)
            27315, 27317, 27319, 27321 // Nectar (heals but drains combat stats)
    ));

    // Checks for a manual rule first. Falls back to RuneLite's ItemStatChangesService for standard foods.
    public FoodInfo calculate(int itemId, int baseHp, int currentHP) {
        if (BLOCKED_ITEMS.contains(itemId)) {
            return null;
        }
        HealRule rule = foodRuleData.getRuleForItemId(itemId);
        if (rule != null) {
            return calculateFromRule(rule, baseHp, currentHP);
        }
        return calculateFromItemStats(itemId, baseHp, currentHP);
    }
        // Food with special effects (Overheal, delayed heal) must be delegated to a heal rule and are handled here.
        private FoodInfo calculateFromRule(HealRule rule, int baseHp, int currentHP){
            int allowedMax = rule.allowedMaxHp(baseHp);
            int itemBaseHeal = Math.max(0, rule.itemBaseHealAmount(baseHp));

            if (itemBaseHeal <= 0) {
                return null;
            }

            int missingToAllowedMax = Math.max(0, allowedMax - currentHP);
            int effectiveHeal = Math.min(itemBaseHeal, missingToAllowedMax);

            // Extract immediate/delayed portions if this is a delayed heal rule,
            // otherwise treat the full heal as immediate with no delayed portion.
            int immediateHeal;
            int delayedHeal;
            if (rule instanceof DelayedHealRule)
            {
                DelayedHealRule delayedRule = (DelayedHealRule) rule;
                immediateHeal = delayedRule.immediateHeal;
                delayedHeal = delayedRule.delayedHeal;
            }
            else
            {
                immediateHeal = itemBaseHeal;
                delayedHeal = 0;
            }

            return new FoodInfo(allowedMax, itemBaseHeal, effectiveHeal, missingToAllowedMax, immediateHeal, delayedHeal);
        }
    private FoodInfo calculateFromItemStats(int itemId, int baseHp, int currentHP)
    {
        Effect effect = itemStatChangesService.getItemStatChanges(itemId);
        if (effect == null)
        {
            return null;
        }
        // RuneLite registers these as multiple HITPOINTS entries, so we sum them all for the true total.
        int healTotal = 0;
        int allowedMax = baseHp;
        int missingToAllowedMax = Math.max(0, allowedMax - currentHP);
        int immediateHeal =0;
        int delayedHeal =0;
        int hitpointEntryCount = 0;

        StatsChanges statsChanges = effect.calculate(client);
        if (statsChanges == null || statsChanges.getStatChanges() == null)
        {
            return null;
        }

        for (StatChange statChange : statsChanges.getStatChanges())
        {
            if (statChange == null)
            {
                continue;
            }

            if (Stats.HITPOINTS.equals(statChange.getStat()))
            {

                int entryHeal = Math.max(0, statChange.getTheoretical());
                if (entryHeal <= 0)
                {
                    continue;
                }
                healTotal = entryHeal + healTotal;
                if(hitpointEntryCount ==0) immediateHeal = entryHeal;
                else delayedHeal = entryHeal;
                hitpointEntryCount++;

            }
        }
        // No HITPOINTS entries found — not a food item.
        if(healTotal == 0) return null;
        int effectiveHeal = Math.min(healTotal, missingToAllowedMax);
        return new FoodInfo(allowedMax, healTotal, effectiveHeal, missingToAllowedMax, immediateHeal, delayedHeal);
    }
}