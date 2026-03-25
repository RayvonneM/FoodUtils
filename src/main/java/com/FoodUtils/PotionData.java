package com.FoodUtils;

import com.google.inject.Inject;
import javax.inject.Singleton;

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

        private PotionInfo(int baseBoost, int effectiveBoost)
        {
            this.baseBoost = baseBoost;
            this.effectiveBoost = effectiveBoost;
        }
    }
    private final ItemStatChangesService itemStatChangesService;
    private final Client client;

    @Inject
    public PotionData(ItemStatChangesService itemStatChangesService, Client client)
    {
        this.itemStatChangesService = itemStatChangesService;
        this.client = client;
    }

    public PotionInfo calculate(int itemId)
    {
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

        return new PotionInfo(bestBase, bestEffective);
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
