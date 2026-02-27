package com.FoodUtils;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.*;
import java.util.Map;
import net.runelite.api.ItemID;
import javax.inject.Inject;

public class FoodHealOverlay extends WidgetItemOverlay{

    private final FoodUtilsConfig config;

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
    public FoodHealOverlay(FoodUtilsConfig config)
    {
        this.config = config;
        showOnInventory();
    }
    @Override public void renderItemOverlay(java.awt.Graphics2D graphics,int itemId, net.runelite.api.widgets.WidgetItem item)
    {
        Integer heal = HEAL_BY_ITEM.get(itemId);
        if(heal==null){
            return;
        }
        String text = "+" +heal;
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

        //Shadow
        if(config.shadow()) {
            graphics.setColor(java.awt.Color.BLACK);
            graphics.drawString(text, x + 1, y + 1);
        }
        //Main Text
        graphics.setColor(java.awt.Color.WHITE);
        graphics.drawString(text, x, y);

    }

}
