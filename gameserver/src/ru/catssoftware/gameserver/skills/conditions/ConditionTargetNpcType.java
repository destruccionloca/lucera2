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
package ru.catssoftware.gameserver.skills.conditions;

import ru.catssoftware.gameserver.skills.Env;

public class ConditionTargetNpcType extends Condition
{
	private final String[] _npcType;

	public ConditionTargetNpcType(String[] type)
	{
		_npcType = type;
	}

	@Override
	public boolean testImpl(Env env)
	{
		if (env.target == null)
			return false;

		for (int i = 0; i < _npcType.length;i++)
			if(env.target.getClass().getSimpleName().startsWith(_npcType[i]))
				return true;
		return false;
	}
}