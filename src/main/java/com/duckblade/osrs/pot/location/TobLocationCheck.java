package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import javax.inject.Singleton;
import net.runelite.api.gameval.InterfaceID;

@Singleton
public class TobLocationCheck
	extends RaidPartyWidgetBasedCheck
{

	@Override
	public boolean isEnabled(PotNotifierConfig config)
	{
		return config.locationTob();
	}

	@Override
	public boolean isInRegion(int regionId)
	{
		// tob lobby
		return regionId == 14642;
	}

	@Override
	protected int getWidgetId()
	{
		return InterfaceID.TobHud.NAMES;
	}

}
