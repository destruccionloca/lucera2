/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ru.catssoftware.gameserver.model.actor.status;

import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.actor.instance.L2NpcInstance;

public class NpcStatus extends CharStatus
{
	public NpcStatus(L2NpcInstance activeChar)
	{
		super(activeChar);
	}

	@Override
	void reduceHp0(double value, L2Character attacker, boolean awake, boolean isDOT, boolean directHp)
	{
		getActiveChar().addAttackerToAttackByList(attacker);
		super.reduceHp0(value, attacker, awake, isDOT, directHp);
	}

	@Override
	public L2NpcInstance getActiveChar()
	{
		return (L2NpcInstance) _activeChar;
	}
}