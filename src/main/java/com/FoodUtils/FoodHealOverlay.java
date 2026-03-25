package com.FoodUtils;
import net.runelite.api.Skill;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import net.runelite.api.Point;
import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;
import com.FoodUtils.enums.MixedConsumableMode;

public class FoodHealOverlay extends WidgetItemOverlay{

    private final FoodUtilsConfig config;
    private final Client client;
    private final FoodData foodData;
    private final PotionData potionData;


    @Inject
    public FoodHealOverlay(Client client, FoodUtilsConfig config, FoodData foodData, PotionData potionData)
    {
        this.config = config;
        this.client = client;
        this.foodData = foodData;
        this.potionData = potionData;
        showOnInventory();
    }

    @Override public void renderItemOverlay(java.awt.Graphics2D graphics, int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        //Null error handling
        if (graphics == null || item == null)
        {
            return;
        }

        Rectangle bounds = item.getCanvasBounds();
        if (bounds == null)
        {
            return;
        }

        PotionData.PotionInfo potionInfo = null;

        if (config.showPotions())
        {
            potionInfo = potionData.calculate(itemId);
        }

        if (potionInfo != null && config.mixedConsumableMode() == MixedConsumableMode.BOOST)
        {
            return;
        }

        int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
        int baseHP = client.getRealSkillLevel(Skill.HITPOINTS);
        //Null Error handling
        FoodData.FoodInfo info = foodData.calculate(itemId, baseHP, currentHP);
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
        //On hover implementation
        if(config.onlyShowOnHover())
        {
            net.runelite.api.Point mouse = client.getMouseCanvasPosition();
            if(!bounds.contains(mouse.getX(), mouse.getY()))
            {
                return;
            }
        }
        Font oldFont = graphics.getFont();
        Font customFont = oldFont.deriveFont((float) config.fontSize());
        graphics.setFont(customFont);
        java.awt.FontMetrics fm = graphics.getFontMetrics();

        int x;
        int y;
        //Text position implementation
        switch (config.textPosition()){
            case TOP_LEFT:
                x = bounds.x + 2;
                y = bounds.y + 12;
                break;

            case TOP_RIGHT:
                x = bounds.x + bounds.width - fm.stringWidth(text) - 2;
                y = bounds.y + 12;
                break;

            case MIDDLE_LEFT:
                x = bounds.x + 2;
                y = bounds.y + (bounds.height / 2) + (fm.getAscent() / 2) - 2;
                break;

            case MIDDLE_RIGHT:
                x = bounds.x + bounds.width - fm.stringWidth(text) - 2;
                y = bounds.y + (bounds.height / 2) + (fm.getAscent() / 2) - 2;
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

        x += config.xOffset();
        y += config.yOffset();
        //Default text color
        java.awt.Color oldColor = graphics.getColor();
        java.awt.Color textColor = Color.WHITE;

        // Shadow Implementation
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
        graphics.setFont(oldFont);
        graphics.setColor(oldColor);

    }

}
