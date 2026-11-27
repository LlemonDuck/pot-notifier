package com.duckblade.osrs.pot;

import net.runelite.api.gameval.SoundEffectID;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Notification;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("potnotifier")
public interface PotNotifierConfig extends Config
{

	@ConfigSection(
		name = "Locations",
		description = "Where to notify when a teammate pots.",
		position = 0
	)
	String SECTION_LOCATIONS = "locations";

	@ConfigSection(
		name = "Notifications",
		description = "How to notify when a teammate pots.",
		position = 1
	)
	String SECTION_NOTIFICATIONS = "notifications";

	@ConfigItem(
		keyName = "locationCox",
		name = "CoX",
		description = "Notify in the Chambers of Xeric.",
		section = SECTION_LOCATIONS,
		position = 0
	)
	default boolean locationCox()
	{
		return true;
	}

	@ConfigItem(
		keyName = "locationTob",
		name = "ToB",
		description = "Notify in the Theatre of Blood.",
		section = SECTION_LOCATIONS,
		position = 1
	)
	default boolean locationTob()
	{
		return true;
	}

	@ConfigItem(
		keyName = "locationToa",
		name = "ToA",
		description = "Notify in the Tombs of Amascut.",
		section = SECTION_LOCATIONS,
		position = 2
	)
	default boolean locationToa()
	{
		return true;
	}

	@ConfigItem(
		keyName = "locationTfa",
		name = "TFA",
		description = "Notify in The Fractured Archive.",
		section = SECTION_LOCATIONS,
		position = 3
	)
	default boolean locationTfa()
	{
		return true;
	}

	@ConfigItem(
		keyName = "locationNex",
		name = "Nex",
		description = "Notify at Nex.",
		section = SECTION_LOCATIONS,
		position = 4
	)
	default boolean locationNex()
	{
		return true;
	}

	@ConfigItem(
		keyName = "notificationRuneLite",
		name = "RuneLite Notification",
		description = "Send a RuneLite notification when a teammate pots.",
		section = SECTION_NOTIFICATIONS,
		position = 0
	)
	default Notification notificationRuneLite()
	{
		return Notification.OFF;
	}

	@ConfigItem(
		keyName = "soundEffectEnabled",
		name = "In-Game Sound Effect",
		description = "Play an in-game sound effect when a teammate pots.",
		section = SECTION_NOTIFICATIONS,
		position = 1
	)
	default boolean soundEffectEnabled()
	{
		return true;
	}

	@ConfigItem(
		keyName = "soundEffectId",
		name = " ↳ Sound Effect ID",
		description = "The in-game sound effect ID to play.",
		section = SECTION_NOTIFICATIONS,
		position = 2
	)
	@Range(min = 0)
	default int soundEffectId()
	{
		return SoundEffectID.JEWEL_DING;
	}

	@ConfigItem(
		keyName = "soundEffectVolume",
		name = " ↳ Volume",
		description = "The volume of the in-game sound effect.",
		section = SECTION_NOTIFICATIONS,
		position = 3
	)
	@Units(Units.PERCENT)
	@Range(min = 1, max = 100)
	default int soundEffectVolume()
	{
		return 50;
	}

	@ConfigItem(
		keyName = "customSoundEnabled",
		name = "Custom Sound",
		description = "Play a custom sound when a teammate pots." +
			"<br>Place a WAV file named notification.wav in .runelite/plugin-data/pot-notifier for it to play.",
		section = SECTION_NOTIFICATIONS,
		position = 4
	)
	default boolean customSoundEnabled()
	{
		return false;
	}

	@ConfigItem(
		keyName = "customSoundVolume",
		name = " ↳ Volume",
		description = "The volume of the custom sound.",
		section = SECTION_NOTIFICATIONS,
		position = 5
	)
	@Units(Units.PERCENT)
	@Range(min = 1, max = 100)
	default int customSoundVolume()
	{
		return 50;
	}
}
