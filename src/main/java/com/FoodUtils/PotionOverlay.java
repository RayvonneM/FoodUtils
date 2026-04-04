package com.FoodUtils;

import net.runelite.client.ui.overlay.WidgetItemOverlay;
import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;
import com.FoodUtils.enums.MixedConsumableMode;

public class PotionOverlay extends WidgetItemOverlay {

    private final FoodUtilsConfig config;
    private final Client client;
    private final PotionData potionData;
    private final FoodData foodData;

    @Inject
    public PotionOverlay(Client client, FoodUtilsConfig config, PotionData potionData, FoodData foodData)
    {
        this.config = config;
        this.client = client;
        this.potionData = potionData;
        this.foodData = foodData;
        showOnInventory();
    }

    @Override
    public void renderItemOverlay(java.awt.Graphics2D graphics, int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        if (!config.showPotions()) return;
        if (graphics == null || item == null) return;

        Rectangle bounds = item.getCanvasBounds();
        if (bounds == null) return;

        int currentHP = client.getBoostedSkillLevel(net.runelite.api.Skill.HITPOINTS);
        int baseHP = client.getRealSkillLevel(net.runelite.api.Skill.HITPOINTS);

        FoodData.FoodInfo foodInfo = foodData.calculate(itemId, baseHP, currentHP);
        if (foodInfo != null && config.mixedConsumableMode() == MixedConsumableMode.HEAL) return;

        PotionData.PotionInfo info = potionData.calculate(itemId);
        if (info == null) return;

        int base = info.baseBoost;
        int effective = info.effectiveBoost;

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

        // Gradual potions (e.g. prayer regen) get a ~ prefix and no + sign to indicate they restore over time
        if (info.isGradual)
        {
            switch (config.healDisplayMode())
            {
                case BASE:
                    text = "~" + base;
                    break;
                case EFFECTIVE:
                    text = "~" + effective;
                    break;
                case BOTH:
                default:
                    text = "~" + effective + "/" + base;
                    break;
            }
        }

        if (config.onlyShowOnHover())
        {
            net.runelite.api.Point mouse = client.getMouseCanvasPosition();
            if (!bounds.contains(mouse.getX(), mouse.getY())) return;
        }

        Font oldFont = graphics.getFont();
        Font customFont = oldFont.deriveFont((float) config.fontSize());
        graphics.setFont(customFont);
        FontMetrics fm = graphics.getFontMetrics();

        int x;
        int y;
        switch (config.textPosition())
        {
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

        java.awt.Color oldColor = graphics.getColor();
        java.awt.Color textColor;

        // Gradual potions always use the gradual color — waste logic doesn't apply
        if (info.isGradual)
        {
            textColor = config.gradualPotionColor();
        }
        else if (config.colorForWaste())
        {
            if (effective == 0) textColor = config.noHealColor();
            else if (effective < base) textColor = config.wasteColor();
            else textColor = config.fullValueColor();
        }
        else
        {
            textColor = config.fullValueColor();
        }

        if (config.shadow())
        {
            graphics.setColor(java.awt.Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }
        graphics.setColor(textColor);
        graphics.drawString(text, x, y);
        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }
}
