package com.FoodUtils;


import java.util.HashMap;
import java.util.Map;
import net.runelite.api.ItemID;


public final class FoodData {
    private interface  HealRule
    {
        int baseHeal(int baseHP);
        default int allowedMaxHP(int baseHP)
        {
            return baseHP;
        }
    }
    public static final class FoodInfo
    {
        public final int allowedMaxHp;
        public final int baseHeal;
        public final int effectiveHeal;
        public final int missingAllowedtoMax;
        private FoodInfo(int allowedMaxHp, int baseHeal, int effectiveHeal, int missingAllowedtoMax)
        {
            this.allowedMaxHp = allowedMaxHp;
            this.baseHeal = baseHeal;
            this.effectiveHeal = effectiveHeal;
            this.missingAllowedtoMax = missingAllowedtoMax;
        }
    }
    private static final Map<Integer, HealRule> RULES = new HashMap<>();

    static
    {
        //Fixed Heal
        addHeal(-5, net.runelite.api.gameval.ItemID.TBWT_POORLY_COOKED_KARAMBWAN);
        addHeal(1, net.runelite.api.gameval.ItemID.POTATO, net.runelite.api.gameval.ItemID.ONION, net.runelite.api.gameval.ItemID.CABBAGE, net.runelite.api.gameval.ItemID.POT_OF_CREAM, net.runelite.api.gameval.ItemID.BOWL_ONION, net.runelite.api.gameval.ItemID.ANCHOVIES, net.runelite.api.gameval.ItemID.BOWL_NETTLEWATER, net.runelite.api.gameval.ItemID.EQUA_LEAVES,
                net.runelite.api.gameval.ItemID.SWAN_MONKFISH /* Cooked */, net.runelite.api.gameval.ItemID.COOKED_CHICKEN_UNDEAD /* Undead */, net.runelite.api.gameval.ItemID.COOKED_MEAT_UNDEAD /* Undead */);
        addHeal(2, net.runelite.api.gameval.ItemID.TOMATO, net.runelite.api.gameval.ItemID.BOWL_TOMATO, net.runelite.api.gameval.ItemID.BANANA, net.runelite.api.gameval.ItemID.TBWT_SLICED_BANANA, net.runelite.api.gameval.ItemID.ORANGE, net.runelite.api.gameval.ItemID.ORANGE_SLICES, net.runelite.api.gameval.ItemID.ORANGE_CHUNKS,
                net.runelite.api.gameval.ItemID.PINEAPPLE_RING, net.runelite.api.gameval.ItemID.PINEAPPLE_CHUNKS, net.runelite.api.gameval.ItemID.BOWL_CHILLI, net.runelite.api.gameval.ItemID.CHEESE, net.runelite.api.gameval.ItemID.SPINACH_ROLL, net.runelite.api.gameval.ItemID.LEMON, net.runelite.api.gameval.ItemID.LEMON_CHUNKS, net.runelite.api.gameval.ItemID.LEMON_SLICES,
                net.runelite.api.gameval.ItemID.LIME, net.runelite.api.gameval.ItemID.LIME_CHUNKS, net.runelite.api.gameval.ItemID.LIME_SLICES, net.runelite.api.gameval.ItemID.DWELLBERRIES, net.runelite.api.gameval.ItemID.KING_WORM, net.runelite.api.gameval.ItemID.BOWL_CARNE, net.runelite.api.gameval.ItemID.BOWL_SPICYTOMATO, net.runelite.api.gameval.ItemID.MDAUGHTER_WHITE_PEARL_FRUIT);
        addHeal(3, net.runelite.api.gameval.ItemID.SHRIMP, net.runelite.api.gameval.ItemID.COOKED_MEAT, net.runelite.api.gameval.ItemID.COOKED_CHICKEN, net.runelite.api.gameval.ItemID.BRUT_ROE, net.runelite.api.gameval.ItemID.CHOCOLATE_BAR, net.runelite.api.gameval.ItemID.COOKED_UGTHANKI_MEAT, net.runelite.api.gameval.ItemID.TOADS_LEGS, net.runelite.api.gameval.ItemID.BOWL_ONIONTOMATO,
                net.runelite.api.gameval.ItemID.BOWL_SPICYMEAT, net.runelite.api.gameval.ItemID.OS_BIRTHDAY_CAKE_SLICE, net.runelite.api.gameval.ItemID.LOCUST_MEAT, net.runelite.api.gameval.ItemID.BRUTAL_RELICYMS_BALM1, net.runelite.api.gameval.ItemID.BRUTAL_RELICYMS_BALM2, net.runelite.api.gameval.ItemID.BRUTAL_1DOSEANTIPOISON,
                net.runelite.api.gameval.ItemID.BRUTAL_2DOSEANTIPOISON);
        addHeal(4, net.runelite.api.gameval.ItemID.SARDINE, net.runelite.api.gameval.ItemID.CAKE, net.runelite.api.gameval.ItemID.PARTIAL_CAKE, net.runelite.api.gameval.ItemID.CAKE_SLICE, net.runelite.api.gameval.ItemID.CHOCOLATY_MILK, net.runelite.api.gameval.ItemID.POTATO_BAKED, net.runelite.api.gameval.ItemID.EDIBLE_SEAWEED, net.runelite.api.gameval.ItemID.MOONLIGHT_MEAD,
                net.runelite.api.gameval.ItemID.KEG_MOONLIGHT_MEAD_4, net.runelite.api.gameval.ItemID.KEG_MOONLIGHT_MEAD_3, net.runelite.api.gameval.ItemID.KEG_MOONLIGHT_MEAD_2, net.runelite.api.gameval.ItemID.KEG_MOONLIGHT_MEAD_1, net.runelite.api.gameval.ItemID.MM_MONKEY_NUTS);
        addHeal(5, net.runelite.api.gameval.ItemID.BREAD, net.runelite.api.gameval.ItemID.HERRING, net.runelite.api.gameval.ItemID.CHOCOLATE_CAKE, net.runelite.api.gameval.ItemID.PARTIAL_CHOCOLATE_CAKE, net.runelite.api.gameval.ItemID.CHOCOLATE_SLICE, net.runelite.api.gameval.ItemID.COOKED_RABBIT, net.runelite.api.gameval.ItemID.BOWL_CHILLI_CARNE,
                net.runelite.api.gameval.ItemID.BOWL_MUSHROOM_FRIED, net.runelite.api.gameval.ItemID.BOWL_ONION_FRIED, net.runelite.api.gameval.ItemID.REDBERRY_PIE, net.runelite.api.gameval.ItemID.HALF_A_REDBERRY_PIE, net.runelite.api.gameval.ItemID.BRUT_CAVIAR, net.runelite.api.gameval.ItemID.RAIDS_FISH0_COOKED, net.runelite.api.gameval.ItemID.COOKED_MYSTERY_MEAT,
                net.runelite.api.gameval.ItemID.BOWL_EGG_SCRAMBLED, net.runelite.api.gameval.ItemID.MM_MONKEY_BAR, net.runelite.api.gameval.ItemID.HUNDRED_ILM_TCHIKI_MONKEY_NUTS, net.runelite.api.gameval.ItemID.HUNDRED_ILM_TCHIKI_MONKEY_NUT_PASTE, net.runelite.api.gameval.ItemID.HUNDRED_ILM_RED_BANANA, net.runelite.api.gameval.ItemID.HUNDRED_ILM_SLICED_RED_BANANA);
        addHeal(6, net.runelite.api.gameval.ItemID.MACKEREL, net.runelite.api.gameval.ItemID.MEAT_PIE, net.runelite.api.gameval.ItemID.HALF_A_MEAT_PIE, net.runelite.api.gameval.ItemID.RAIDS_BAT0_COOKED, net.runelite.api.gameval.ItemID.SPIT_ROASTED_BIRD_MEAT, net.runelite.api.gameval.ItemID.SQUARE_SANDWICH,
                net.runelite.api.gameval.ItemID.ROLL, net.runelite.api.gameval.ItemID.BAGUETTE, net.runelite.api.gameval.ItemID.TRIANGLE_SANDWICH, net.runelite.api.gameval.ItemID.GIANT_CARP, net.runelite.api.gameval.ItemID.MATURE_MOONLIGHT_MEAD, net.runelite.api.gameval.ItemID.KEG_MATURE_MOONLIGHT_MEAD_4, net.runelite.api.gameval.ItemID.KEG_MATURE_MOONLIGHT_MEAD_3, net.runelite.api.gameval.ItemID.KEG_MATURE_MOONLIGHT_MEAD_2,
                net.runelite.api.gameval.ItemID.KEG_MATURE_MOONLIGHT_MEAD_1, net.runelite.api.gameval.ItemID.BIM_STEAK_SANDWICH, net.runelite.api.gameval.ItemID.GIANT_FROG_LEGS, net.runelite.api.gameval.ItemID.BRUTAL_1DOSE1ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_2DOSE1ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_1DOSE2ANTIDRAGON,
                net.runelite.api.gameval.ItemID.BRUTAL_2DOSE2ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_1DOSE3ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_2DOSE3ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_1DOSE4ANTIDRAGON,
                net.runelite.api.gameval.ItemID.BRUTAL_2DOSE4ANTIDRAGON, net.runelite.api.gameval.ItemID.BRUTAL_1DOSE2ANTIPOISON, net.runelite.api.gameval.ItemID.BRUTAL_2DOSE2ANTIPOISON, net.runelite.api.gameval.ItemID.BRUTAL_ANTIDOTE_1, net.runelite.api.gameval.ItemID.BRUTAL_ANTIDOTE_2);
        addHeal(7, net.runelite.api.gameval.ItemID.ELID_CHOC_ICE, net.runelite.api.gameval.ItemID.TROUT, net.runelite.api.gameval.ItemID.COD, net.runelite.api.gameval.ItemID.PLAIN_PIZZA, net.runelite.api.gameval.ItemID.HALF_PLAIN_PIZZA, net.runelite.api.gameval.ItemID.APPLE_PIE, net.runelite.api.gameval.ItemID.HALF_AN_APPLE_PIE, net.runelite.api.gameval.ItemID.SPIT_ROASTED_RABBIT_MEAT,
                net.runelite.api.gameval.ItemID.PREMADE_CHOCCHIP_CRUNCHIES, net.runelite.api.gameval.ItemID.CHOCCHIP_CRUNCHIES, net.runelite.api.gameval.ItemID.PREMADE_SPICY_CRUNCHIES, net.runelite.api.gameval.ItemID.SPICY_CRUNCHIES);
        addHeal(8, net.runelite.api.gameval.ItemID.PIKE, net.runelite.api.gameval.ItemID.SPIT_ROASTED_BEAST_MEAT, net.runelite.api.gameval.ItemID.MEAT_PIZZA, net.runelite.api.gameval.ItemID.HALF_MEAT_PIZZA, net.runelite.api.gameval.ItemID.PREMADE_WORM_CRUNCHIES, net.runelite.api.gameval.ItemID.WORM_CRUNCHIES, net.runelite.api.gameval.ItemID.PREMADE_TOAD_CRUNCHIES,
                net.runelite.api.gameval.ItemID.TOAD_CRUNCHIES, net.runelite.api.gameval.ItemID.BOWL_EGG_TOMATO, net.runelite.api.gameval.ItemID.RAIDS_BAT1_COOKED, net.runelite.api.gameval.ItemID.PEACH, net.runelite.api.gameval.ItemID.RAIDS_FISH1_COOKED, net.runelite.api.gameval.ItemID.RED_CRAB_MEAT);
        addHeal(9, net.runelite.api.gameval.ItemID.PREMADE_PINEAPPLE_PUNCH, net.runelite.api.gameval.ItemID.PINEAPPLE_PUNCH, net.runelite.api.gameval.ItemID.PREMADE_FRUIT_BLAST, net.runelite.api.gameval.ItemID.FRUIT_BLAST, net.runelite.api.gameval.ItemID.SALMON, net.runelite.api.gameval.ItemID.ANCHOVIE_PIZZA,
                net.runelite.api.gameval.ItemID.HALF_ANCHOVIE_PIZZA);
        addHeal(10, net.runelite.api.gameval.ItemID.TUNA, net.runelite.api.gameval.ItemID.HUNDRED_PIRATE_GIANT_CRAB_MEAT_5, net.runelite.api.gameval.ItemID.BOWL_TUNA, net.runelite.api.gameval.ItemID.COOKED_CHOMPY, net.runelite.api.gameval.ItemID.PEST_FIELD_RATION, net.runelite.api.gameval.ItemID.DRAGONFRUIT,
                net.runelite.api.gameval.ItemID.DEADMAN_STARTER_TUNA /* Deadman Starter Pack */);
        addHeal(11, net.runelite.api.gameval.ItemID.HUNTING_FISH_SPECIAL, net.runelite.api.gameval.ItemID.STEW, net.runelite.api.gameval.ItemID.PINEAPPLE_PIZZA, net.runelite.api.gameval.ItemID.HALF_PINEAPPLE_PIZZA, net.runelite.api.gameval.ItemID.HUNDRED_PIRATE_FISHCAKE,
                net.runelite.api.gameval.ItemID.PREMADE_VEGETABLE_BATTA, net.runelite.api.gameval.ItemID.VEGETABLE_BATTA, net.runelite.api.gameval.ItemID.PREMADE_WORM_BATTA, net.runelite.api.gameval.ItemID.WORM_BATTA, net.runelite.api.gameval.ItemID.PREMADE_TOAD_BATTA, net.runelite.api.gameval.ItemID.TOAD_BATTA, net.runelite.api.gameval.ItemID.PREMADE_CHEESE_TOM_BATTA,
                net.runelite.api.gameval.ItemID.CHEESE_TOM_BATTA, net.runelite.api.gameval.ItemID.PREMADE_FRUIT_BATTA, net.runelite.api.gameval.ItemID.FRUIT_BATTA, net.runelite.api.gameval.ItemID.BOWL_MUSHROOM_ONION, net.runelite.api.gameval.ItemID.RAIDS_BAT2_COOKED, net.runelite.api.gameval.ItemID.LAVA_EEL, net.runelite.api.gameval.ItemID.RAIDS_FISH2_COOKED, net.runelite.api.gameval.ItemID.MM_BANANA_STEW);
        addHeal(12, net.runelite.api.gameval.ItemID.LOBSTER, net.runelite.api.gameval.ItemID.PREMADE_WORM_HOLE, net.runelite.api.gameval.ItemID.WORM_HOLE, net.runelite.api.gameval.ItemID.PREMADE_VEG_BALL, net.runelite.api.gameval.ItemID.VEG_BALL);
        addHeal(13, net.runelite.api.gameval.ItemID.BASS, net.runelite.api.gameval.ItemID.BOWL_TUNA_SWEETCORN);
        addHeal(14, net.runelite.api.gameval.ItemID.POTATO_BUTTER, net.runelite.api.gameval.ItemID.POTATO_CHILLI_CARNE, net.runelite.api.gameval.ItemID.SWORDFISH, net.runelite.api.gameval.ItemID.RAIDS_BAT3_COOKED, net.runelite.api.gameval.ItemID.PUMPKIN, net.runelite.api.gameval.ItemID.EASTER_EGG, net.runelite.api.gameval.ItemID.RAIDS_FISH3_COOKED,
                net.runelite.api.gameval.ItemID.COOKED_OOMLIE, net.runelite.api.gameval.ItemID.BLUE_CRAB_MEAT);
        addHeal(15, net.runelite.api.gameval.ItemID.PREMADE_TANGLED_TOADS_LEGS, net.runelite.api.gameval.ItemID.TANGLED_TOADS_LEGS, net.runelite.api.gameval.ItemID.PREMADE_CHOCOLATE_BOMB, net.runelite.api.gameval.ItemID.CHOCOLATE_BOMB, net.runelite.api.gameval.ItemID._100_JUBBLY_MEAT_COOKED, net.runelite.api.gameval.ItemID.SWORDTIP_SQUID);
        addHeal(16, net.runelite.api.gameval.ItemID.MONKFISH, net.runelite.api.gameval.ItemID.POTATO_CHEESE, net.runelite.api.gameval.ItemID.POTATO_EGG_TOMATO, net.runelite.api.gameval.ItemID.GAUNTLET_COMBO_FOOD, net.runelite.api.gameval.ItemID.GAUNTLET_COMBO_FOOD_HM);
        addHeal(17, net.runelite.api.gameval.ItemID.RAIDS_FISH4_COOKED, net.runelite.api.gameval.ItemID.RAIDS_BAT4_COOKED, net.runelite.api.gameval.ItemID.GIANT_KRILL, net.runelite.api.gameval.ItemID.JUMBO_SQUID);
        addHeal(18, net.runelite.api.gameval.ItemID.TBWT_COOKED_KARAMBWAN, net.runelite.api.gameval.ItemID.BLIGHTED_KARAMBWAN, net.runelite.api.gameval.ItemID.BR_TBWT_COOKED_KARAMBWAN /* LMS */);
        addHeal(19, net.runelite.api.gameval.ItemID.CURRY, net.runelite.api.gameval.ItemID.UGTHANKI_KEBAB_BAD, net.runelite.api.gameval.ItemID.UGTHANKI_KEBAB, net.runelite.api.gameval.ItemID.RAINBOW_CRAB_MEAT);
        addHeal(20, net.runelite.api.gameval.ItemID.POTATO_MUSHROOM_ONION, net.runelite.api.gameval.ItemID.SHARK, net.runelite.api.gameval.ItemID.RAIDS_FISH5_COOKED, net.runelite.api.gameval.ItemID.RAIDS_BAT5_COOKED, net.runelite.api.gameval.ItemID.HUNDRED_ILM_COOKED_STUFFED_SNAKE, net.runelite.api.gameval.ItemID.BR_SHARK /* LMS */, net.runelite.api.gameval.ItemID.GAUNTLET_FOOD, net.runelite.api.gameval.ItemID.EVENT_CORRUPTED_SHARK, net.runelite.api.gameval.ItemID.HALIBUT);
        addHeal(21, net.runelite.api.gameval.ItemID.SEATURTLE);
        addHeal(22, net.runelite.api.gameval.ItemID.MANTARAY, net.runelite.api.gameval.ItemID.BLIGHTED_MANTARAY, net.runelite.api.gameval.ItemID.DARK_CRAB, net.runelite.api.gameval.ItemID.POTATO_TUNA_SWEETCORN);
        addHeal(23, net.runelite.api.gameval.ItemID.RAIDS_FISH6_COOKED, net.runelite.api.gameval.ItemID.RAIDS_BAT6_COOKED);
        addHeal(24, net.runelite.api.gameval.ItemID.MARLIN);

        //Percent Heal
        addPercentCeil(0.06, 0, ItemID.STRAWBERRY);

        //Sarabrew style
        addBrewStyle(0.15, 2,
                ItemID.SARADOMIN_BREW4,
                ItemID.SARADOMIN_BREW3,
                ItemID.SARADOMIN_BREW2,
                ItemID.SARADOMIN_BREW1,
                ItemID.NECTAR_4,
                ItemID.NECTAR_3,
                ItemID.NECTAR_2,
                ItemID.NECTAR_1);
        //Angler
        addAnglerfish(
                ItemID.ANGLERFISH,
                ItemID.BLIGHTED_ANGLERFISH
        );

    }

