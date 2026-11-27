package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import javax.inject.Singleton;
import net.runelite.api.gameval.InterfaceID;

@Singleton
public class ToaLocationCheck
	extends RaidPartyWidgetBasedCheck
{

	@Override
	public boolean isEnabled(PotNotifierConfig config)
	{
		return config.locationToa();
	}

	@Override
	public boolean isInRegion(int regionId)
	{
		// toa lobby
		return regionId == 13454;
	}

	@Override
	protected int getWidgetId()
	{
		return InterfaceID.ToaLobby.NAMES;
	}

}
