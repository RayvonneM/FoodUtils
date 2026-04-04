package com.FoodUtils.FoodHealRules;

public class HaddockHealRule implements HealRule
{
    @Override
    public int itemBaseHealAmount(int baseHp)
    {
        return 18;
    }

    @Override
    public int allowedMaxHp(int baseHp)
    {
        // Overheal cap is floor(min(10, 0.2 * baseHp)) above max HP
        int overheal = (int) Math.floor(Math.min(10, 0.2 * baseHp));
        return baseHp + overheal;
    }
}
