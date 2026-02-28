package com.FoodUtils;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import com.FoodUtils.TextPosition;
import com.FoodUtils.HealDisplayMode;

@ConfigGroup("foodUtils")
public interface FoodUtilsConfig extends Config {
	@ConfigItem(
			keyName = "greeting",
			name = "Welcome Greeting",
			description = "Message shown when you log in"
	)
	default String greeting()
	{
		return "Welcome to FoodUtils";
	}
	@ConfigItem(
			position = 1,
			keyName = "textPosition",
			name = "Text Position",
			description = "Where the heal number appears on items"
	)
	default TextPosition textPosition(){
		return TextPosition.BOTTOM_RIGHT;
	}
	@ConfigItem(
			position = 2,
			keyName = "shadow",
			name = "Enable Shadow",
			description = "Draw black shadow behind text"
	)
	default boolean shadow()
	{
		return true;
	}
	@ConfigItem(
			position = 0,
			keyName = "healDisplayMode",
			name = "Heal Display",
			description = "Shoow base heal, effective heal, or both"
	)
	default HealDisplayMode healDisplayMode()
	{
		return HealDisplayMode.BOTH;
	}
	@ConfigItem(
			position = 3,
			keyName = "colorForWaste",
			name = "Color for wasted healing",
			description ="Color food red when it would waste healing"
	)
	default boolean colorForWaste()
	{
		return true;
	}
	@ConfigItem(
			position = 4,
			keyName = "noHealColor",
			name = "No-heal Color",
			description = "Text color when you are at full HP (missing = 0)"
	)
	default java.awt.Color noHealColor()
	{
		return java.awt.Color.GRAY;
	}

	@ConfigItem(
			position = 5,
			keyName = "wasteColor",
			name = "Waste Color",
			description = "Text color when eating would waste healing (missing < base)"
	)
	default java.awt.Color wasteColor()
	{
		return java.awt.Color.RED;
	}

	@ConfigItem(
			position = 6,
			keyName = "fullValueColor",
			name = "Full-value Color",
			description = "Text color when you will get the full heal (missing >= base)"
	)
	default java.awt.Color fullValueColor()
	{
		return java.awt.Color.WHITE;
	}
}
