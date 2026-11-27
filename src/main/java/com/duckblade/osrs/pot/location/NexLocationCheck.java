package com.duckblade.osrs.pot.location;

import com.duckblade.osrs.pot.PotNotifierConfig;
import javax.inject.Singleton;

@Singleton
public class NexLocationCheck
	implements LocationCheck
{

	@Override
	public boolean isEnabled(PotNotifierConfig config)
	{
		return config.locationNex();
	}

	@Override
	public boolean isInRegion(int regionId)
	{
		// nex lobby
		return regionId == 11601;
	}

}
