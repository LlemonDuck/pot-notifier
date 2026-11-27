# Pot Notifier

Get notified when your teammates pot, so you know when to pot too.

Watches for divine potion sips and chugging barrel uses.
There's a cooldown of 30s after seeing one player sip, or after you sip.

## Locations

Toggleable in the config.

* **CoX** - Chambers of Xeric. Inside a raid, everyone in your raid counts as a teammate. In the lobby, members of your friends chat count.
* **ToB** - Theatre of Blood. Members of your raid party count.
* **ToA** - Tombs of Amascut. Members of your raid party count.
* **TFA** - The Fractured Archive. Members of your party in the lobby count.
* **Nex**

## Notifications

* **RuneLite Notification** - send a RuneLite notification. Off by default.
* **In-Game Sound Effect** - play an in-game sound effect. On by default.
  * **Sound Effect ID** - the sound effect to play.
  * **Volume** - how loud the sound effect is.
* **Custom Sound** - play your own sound. Off by default.
  * **Volume** - how loud your sound is.

## Using a custom sound

1. Enable the plugin once, so it creates its data folder.
2. Place a WAV file named `notification.wav` in `.runelite/plugin-data/pot-notifier`. This folder is inside your RuneLite folder (`.runelite`, in your user folder).
3. Enable **Custom Sound** in the plugin's settings.

If the file is missing, or isn't a valid WAV file, no custom sound will play.
