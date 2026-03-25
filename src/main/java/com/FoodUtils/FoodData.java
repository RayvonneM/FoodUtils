package com.FoodUtils;

import com.google.inject.Inject;
import javax.inject.Singleton;
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

        private FoodInfo(int allowedMaxHp, int baseHeal, int effectiveHeal, int missingToAllowedMax) {
            this.allowedMaxHp = allowedMaxHp;
            this.baseHeal = baseHeal;
            this.effectiveHeal = effectiveHeal;
            this.missingToAllowedMax = missingToAllowedMax;
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

    public FoodInfo calculate(int itemId, int baseHp, int currentHP) {
        HealRule rule = foodRuleData.getRuleForItemId(itemId);
        if (rule != null) {
            return calculateFromRule(rule, baseHp, currentHP);
        }
        return calculateFromItemStats(itemId, baseHp, currentHP);
    }
        private FoodInfo calculateFromRule(HealRule rule,int baseHp, int currentHP){
            int allowedMax = rule.allowedMaxHp(baseHp);
            int itemBaseHeal = Math.max(0, rule.itemBaseHealAmount(baseHp));

            if (itemBaseHeal <= 0) {
                return null;
            }

            int missingToAllowedMax = Math.max(0, allowedMax - currentHP);
            int effectiveHeal = Math.min(itemBaseHeal, missingToAllowedMax);

            return new FoodInfo(allowedMax, itemBaseHeal, effectiveHeal, missingToAllowedMax);
        }
    private FoodInfo calculateFromItemStats(int itemId, int baseHp, int currentHP)
    {
        Effect effect = itemStatChangesService.getItemStatChanges(itemId);
        if (effect == null)
        {
            return null;
        }

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
                int itemBaseHeal = Math.max(0, statChange.getTheoretical());
                if (itemBaseHeal <= 0)
                {
                    return null;
                }

                int allowedMax = baseHp;
                int missingToAllowedMax = Math.max(0, allowedMax - currentHP);
                int effectiveHeal = Math.min(itemBaseHeal, missingToAllowedMax);

                return new FoodInfo(allowedMax, itemBaseHeal, effectiveHeal, missingToAllowedMax);
            }
        }

        return null;
    }
}