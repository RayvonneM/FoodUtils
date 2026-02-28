package com.FoodUtils;
import net.runelite.api.Skill;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.*;
import java.util.Map;
import net.runelite.api.ItemID;
import javax.inject.Inject;
import net.runelite.api.Client;

public class FoodHealOverlay extends WidgetItemOverlay{

    private final FoodUtilsConfig config;
    private final Client client;

    private static final Map<Integer, Integer> HEAL_BY_ITEM = Map.of(
            ItemID.SHARK, 20,
            ItemID.COOKED_KARAMBWAN, 18,
            ItemID.CHILLI_POTATO, 14,
            ItemID.TROUT, 7,
            ItemID.SALMON, 9,
            ItemID.LOBSTER, 12,
            ItemID.SWORDFISH, 14,
            ItemID.MONKFISH, 16


    );
    @Inject
    public FoodHealOverlay(Client client, FoodUtilsConfig config)
    {
        this.config = config;
        this.client = client;
        showOnInventory();
    }
    @Override public void renderItemOverlay(java.awt.Graphics2D graphics,int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        // HP calculation logic/Variables
        Integer heal = HEAL_BY_ITEM.get(itemId);

        if(heal == null)
        {
            return;
        }
        //Boosted displays current hp + all modifiers including damage taken
        int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
        //Real displays current hp lvl
        int maxHP = client.getRealSkillLevel(Skill.HITPOINTS);
        int missing = Math.max(0, maxHP - currentHP);
        int base = heal;
        int effective = Math.min(base, missing);

        // Write heal value
        String text;

        switch (config.healDisplayMode())
        {
            case BASE:
                text = "+" + base;
                break;

            case EFFECTIVE:
                text = "+" + effective;
                break;

            case BOTH:
            default:
                text = "+" + effective + "/" + base;
                break;
        }
        java.awt.Rectangle bounds = item.getCanvasBounds();
        java.awt.FontMetrics fm = graphics.getFontMetrics();

        int x;
        int y;

        switch (config.textPosition()){
            case TOP_LEFT:
                x = bounds.x + 2;
                y = bounds.y + 12;
                break;

            case TOP_RIGHT:
                x = bounds.x + bounds.width - fm.stringWidth(text) - 2;
                y = bounds.y + 12;
                break;

            case BOTTOM_LEFT:
                x = bounds.x + 2;
                y = bounds.y + bounds.height - 2;
                break;

            case BOTTOM_RIGHT:
            default:
                x = bounds.x + bounds.width - fm.stringWidth(text) - 2;
                y = bounds.y + bounds.height - 2;
                break;
        }

        java.awt.Color oldColor = graphics.getColor();
        java.awt.Color textColor = Color.WHITE;

        //Shadow
        if (config.colorForWaste())
        {
            if(missing == 0)
            {
                textColor = config.noHealColor();
            }
            else if(missing < base)
            {
                textColor = config.wasteColor();
            }
            else
            {
                textColor = config.fullValueColor();
            }
        }
        if(config.shadow()) {
            graphics.setColor(java.awt.Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }
        //Main Text
        graphics.setColor(textColor);
        graphics.drawString(text, x, y);

        //Draw with userColors
        graphics.setColor(textColor);
        graphics.drawString(text, x , y);
        //Reset old color just in case
        graphics.setColor(oldColor);

    }

}
