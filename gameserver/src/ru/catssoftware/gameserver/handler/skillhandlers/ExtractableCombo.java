package ru.catssoftware.gameserver.handler.skillhandlers;

import ru.catssoftware.gameserver.datatables.ItemTable;
import ru.catssoftware.gameserver.datatables.xml.ExtractableItemsData;
import ru.catssoftware.gameserver.handler.ISkillHandler;
import ru.catssoftware.gameserver.items.model.L2ExtractableItem;
import ru.catssoftware.gameserver.items.model.L2ExtractableProductItem;
import ru.catssoftware.gameserver.model.L2Character;
import ru.catssoftware.gameserver.model.L2Skill;
import ru.catssoftware.gameserver.model.actor.instance.L2PcInstance;
import ru.catssoftware.gameserver.network.SystemMessageId;
import ru.catssoftware.gameserver.network.serverpackets.SystemMessage;
import ru.catssoftware.gameserver.templates.skills.L2SkillType;
import ru.catssoftware.tools.random.Rnd;

public class ExtractableCombo implements ISkillHandler
{
	private static final L2SkillType[] SKILL_IDS = { L2SkillType.EXTRACTABLE_COMBO };

	public void useSkill(L2Character activeChar, L2Skill skill, L2Character... targets)
	{
		if (!(activeChar.isPlayer()))
			return;

		L2PcInstance player = (L2PcInstance)activeChar;
		int itemID = skill.getReferenceItemId();
		if (itemID == 0)
			return;

		L2ExtractableItem exitem = ExtractableItemsData.getInstance().getExtractableItem(itemID);

		if (exitem == null)
			return;

		int rndNum = Rnd.get(100), chanceFrom = 0;
		int[] createItemId = new int[120];
		int[] createAmount = new int[120];
		int ch=0;
		// calculate extraction
		for (L2ExtractableProductItem expi : exitem.getProductItemsArray())
		{
			int chance = expi.getChance();

			if (rndNum >= chanceFrom && rndNum <= chance + chanceFrom)
			{
				for (int i = 0; i < expi.getId().length; i++)
				{
					createItemId[ch+i] = expi.getId()[i];
					createAmount[ch+i] = expi.getAmmount()[i];
					ch++;
				}
			}
		}

		if (createItemId[0] <= 0 || createItemId.length == 0 )
		{
			player.sendPacket(SystemMessageId.NOTHING_INSIDE_THAT);
			return;
		}
		else
		{
			for (int i = 0; i < createItemId.length; i++)
			{
				if (createItemId[i] > 0)
				{

					if (ItemTable.getInstance().createDummyItem(createItemId[i]) == null)
					{
						_log.warn("createItemID " + createItemId[i] + " doesn't have template!");
						player.sendPacket(SystemMessageId.NOTHING_INSIDE_THAT);
						return;
					}

					if (ItemTable.getInstance().createDummyItem(createItemId[i]).isStackable())
						player.addItem("Extract", createItemId[i], createAmount[i], targets[0], false);
					else
					{
						for (int j = 0; j < createAmount[i]; j++)
							player.addItem("Extract", createItemId[i], 1, targets[0], false);
					}
					SystemMessage sm = new SystemMessage(SystemMessageId.EARNED_S2_S1_S);
					SystemMessage sm2 = new SystemMessage(SystemMessageId.EARNED_S1_ADENA);
					if (createItemId[i] == 57)
					{
						sm2.addNumber(createAmount[i]);
						player.sendPacket(sm2);
					}
					else
					{
						sm.addItemName(createItemId[i]);
						if (createAmount[i] > 1)
							sm.addNumber(createAmount[i]);
						player.sendPacket(sm);
					}
				}
			}
		}
	}
	
	public L2SkillType[] getSkillIds()
	{
		return SKILL_IDS;
	}
}