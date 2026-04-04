package com.FoodUtils.PotionRules;

public interface PotionRule {
    int baseRestoreAmount(int statRealLevel);
    int allowedMaxStat(int statRealLevel);
}
