package com.duckblade.osrs.pot.location;

import com.google.common.base.Strings;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.widgets.Widget;
import net.runelite.client.util.Text;

/**
 * For raid widgets (ToB, ToA, TFA) which show Member1<br>Member2<br>... in a widget.
 */
public abstract class RaidPartyWidgetBasedCheck
	implements LocationCheck
{

	@Inject
	private Client client;

	/**
	 * @return the id of the widget which lists the names of the party members
	 */
	protected abstract int getWidgetId();

	@Override
	public boolean isPlayerOfInterest(Player p)
	{
		String playerName = p.getName();
		if (playerName == null)
		{
			return false;
		}

		Widget partyMembersW = client.getWidget(getWidgetId());
		if (partyMembersW == null)
		{
			return false;
		}

		String namesRaw = partyMembersW.getText();
		if (Strings.isNullOrEmpty(namesRaw))
		{
			return false;
		}

		String normalizedPlayerName = Text.toJagexName(playerName);
		for (String name : namesRaw.split("<br>"))
		{
			String memberName = Text.toJagexName(Text.removeTags(name));
			if (normalizedPlayerName.equals(memberName))
			{
				return true;
			}
		}

		return false;
	}

}
