package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import javax.inject.Singleton;
import net.runelite.api.gameval.InterfaceID;

@Singleton
public class TfaLocationCheck
	extends RaidPartyWidgetBasedCheck
{

	@Override
	public boolean isEnabled(PotNotifierConfig config)
	{
		return config.locationTfa();
	}

	@Override
	public boolean isInRegion(int regionId)
	{
		// tfa lobby
		return regionId == 16470;
	}

	@Override
	public int getWidgetId()
	{
		return InterfaceID.TfaLobby.NAMES;
	}

}
