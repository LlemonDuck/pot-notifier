package com.duckblade.osrs.pot;

import com.duckblade.osrs.pot.location.CoxLocationCheck;
import com.duckblade.osrs.pot.location.LocationCheck;
import com.duckblade.osrs.pot.location.NexLocationCheck;
import com.duckblade.osrs.pot.location.TfaLocationCheck;
import com.duckblade.osrs.pot.location.ToaLocationCheck;
import com.duckblade.osrs.pot.location.TobLocationCheck;
import com.google.common.collect.ImmutableList;
import com.google.inject.Provides;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.gameval.AnimationID;
import net.runelite.api.gameval.SpotanimID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Pot Notifier",
	internalName = "pot-notifier",
	description = "Notifies you when your teammates pot.",
	tags = {"pot", "potion", "chug", "chugging", "barrel", "ready"}
)
public class PotNotifierPlugin extends Plugin
{

	private static final int COOLDOWN_TICKS = 50;

	@Inject
	private Client client;

	@Inject
	private PotNotifierConfig config;

	@Inject
	private CoxLocationCheck coxLocationCheck;

	@Inject
	private TobLocationCheck tobLocationCheck;

	@Inject
	private ToaLocationCheck toaLocationCheck;

	@Inject
	private TfaLocationCheck tfaLocationCheck;

	@Inject
	private NexLocationCheck nexLocationCheck;

	@Inject
	private NotificationManager notificationManager;

	private int lastLocalPot = Integer.MIN_VALUE;
	private int lastNotify = Integer.MIN_VALUE;
	private List<LocationCheck> locationChecks;

	@Provides
	PotNotifierConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PotNotifierConfig.class);
	}

	@Override
	protected void startUp()
	{
		locationChecks = ImmutableList.of(
			coxLocationCheck,
			tobLocationCheck,
			toaLocationCheck,
			tfaLocationCheck,
			nexLocationCheck
		);

		reset();

		try
		{
			notificationManager.startUp(getPluginDirectory());
		}
		catch (IOException e)
		{
			log.warn("Failed to create plugin data directory, custom sounds are unavailable", e);
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged e)
	{
		GameState state = e.getGameState();
		if (state == GameState.LOGGING_IN || state == GameState.HOPPING)
		{
			reset();
		}
	}

	private void reset()
	{
		lastLocalPot = Integer.MIN_VALUE;
		lastNotify = Integer.MIN_VALUE;
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged e)
	{
		int now = client.getTickCount();
		if (now < lastLocalPot + COOLDOWN_TICKS || now < lastNotify + COOLDOWN_TICKS)
		{
			return;
		}

		if (!(e.getActor() instanceof Player))
		{
			return;
		}

		int regionId = WorldPoint.fromLocalInstance(client, client.getLocalPlayer().getLocalLocation()).getRegionID();
		LocationCheck location = resolveLocationCheck(regionId);
		if (location == null)
		{
			return;
		}

		Player p = (Player) e.getActor();
		if (!playerIsPotting(p))
		{
			return;
		}

		if (p == client.getLocalPlayer())
		{
			lastLocalPot = now;
			return;
		}

		if (location.isPlayerOfInterest(p))
		{
			lastNotify = now;
			notificationManager.notifyPotted(p);
		}
	}

	private LocationCheck resolveLocationCheck(int regionId)
	{
		for (LocationCheck check : locationChecks)
		{
			if (check.isInRegion(regionId) && check.isEnabled(config))
			{
				return check;
			}
		}

		return null;
	}

	private boolean playerIsPotting(Player p)
	{
		if (p.getAnimation() == AnimationID.PREPOT_DEVICE_DRINK_FULL)
		{
			return true;
		}

		return p.getAnimation() == AnimationID.HUMAN_EAT &&
			p.hasSpotAnim(SpotanimID.SKELETON_KILLERWATT_ELECTRICSHOCK);
	}
}