    private FoodData() {}
    public static FoodInfo calculate(int itemId, int baseHP, int currentHP)
    {
        HealRule rule = RULES.get(itemId);
        if (rule == null)
        {
            return null;
        }

        int allowedMax = rule.allowedMaxHP(baseHP);
        int baseHeal = Math.max(0, rule.baseHeal(baseHP));

        if (baseHeal <= 0)
        {
            return null;
        }

        int missingToAllowedMax = Math.max(0, allowedMax - currentHP);
        int effectiveHeal = Math.min(baseHeal, missingToAllowedMax);

        return new FoodInfo(allowedMax, baseHeal, effectiveHeal, missingToAllowedMax);
    }

    private static void addHeal(int heal, int... itemIds)
    {
            addFixed(heal, itemIds);
    }
    private static void addRule(HealRule rule, int... itemIds)
    {
        for (int id : itemIds)
        {
            RULES.put(id, rule);
        }
    }

    private static void addFixed(int heal, int... itemIds)
    {
        addRule(baseHP -> heal, itemIds);
    }

    private static void addPercentCeil(double pct, int flat, int... itemIds)
    {
        addRule(baseHP -> (int) Math.ceil(baseHP * pct) + flat, itemIds);
    }

    private static void addPercentFloorPlus(double pct, int flat, int... itemIds)
    {
        addRule(baseHP -> (int) Math.floor(baseHP * pct) + flat, itemIds);
    }
    private static void addBrewStyle(double pct, int flat, int... itemIds)
    {
        addRule(new HealRule()
        {
            @Override
            public int baseHeal(int baseHP)
            {
                return (int) Math.floor(baseHP * pct) + flat;
            }

            @Override
            public int allowedMaxHP(int baseHP)
            {
                return baseHP + baseHeal(baseHP);
            }
        }, itemIds);
    }
    private static void addAnglerfish(int... itemIds)
    {
        addRule(new HealRule()
        {
            @Override
            public int baseHeal(int baseHP)
            {
                // Anglerfish heal scales with base HP and caps at 22.
                int c;

                if (baseHP >= 93)      c = 13;   // makes 99 -> floor(9) + 13 = 22
                else if (baseHP >= 83) c = 12;
                else if (baseHP >= 73) c = 11;
                else if (baseHP >= 63) c = 10;
                else if (baseHP >= 53) c = 9;
                else if (baseHP >= 43) c = 8;
                else if (baseHP >= 33) c = 7;
                else if (baseHP >= 23) c = 6;
                else                   c = 5;

                int heal = (baseHP / 10) + c;
                return Math.min(22, heal);
            }

            @Override
            public int allowedMaxHP(int baseHP)
            {
                // Angler overheal cap is "baseHP + baseHeal"
                return baseHP + baseHeal(baseHP);
            }
        }, itemIds);
    }
}