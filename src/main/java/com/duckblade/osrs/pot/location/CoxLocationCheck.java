package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.runelite.api.Client;
import net.runelite.api.FriendsChatManager;
import net.runelite.api.Player;
import net.runelite.client.util.Text;

@Singleton
public class CoxLocationCheck
	implements LocationCheck
{

	@Inject
	private Client client;

	@Override
	public boolean isEnabled(PotNotifierConfig config)
	{
		return config.locationCox();
	}

	@Override
	public boolean isInRegion(int regionId)
	{
		switch (regionId)
		{
			case 4919: // outside
			case 13393: // inside cox variant 1
			case 13137: // inside cox variant 2
				return true;

			default:
				return false;
		}
	}

	@Override
	public boolean isPlayerOfInterest(Player p)
	{
		// everyone inside a raid instance is on the local player's team
		if (client.getTopLevelWorldView().isInstance())
		{
			return true;
		}

		// otherwise (e.g. the lobby), only the local friends chat counts
		FriendsChatManager friendsChat = client.getFriendsChatManager();
		String name = p.getName();
		return friendsChat != null &&
			name != null &&
			friendsChat.findByName(Text.removeTags(name)) != null;
	}

}
