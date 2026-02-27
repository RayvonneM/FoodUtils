package com.FoodUtils;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import com.FoodUtils.TextPosition;

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
			keyName = "textPosition",
			name = "Text Position",
			description = "Where the heal number appears on items"
	)
	default TextPosition textPosition(){
		return TextPosition.BOTTOM_RIGHT;
	}
	@ConfigItem(
			keyName = "shadow",
			name = "Enable Shadow",
			description = "Draw black shadow behind text"
	)
	default boolean shadow()
	{
		return true;
	}
}
