package com.FoodUtils;
import com.FoodUtils.PotionRules.PotionRule;
import com.FoodUtils.PotionRules.RegenerationPotionRule;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class PotionRuleData {

    private final Map<Integer, PotionRule> RULES = new HashMap<>();
    @Inject
    public PotionRuleData(){

        populateRuleMap();

    }
    public PotionRule getRuleForItemId(int itemId){
        return RULES.get(itemId);
    }

    private void addRule(PotionRule rule, int... itemIds)
    {
        for (int id: itemIds)
        {
            RULES.put(id, rule);
        }
    }

    private void populateRuleMap()
    {
        // Prayer regeneration potion restores 66 prayer per dose gradually over 8 minutes
        addRule(new RegenerationPotionRule(), 30125, 30128, 30131, 30134);
    }
}
