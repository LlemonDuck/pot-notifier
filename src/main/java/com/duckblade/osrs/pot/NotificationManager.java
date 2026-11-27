package com.duckblade.osrs.pot;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.SoundEffectVolume;
import net.runelite.client.Notifier;
import net.runelite.client.audio.AudioPlayer;
import net.runelite.client.util.Filepath;

@Slf4j
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class NotificationManager
{

	private static final String CUSTOM_SOUND_FILE_NAME = "notification.wav";

	private final Client client;
	private final PotNotifierConfig config;
	private final Notifier notifier;
	private final AudioPlayer audioPlayer;
	private final ScheduledExecutorService executor;

	private volatile Filepath customSoundFile;

	public void startUp(Filepath dataDir) throws IOException
	{
		customSoundFile = null;
		dataDir.createDirectories();
		customSoundFile = dataDir.joinSegment(CUSTOM_SOUND_FILE_NAME);
	}

	public void notifyPotted(Player p)
	{
		notifier.notify(config.notificationRuneLite(), p.getName() + " has potted!");

		if (config.soundEffectEnabled())
		{
			playInGameSound();
		}

		if (config.customSoundEnabled())
		{
			playCustomSound();
		}
	}

	private void playInGameSound()
	{
		// config is a 1-100 percentage, the game's sound effect volume is 0-127
		int volume = Math.round(config.soundEffectVolume() * SoundEffectVolume.HIGH / 100f);
		client.playSoundEffect(config.soundEffectId(), volume);
	}

	private void playCustomSound()
	{
		Filepath file = customSoundFile;
		int volume = config.customSoundVolume();
		if (file == null || volume <= 0)
		{
			return;
		}

		float gainDb = (float) Math.log10(volume / 100f) * 20;
		executor.submit(() ->
		{
			if (!file.isFile())
			{
				log.debug("custom sound file {} does not exist", file);
				return;
			}

			try
			{
				audioPlayer.play(file, gainDb);
			}
			catch (Exception e)
			{
				log.warn("Failed to play custom sound {}", file, e);
			}
		});
	}

}
