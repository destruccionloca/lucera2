package ru.catssoftware.gameserver.handler.skillhandlers;

import ru.catssoftware.gameserver.handler.ISkillHandler;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Fishing;
import ru.catssoftware.gameserver.model.L2ItemInstance;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.SystemMessageId;
import ru.catssoftware.gameserver.network.serverpackets.ActionFailed;
import ru.catssoftware.gameserver.templates.item.L2Weapon;
import ru.catssoftware.gameserver.templates.skills.L2SkillType;

public class FishingSkill implements ISkillHandler
{
	private static final L2SkillType[]	SKILL_IDS	= { L2SkillType.PUMPING, L2SkillType.REELING };

	public void useSkill(L2Character activeChar, L2Skill skill, L2Character... targets)
	{
		if (!(activeChar instanceof L2PcInstance))
			return;

		L2PcInstance player = (L2PcInstance) activeChar;

		L2Fishing fish = player.getFishCombat();
		if (fish == null)
		{
			if (skill.getSkillType() == L2SkillType.PUMPING)
			{
				//Pumping skill is available only while fishing
				player.sendPacket(SystemMessageId.CAN_USE_PUMPING_ONLY_WHILE_FISHING);
			}
			else if (skill.getSkillType() == L2SkillType.REELING)
			{
				//Reeling skill is available only while fishing
				player.sendPacket(SystemMessageId.CAN_USE_REELING_ONLY_WHILE_FISHING);
			}
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return;
		}
		L2Weapon weaponItem = player.getActiveWeaponItem();
		L2ItemInstance weaponInst = activeChar.getActiveWeaponInstance();
		if (weaponInst == null || weaponItem == null)
			return;
		int SS = 1;
		int pen = 0;
		if (weaponInst.getChargedFishshot())
			SS = 2;
		double gradebonus = 1 + weaponItem.getCrystalType() * 0.1;
		int dmg = (int) (skill.getPower() * gradebonus * SS);
		if (player.getSkillLevel(1315) <= skill.getLevel() - 2) //1315 - Fish Expertise
		{
			//Penalty
			player.sendPacket(SystemMessageId.REELING_PUMPING_3_LEVELS_HIGHER_THAN_FISHING_PENALTY);
			pen = 50;
			int penatlydmg = dmg - pen;
			if (player.isGM())
				player.sendMessage("Dmg w/o penalty = " + dmg);
			dmg = penatlydmg;
		}
		if (SS > 1)
		{
			weaponInst.setChargedFishshot(false);
		}
		if (skill.getSkillType() == L2SkillType.REELING)//Realing
		{
			fish.useRealing(dmg, pen);
		}
		else
		//Pumping
		{
			fish.usePomping(dmg, pen);
		}
	}

	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}
