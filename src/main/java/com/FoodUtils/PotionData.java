package com.FoodUtils;

import com.FoodUtils.PotionRules.PotionRule;
import com.google.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.runelite.api.Client;

import net.runelite.client.plugins.itemstats.Effect;
import net.runelite.client.plugins.itemstats.ItemStatChangesService;
import net.runelite.client.plugins.itemstats.stats.Stat;
import net.runelite.client.plugins.itemstats.StatChange;
import net.runelite.client.plugins.itemstats.StatsChanges;
import net.runelite.api.Skill;

@Singleton
public class PotionData {

    public static final class PotionInfo
    {
        public final int baseBoost;
        public final int effectiveBoost;
        // True for potions that restore stats gradually over time rather than instantly
        public final boolean isGradual;

        private PotionInfo(int baseBoost, int effectiveBoost, boolean isGradual)
        {
            this.baseBoost = baseBoost;
            this.effectiveBoost = effectiveBoost;
            this.isGradual = isGradual;
        }
    }
    private final ItemStatChangesService itemStatChangesService;
    private final Client client;
    private final PotionRuleData potionRuleData;

    @Inject
    public PotionData(PotionRuleData potionRuleData, ItemStatChangesService itemStatChangesService, Client client)
    {
        this.potionRuleData = potionRuleData;
        this.itemStatChangesService = itemStatChangesService;
        this.client = client;
    }

    // Items with mechanics too complex or unique to display correctly — blocked to avoid misleading values
    private static final Set<Integer> BLOCKED_ITEMS = new HashSet<>(Arrays.asList(
            27335, 27337,               // Blessed crystal scarab (gradual prayer restore over time)
            27202, 27205, 27208, 27211, // Menaphite remedy (gradual combat stat restore over 5 minutes)
            32344                       // Bluefin tuna (food item, prayer restore should not show on potion overlay)
    ));

    public PotionInfo calculate(int itemId)
    {
        if (BLOCKED_ITEMS.contains(itemId)) {
            return null;
        }
        PotionRule rule = potionRuleData.getRuleForItemId(itemId);
                if(rule !=null){
                    return calculateFromRule(rule);
                }
        Effect effect = itemStatChangesService.getItemStatChanges(itemId);
        if(effect == null)
        {
            return null;
        }

        StatsChanges statsChanges = effect.calculate(client);
        if (statsChanges == null || statsChanges.getStatChanges() == null)
        {
            return null;
        }

        int bestBase = 0;
        int bestEffective = 0;
        for (StatChange statChange : statsChanges.getStatChanges())
        {
            if (statChange == null)
            {
                continue;
            }

            int base = statChange.getTheoretical(); // max boost
            if (base <= 0)
            {
                continue; // ignore non-boost effects
            }

            // Convert RuneLite Stat → Skill (needed for levels)
            Skill skill = mapStatToSkill(statChange.getStat());
            if (skill == null)
            {
                continue; // ignore stats we don't care about
            }

            // Get player levels
            int realLevel = client.getRealSkillLevel(skill);
            int currentLevel = client.getBoostedSkillLevel(skill);

            // Calculate how much we can actually gain
            int allowedMax = realLevel + base;
            int missing = allowedMax - currentLevel;
            int effective = Math.min(base, Math.max(0, missing));

            // Track the highest values across all stats
            if(base > bestBase)
            {
                bestBase = base;
                bestEffective = effective;
            }
        }

        // If nothing useful was found, return null
        if (bestBase <= 0)
        {
            return null;
        }

        return new PotionInfo(bestBase, bestEffective, false);
    }

    private PotionInfo calculateFromRule(PotionRule rule)
    {
        int realLevel = client.getRealSkillLevel(Skill.PRAYER);
        int currentLevel = client.getBoostedSkillLevel(Skill.PRAYER);
        int base = rule.baseRestoreAmount(realLevel);
        int allowedMax = rule.allowedMaxStat(realLevel);
        int effective = Math.min(base, Math.max(0, allowedMax - currentLevel));
        return new PotionInfo(base, effective, true);
    }

    private Skill mapStatToSkill(Stat stat)
    {
        if (stat == null)
        {
            return null;
        }

        switch (stat.getName())
        {
            case "Attack":
                return Skill.ATTACK;

            case "Strength":
                return Skill.STRENGTH;

            case "Defence":
                return Skill.DEFENCE;

            case "Ranged":
                return Skill.RANGED;

            case "Magic":
                return Skill.MAGIC;

            case "Hitpoints":
                return Skill.HITPOINTS;

            case "Prayer":
                return Skill.PRAYER;

            case "Agility":
                return Skill.AGILITY;

            default:
                return null; // ignore unsupported stats
        }
    }
}
