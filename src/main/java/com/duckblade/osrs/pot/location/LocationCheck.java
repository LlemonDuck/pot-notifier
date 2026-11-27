package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import net.runelite.api.Player;

public interface LocationCheck
{

	/**
	 * @return whether notifications are enabled for this location in the config
	 */
	boolean isEnabled(PotNotifierConfig config);

	/**
	 * @param regionId the region of the local player
	 * @return whether the region is part of this location
	 */
	boolean isInRegion(int regionId);

	/**
	 * @return whether the given player is someone we want to be notified about, e.g. in our raid party
	 */
	default boolean isPlayerOfInterest(Player p)
	{
		return true;
	}

}
