package com.FoodUtils;

import com.FoodUtils.FoodHealRules.*;
import net.runelite.api.gameval.ItemID;
import com.google.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class FoodRuleData
{
    private final Map<Integer, HealRule> RULES = new HashMap<>();
    @Inject
    public FoodRuleData()
    {
        populateRuleMap();


    }
    public HealRule getRuleForItemId(int itemId){

        return RULES.get(itemId);
    }

    private void addRule(HealRule rule, int... itemIds)
    {
        for (int id : itemIds)
        {
            RULES.put(id, rule);
        }
    }

    private void addHeal(int itemHealAmount, int... itemIds)
    {
        addRule(new FixedItemHealRule(itemHealAmount), itemIds);
    }

    private void addPercentCeil(double pct, int flat, int... itemIds)
    {
        addRule(new PercentageItemHealRule(pct,flat), itemIds);
    }

    private void addBrewStyle(double pct, int flat, int... itemIds)
    {
        addRule(new BrewHealRule(pct,flat), itemIds);
    }

    private void addAnglerfish(int... itemIds)
    {
        addRule(new AnglerFishHealRule(), itemIds);
    }

    private void populateRuleMap()
    {


        //Percent Heal
        addPercentCeil(0.06, 0,
                ItemID.STRAWBERRY);

        //Sarabrew style

        addBrewStyle(0.15, 2,
                ItemID._1DOSEPOTIONOFSARADOMIN,
                ItemID._2DOSEPOTIONOFSARADOMIN,
                ItemID._3DOSEPOTIONOFSARADOMIN,
                ItemID._4DOSEPOTIONOFSARADOMIN,
                ItemID.TOA_SUPPLY_HEAL_1,
                ItemID.TOA_SUPPLY_HEAL_2,
                ItemID.TOA_SUPPLY_HEAL_3,
                ItemID.TOA_SUPPLY_HEAL_4
                );
        //Angler
        addAnglerfish(
                ItemID.ANGLERFISH,
                ItemID.BLIGHTED_ANGLERFISH
        );
    }
}