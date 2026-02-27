package com.FoodUtils;
import net.runelite.client.ui.overlay.WidgetItemOverlay;

import java.awt.*;
import java.util.Map;
import net.runelite.api.ItemID;

public class FoodHealOverlay extends WidgetItemOverlay{
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
    public FoodHealOverlay()
    {
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

        int x = bounds.x + bounds.width - fm.stringWidth(text) -2; // Right Padding
        int y = bounds.y + bounds.height -2; //padding from bottom

        java.awt.Color oldColor = graphics.getColor();

        //Shadow
        graphics.setColor(java.awt.Color.BLACK);
        graphics.drawString(text,x+1,y+1);

        //Main Text
        graphics.setColor(java.awt.Color.WHITE);
        graphics.drawString(text, x, y);

    }

}
