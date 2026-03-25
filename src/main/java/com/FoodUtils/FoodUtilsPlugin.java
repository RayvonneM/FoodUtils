package com.FoodUtils;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.itemstats.ItemStatPlugin;


@Slf4j
@PluginDescriptor(
	name = "FoodUtils"
)
@PluginDependency(ItemStatPlugin.class)
public class FoodUtilsPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private FoodUtilsConfig config;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private FoodHealOverlay foodHealOverlay;

	@Inject
	private PotionOverlay potionOverlay;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(foodHealOverlay);
		overlayManager.add(potionOverlay);
		log.debug("Food Utils Running");
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(foodHealOverlay);
		overlayManager.remove(potionOverlay);
		log.debug("Food Utils Stopped");
	}

	@Provides
	FoodUtilsConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FoodUtilsConfig.class);
	}
}
