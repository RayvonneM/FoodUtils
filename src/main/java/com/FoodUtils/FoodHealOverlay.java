package com.FoodUtils;
import net.runelite.api.Skill;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;

public class FoodHealOverlay extends WidgetItemOverlay{

    private final FoodUtilsConfig config;
    private final Client client;


    @Inject
    public FoodHealOverlay(Client client, FoodUtilsConfig config)
    {
        this.config = config;
        this.client = client;
        showOnInventory();
    }
    @Override public void renderItemOverlay(java.awt.Graphics2D graphics,int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
        int baseHP = client.getRealSkillLevel(Skill.HITPOINTS);

        FoodData.FoodInfo info = FoodData.calculate(itemId, baseHP, currentHP);
                if (info == null)
                {
                    return;
                }
                int base = info.baseHeal;
                int effective = info.effectiveHeal;

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
            if(effective == 0)
            {
                textColor = config.noHealColor();
            }
            else if(effective < base)
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
        //Draw with userColors
        graphics.setColor(textColor);
        graphics.drawString(text, x , y);
        //Reset old color just in case
        graphics.setColor(oldColor);

    }

}
