package com.FoodUtils.PotionRules;

public class RegenerationPotionRule implements PotionRule
{
    @Override
    public int baseRestoreAmount(int statRealLevel)
    {
        return 66;
    }

    @Override
    public int allowedMaxStat(int statRealLevel)
    {
        return statRealLevel;
    }
}
