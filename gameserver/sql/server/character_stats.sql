DROP TABLE IF EXISTS `character_stats`;
CREATE TABLE `character_stats` (
  `id` decimal(11,0) NOT NULL,
  `totalKarma` double(255,0) NOT NULL DEFAULT '0',
  `totalPlayerKills` decimal(25,0) NOT NULL DEFAULT '0',
  `totalKills` double(255,0) NOT NULL DEFAULT '0',
  `totalMonKills` double(255,0) NOT NULL DEFAULT '0',
  `totalDamageDealt` double(255,0) NOT NULL DEFAULT '0',
  `totalDamageTaken` double(255,0) NOT NULL DEFAULT '0',
  `totalDied` decimal(25,0) NOT NULL DEFAULT '0',
  `totalMonsterDeaths` decimal(25,0) NOT NULL DEFAULT '0',
  `totalPlayerDeaths` decimal(25,0) NOT NULL DEFAULT '0',
  `totalPKDeaths` decimal(25,0) NOT NULL DEFAULT '0',
  `totalPvPDeaths` decimal(25,0) NOT NULL DEFAULT '0',
  `totalHealthLost` double(255,0) NOT NULL DEFAULT '0',
  `totalHealthGained` double(255,0) NOT NULL DEFAULT '0',
  `totalSpellsCasted` double(255,0) NOT NULL DEFAULT '0',
  `totalTimesAttacked` double(255,0) NOT NULL DEFAULT '0',
  `totalPlayTime` double(255,0) NOT NULL DEFAULT '0',
  `totalXPGained` double(255,0) NOT NULL DEFAULT '0',
  `totalXPLost` double(255,0) NOT NULL DEFAULT '0',
  `totalDistanceTravelled` double(255,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;