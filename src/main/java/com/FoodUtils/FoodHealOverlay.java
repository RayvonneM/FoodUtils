package com.FoodUtils;

import com.FoodUtils.enums.DelayedDisplayMode;
import net.runelite.api.Skill;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import java.awt.*;
import javax.inject.Inject;
import net.runelite.api.Client;
import com.FoodUtils.enums.MixedConsumableMode;

public class FoodHealOverlay extends WidgetItemOverlay {

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

    @Override
    public void renderItemOverlay(java.awt.Graphics2D graphics, int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        if (graphics == null || item == null) return;

        Rectangle bounds = item.getCanvasBounds();
        if (bounds == null) return;

        PotionData.PotionInfo potionInfo = null;
        if (config.showPotions())
        {
            potionInfo = potionData.calculate(itemId);
        }
        if (potionInfo != null && config.mixedConsumableMode() == MixedConsumableMode.BOOST) return;

        int currentHP = client.getBoostedSkillLevel(Skill.HITPOINTS);
        int baseHP = client.getRealSkillLevel(Skill.HITPOINTS);
        FoodData.FoodInfo info = foodData.calculate(itemId, baseHP, currentHP);
        if (info == null) return;

        if (config.onlyShowOnHover())
        {
            net.runelite.api.Point mouse = client.getMouseCanvasPosition();
            if (!bounds.contains(mouse.getX(), mouse.getY())) return;
        }

        Font oldFont = graphics.getFont();
        Font customFont = oldFont.deriveFont((float) config.fontSize());
        graphics.setFont(customFont);
        FontMetrics fm = graphics.getFontMetrics();
        java.awt.Color oldColor = graphics.getColor();

        boolean hasDelayedHeal = info.delayedHeal > 0;
        // Dual multicolor mode draws each heal portion in its own color
        boolean dualMultiColor = hasDelayedHeal
                && config.delayedDisplayMode() == DelayedDisplayMode.DUAL
                && config.multiColorDelayed();

        if (dualMultiColor)
        {
            drawDualMultiColor(graphics, bounds, fm, info);
        }
        else
        {
            drawSingleText(graphics, bounds, fm, info, hasDelayedHeal);
        }

        graphics.setFont(oldFont);
        graphics.setColor(oldColor);
    }

    // Draws immediate and delayed heal portions in separate colors
    private void drawDualMultiColor(Graphics2D graphics, Rectangle bounds, FontMetrics fm, FoodData.FoodInfo info)
    {
        String immText = "+" + info.immediateHeal;
        String delText = "-" + info.delayedHeal;
        int immWidth = fm.stringWidth(immText);
        int totalWidth = immWidth + fm.stringWidth(delText);

        int x = getX(bounds, totalWidth);
        int y = getY(bounds, fm);
        x += config.xOffset();
        y += config.yOffset();

        // Calculate how much of each portion the player can actually receive
        int effectiveImmediate = Math.min(info.immediateHeal, info.missingToAllowedMax);
        int effectiveDelayed = Math.min(info.delayedHeal, Math.max(0, info.missingToAllowedMax - info.immediateHeal));

        Color immColor = getHealColor(effectiveImmediate, info.immediateHeal);
        Color delColor = getHealColor(effectiveDelayed, info.delayedHeal);

        if (config.shadow())
        {
            graphics.setColor(Color.BLACK);
            graphics.drawString(immText, x + 1, y + 1);
            graphics.drawString(delText, x + immWidth + 1, y + 1);
        }
        graphics.setColor(immColor);
        graphics.drawString(immText, x, y);
        graphics.setColor(delColor);
        graphics.drawString(delText, x + immWidth, y);
    }

    private void drawSingleText(Graphics2D graphics, Rectangle bounds, FontMetrics fm, FoodData.FoodInfo info, boolean hasDelayedHeal)
    {
        int base = info.baseHeal;
        int effective = info.effectiveHeal;

        String text;
        if (hasDelayedHeal)
        {
            switch (config.delayedDisplayMode())
            {
                case BASE:
                    text = "+" + info.immediateHeal;
                    break;
                case DELAYED:
                    text = "+" + info.delayedHeal;
                    break;
                case DUAL:
                default:
                    text = "+" + info.immediateHeal + "-" + info.delayedHeal;
                    break;
            }
        }
        else
        {
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
        }

        int x = getX(bounds, fm.stringWidth(text));
        int y = getY(bounds, fm);
        x += config.xOffset();
        y += config.yOffset();

        Color textColor = config.fullValueColor();
        if (config.colorForWaste())
        {
            if (effective == 0) textColor = config.noHealColor();
            else if (effective < base) textColor = config.wasteColor();
            else textColor = config.fullValueColor();
        }

        if (config.shadow())
        {
            graphics.setColor(Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }
        graphics.setColor(textColor);
        graphics.drawString(text, x, y);
    }

    // Returns the appropriate color based on how much of the heal is effective
    private Color getHealColor(int effective, int base)
    {
        if (!config.colorForWaste()) return config.fullValueColor();
        if (effective == 0) return config.noHealColor();
        if (effective < base) return config.wasteColor();
        return config.fullValueColor();
    }

    private int getX(Rectangle bounds, int textWidth)
    {
        switch (config.textPosition())
        {
            case TOP_LEFT:
            case MIDDLE_LEFT:
            case BOTTOM_LEFT:
                return bounds.x + 2;
            case TOP_RIGHT:
            case MIDDLE_RIGHT:
            case BOTTOM_RIGHT:
            default:
                return bounds.x + bounds.width - textWidth - 2;
        }
    }

    private int getY(Rectangle bounds, FontMetrics fm)
    {
        switch (config.textPosition())
        {
            case TOP_LEFT:
            case TOP_RIGHT:
                return bounds.y + 12;
            case MIDDLE_LEFT:
            case MIDDLE_RIGHT:
                return bounds.y + (bounds.height / 2) + (fm.getAscent() / 2) - 2;
            case BOTTOM_LEFT:
            case BOTTOM_RIGHT:
            default:
                return bounds.y + bounds.height - 2;
        }
    }
}
