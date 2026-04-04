package com.FoodUtils.FoodHealRules;

public class PercentageFloorItemHealRule implements HealRule
{
    public PercentageFloorItemHealRule(double pct, int flat)
    {
        this.pct = pct;
        this.flat = flat;
    }

    @Override
    public int itemBaseHealAmount(int baseHp)
    {
        return (int) Math.floor(baseHp * pct) + flat;
    }

    @Override
    public int allowedMaxHp(int baseHp)
    {
        return baseHp;
    }

    double pct;
    int flat;
}
