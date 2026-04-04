package com.FoodUtils;

import com.FoodUtils.enums.HealDisplayMode;
import com.FoodUtils.enums.DelayedDisplayMode;
import com.FoodUtils.enums.TextPosition;
import com.FoodUtils.enums.MixedConsumableMode;
import net.runelite.client.config.*;

@ConfigGroup("foodUtils")
public interface FoodUtilsConfig extends Config
{
	@ConfigSection(
			name = "General",
			description = "General Display Settings",
			position = 0
	)
	String generalSection = "generalSection";

	@ConfigSection(
			name = "Healing",
			description = "Food healing settings",
			position = 1
	)
	String healingSection = "healingSection";

	@ConfigSection(
			name = "Potions",
			description = "Potion Display settings",
			position = 2
	)
	String potionsSection = "potionsSection";

	@ConfigItem(
			position = 0,
			keyName = "textPosition",
			name = "Text Position",
			description = "Where the heal number appears on items",
			section = generalSection
	)
	default TextPosition textPosition(){
		return TextPosition.BOTTOM_RIGHT;
	}

	@ConfigItem(
			position = 1,
			keyName = "xOffset",
			name = "X offset",
			description = "Horizontal adjustment for heal text",
			section = generalSection
	)
	default int xOffset()
	{
		return 0;
	}

	@ConfigItem(
			position = 2,
			keyName = "yOffset",
			name = "Y offset",
			description = "Vertical adjustment for heal text",
			section = generalSection
	)
	default int yOffset()
	{
		return 0;
	}

	@Range(
			min = 8,
			max = 24
	)
	@ConfigItem(
			position = 3,
			keyName = "fontSize",
			name = "Font size",
			description = "Size of heal text",
			section = generalSection
	)
	default int fontSize()
	{
		return 14;
	}

	@ConfigItem(
			position = 4,
			keyName = "shadow",
			name = "Enable Shadow",
			description = "Draw black shadow behind text",
			section = generalSection
	)
	default boolean shadow()
	{
		return true;
	}

	@ConfigItem(
			position = 5,
			keyName = "hoverToggle",
			name = "Show only on hover",
			description = "Display heal values only when hovering an item",
			section = generalSection
	)
	default boolean onlyShowOnHover()
	{
		return false;
	}

	@ConfigItem(
			position = 0,
			keyName = "healDisplayMode",
			name = "Heal Display",
			description = "Show base heal, effective heal, or both",
			section = healingSection
	)
	default HealDisplayMode healDisplayMode()
	{
		return HealDisplayMode.BOTH;
	}
	@ConfigItem(
			position = 1,
			keyName = "delayedDisplayMode",
			name = "Delayed Heal Display",
			description = "How to display foods that heal in two stages (e.g. hunter meats)",
			section = healingSection
	)
	default DelayedDisplayMode delayedDisplayMode()
	{
		return DelayedDisplayMode.DUAL;
	}

	@ConfigItem(
			position = 2,
			keyName = "multiColorDelayed",
			name = "Multi-color Delayed Heals",
			description = "Color each portion of a delayed heal separately based on whether it is wasted",
			section = healingSection
	)
	default boolean multiColorDelayed()
	{
		return true;
	}

	@ConfigItem(
			position = 3,
			keyName = "colorForWaste",
			name = "Color for wasted healing",
			description = "Enable color indicators based on heal waste",
			section = healingSection
	)
	default boolean colorForWaste()
	{
		return true;
	}

	@ConfigItem(
			position = 4,
			keyName = "fullValueColor",
			name = "Full-value Color",
			description = "Text color when you will get the full heal (missing >= base)",
			section = healingSection
	)
	default java.awt.Color fullValueColor()
	{
		return java.awt.Color.WHITE;
	}

	@ConfigItem(
			position = 5,
			keyName = "wasteColor",
			name = "Waste Color",
			description = "Text color when eating would waste healing (missing < base)",
			section = healingSection
	)
	default java.awt.Color wasteColor()
	{
		return java.awt.Color.RED;
	}

	@ConfigItem(
			position = 6,
			keyName = "noHealColor",
			name = "No-heal Color",
			description = "Text color when you are at full HP (missing = 0)",
			section = healingSection
	)
	default java.awt.Color noHealColor()
	{
		return java.awt.Color.GRAY;
	}

	@ConfigItem(
			keyName = "showPotions",
			name = "Show potion boosts",
			description = "Show potion boost values on supported potion items",
			position = 0,
			section = potionsSection
	)
	default boolean showPotions()
	{
		return true;
	}

	@ConfigItem(
			keyName = "gradualPotionColor",
			name = "Gradual Potion Color",
			description = "Text color for potions that restore stats gradually over time (e.g. prayer regeneration potion)",
			position = 2,
			section = potionsSection
	)
	default java.awt.Color gradualPotionColor()
	{
		return new java.awt.Color(255, 200, 0);
	}

	@ConfigItem(
			keyName = "mixedConsumableMode",
			name = "Mixed Consumable Display",
			description = "For items that both heal and boost, choose whether to show heal or boost values",
			position = 1,
			section = potionsSection
	)
	default MixedConsumableMode mixedConsumableMode()
	{
		return MixedConsumableMode.HEAL;
	}
}
