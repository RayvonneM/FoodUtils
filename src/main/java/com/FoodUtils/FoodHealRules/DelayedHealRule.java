package com.FoodUtils.FoodHealRules;

public class DelayedHealRule implements HealRule{

    public DelayedHealRule(int immediateHeal, int delayedHeal){
        this.immediateHeal = immediateHeal;
        this.delayedHeal = delayedHeal;
    }

    public int itemBaseHealAmount(int baseHp){
        return immediateHeal + delayedHeal;
    }

    public int allowedMaxHp(int baseHp)
    {
        return baseHp;
    }
    public int immediateHeal;
    public int delayedHeal;
}
