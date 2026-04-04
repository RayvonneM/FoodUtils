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

    private void addPercentFloor(double pct, int flat, int... itemIds)
    {
        addRule(new PercentageFloorItemHealRule(pct, flat), itemIds);
    }

    private void addBrewStyle(double pct, int flat, int... itemIds)
    {
        addRule(new BrewHealRule(pct,flat), itemIds);
    }

    private void addAnglerfish(int... itemIds)
    {
        addRule(new AnglerFishHealRule(), itemIds);
    }

    private void addHaddock(int... itemIds)
    {
        addRule(new HaddockHealRule(), itemIds);
    }

    private void addDelayedHeal(int immediateHeal, int delayedHeal, int... itemIds)
    {
        addRule(new DelayedHealRule(immediateHeal, delayedHeal), itemIds);
    }


    private void populateRuleMap()
    {


        // Strawberry and basket of strawberries: 1 + floor(6% of base HP), no overheal
        addPercentFloor(0.06, 1,
                ItemID.STRAWBERRY,
                5398, // Basket of strawberries (5)
                5400, // Basket of strawberries (4)
                5402, // Basket of strawberries (3)
                5404, // Basket of strawberries (2)
                5406  // Basket of strawberries (1)
        );

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

        //Haddock (18 HP, overheal scales with level up to +10)
        addHaddock(32320);

        //Armadyl brew (floor(HP * 10%) + 2, overheal)
        addBrewStyle(0.10, 2,
                31650, // 4-dose
                31653, // 3-dose
                31656, // 2-dose
                31659  // 1-dose
        );

        //Guthix rest (5 HP flat, +5 overheal — uses brew formula with 0% pct)
        addBrewStyle(0, 5,
                4417, // 4-dose
                4419, // 3-dose
                4421, // 2-dose
                4423  // 1-dose
        );

        //Add Hunter Meats (delayed)
        addDelayedHeal(4, 4, ItemID.WILDKEBBIT_COOKED);
        addDelayedHeal(6, 5, ItemID.LARUPIA_COOKED);
        addDelayedHeal(7, 5, ItemID.BARBKEBBIT_COOKED);
        addDelayedHeal(8, 6, ItemID.GRAAHK_COOKED);
        addDelayedHeal(9, 8, ItemID.KYATT_COOKED);
        addDelayedHeal(11, 8, ItemID.FENNECFOX_COOKED);
        addDelayedHeal(13, 10, ItemID.DASHINGKEBBIT_COOKED);
        addDelayedHeal(12, 9, ItemID.ANTELOPESUN_COOKED);
        addDelayedHeal(14, 12, ItemID.ANTELOPEMOON_COOKED);

    }
}