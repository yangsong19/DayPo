--
-- Table structure for table `survey`
--

DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `sid` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  `phone` varchar(15) default NULL,
  `email` varchar(255) default NULL,
  `age_range` enum('<18','18-35','>35') default NULL,
  `gender` enum('M','F') default NULL,
  `ctime` datetime NOT NULL,
  `recommend` varchar(20) default '',
  `whynot` varchar(100) default '',
  `share` varchar(100) default '',
  `bbmpin` varchar(100) default '',
  `twitterid` varchar(100) default '',
  PRIMARY KEY  (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4396215 DEFAULT CHARSET=utf8;

--
-- Table structure for table `survey_interest`
--

DROP TABLE IF EXISTS `survey_interest`;
CREATE TABLE `survey_interest` (
  `id` int(11) NOT NULL auto_increment,
  `sid` int(11) default NULL,
  `inid` int(11) default NULL,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3178807 DEFAULT CHARSET=utf8;

--
-- Table structure for table `t1`
--

DROP TABLE IF EXISTS `t1`;
CREATE TABLE `t1` (
  `x` int(11) NOT NULL,
  `y` varchar(32) default NULL,
  `z` int(11) default NULL,
  PRIMARY KEY  (`x`),
  KEY `z` (`z`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `takemeback_display_log`
--

DROP TABLE IF EXISTS `takemeback_display_log`;
CREATE TABLE `takemeback_display_log` (
  `id` int(11) NOT NULL auto_increment,
  `userid` int(11) default NULL,
  `pageurl` varchar(500) default NULL,
  `takemebackurl` varchar(500) default NULL,
  `logtime` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `siteid` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76538726 DEFAULT CHARSET=utf8;

--
-- Table structure for table `taxonomy`
--

DROP TABLE IF EXISTS `taxonomy`;
CREATE TABLE `taxonomy` (
  `taxonomy_id` int(10) unsigned NOT NULL auto_increment,
  `category` varchar(100) NOT NULL,
  `subcategory` varchar(100) default NULL,
  `search_term` varchar(1000) NOT NULL,
  `second_category` varchar(100) default NULL,
  PRIMARY KEY  (`taxonomy_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8161 DEFAULT CHARSET=utf8;

--
-- Table structure for table `telkomsel_sub_activity`
--

DROP TABLE IF EXISTS `telkomsel_sub_activity`;
CREATE TABLE `telkomsel_sub_activity` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `userId` int(10) unsigned NOT NULL,
  `sid` bigint(20) unsigned NOT NULL,
  `msisdn` varchar(20) NOT NULL,
  `key_code` varchar(50) NOT NULL,
  `cid` varchar(45) NOT NULL,
  `size` float NOT NULL,
  `result` tinyint(4) NOT NULL default '0',
  `time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Table structure for table `templete`
--

DROP TABLE IF EXISTS `templete`;
CREATE TABLE `templete` (
  `templeteId` varchar(255) NOT NULL default '',
  `style` varchar(4000) NOT NULL,
  `header` varchar(1000) NOT NULL,
  `footer` varchar(1000) NOT NULL,
  PRIMARY KEY  (`templeteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `tiny_url`
--

DROP TABLE IF EXISTS `tiny_url`;
CREATE TABLE `tiny_url` (
  `tiny_url_id` int(10) unsigned NOT NULL,
  `url` varchar(1000) NOT NULL,
  `ctime` datetime NOT NULL default '1970-01-01 00:00:00',
  `atime` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`tiny_url_id`),
  KEY `url` (`url`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `tksl_content_proxy`
--

DROP TABLE IF EXISTS `tksl_content_proxy`;
CREATE TABLE `tksl_content_proxy` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `cpid` int(10) unsigned NOT NULL,
  `baselink` varchar(500) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Table structure for table `tmp1`
--

DROP TABLE IF EXISTS `tmp1`;
CREATE TABLE `tmp1` (
  `contentId` int(10) unsigned NOT NULL default '0',
  UNIQUE KEY `cid` (`contentId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Table structure for table `tmp12`
--

DROP TABLE IF EXISTS `tmp12`;
CREATE TABLE `tmp12` (
  `contentId` int(10) unsigned NOT NULL default '0',
  `url` varbinary(2000) default NULL,
  UNIQUE KEY `contentId` (`contentId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `tmp2`
--

DROP TABLE IF EXISTS `tmp2`;
CREATE TABLE `tmp2` (
  `back_home_text` text
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Table structure for table `tmp3`
--

DROP TABLE IF EXISTS `tmp3`;
CREATE TABLE `tmp3` (
  `contentId` int(10) unsigned NOT NULL default '0',
  `description` varchar(2000) character set utf8 NOT NULL,
  `fullDescription` varchar(2000) character set utf8 default NULL,
  `source` varchar(50) character set utf8 default NULL,
  `duration` varchar(10) character set utf8 NOT NULL default '',
  `url` varbinary(2000) default NULL,
  `iconUrl` varchar(2000) character set utf8 default NULL,
  `insertDate` timestamp NOT NULL default '0000-00-00 00:00:00',
  `pageurl` varbinary(2000) NOT NULL,
  `pagehash` bigint(20) unsigned NOT NULL,
  `flags` tinyint(1) unsigned default NULL,
  `hiresurl` varbinary(2000) default NULL,
  `youtube_streaming_url` varbinary(2000) default NULL,
  `ext_int_1` int(10) unsigned default NULL,
  `ext_int_2` int(10) unsigned default NULL,
  `ext_varchar_1` varchar(100) character set utf8 default NULL,
  `ext_varchar_2` varchar(200) character set utf8 default NULL,
  `ext_varchar_3` varchar(400) character set utf8 default NULL,
  `ext_varchar_4` varchar(400) character set utf8 collate utf8_bin default NULL,
  `ext_varchar_5` varchar(2000) character set utf8 default NULL,
  `ext_varchar_6` varchar(2000) character set utf8 default NULL,
  `ext_varchar_7` varchar(2000) character set utf8 collate utf8_bin default NULL,
  `ext_varchar_8` varchar(2000) character set utf8 collate utf8_bin default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `tmp4`
--

DROP TABLE IF EXISTS `tmp4`;
CREATE TABLE `tmp4` (
  `contentId` int(10) unsigned NOT NULL default '0',
  `description` varchar(2000) character set utf8 NOT NULL,
  `fullDescription` varchar(2000) character set utf8 default NULL,
  `source` varchar(50) character set utf8 default NULL,
  `duration` varchar(10) character set utf8 NOT NULL default '',
  `url` varbinary(2000) default NULL,
  `iconUrl` varchar(2000) character set utf8 default NULL,
  `insertDate` timestamp NOT NULL default '0000-00-00 00:00:00',
  `pageurl` varbinary(2000) NOT NULL,
  `pagehash` bigint(20) unsigned NOT NULL,
  `flags` tinyint(1) unsigned default NULL,
  `hiresurl` varbinary(2000) default NULL,
  `youtube_streaming_url` varbinary(2000) default NULL,
  `ext_int_1` int(10) unsigned default NULL,
  `ext_int_2` int(10) unsigned default NULL,
  `ext_varchar_1` varchar(100) character set utf8 default NULL,
  `ext_varchar_2` varchar(200) character set utf8 default NULL,
  `ext_varchar_3` varchar(400) character set utf8 default NULL,
  `ext_varchar_4` varchar(400) character set utf8 collate utf8_bin default NULL,
  `ext_varchar_5` varchar(2000) character set utf8 default NULL,
  `ext_varchar_6` varchar(2000) character set utf8 default NULL,
  `ext_varchar_7` varchar(2000) character set utf8 collate utf8_bin default NULL,
  `ext_varchar_8` varchar(2000) character set utf8 collate utf8_bin default NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `tmp5`
--

DROP TABLE IF EXISTS `tmp5`;
CREATE TABLE `tmp5` (
  `contentId` int(10) unsigned NOT NULL default '0',
  `source` varchar(50) character set utf8 default NULL,
  `source_name` varchar(50) character set utf8 NOT NULL,
  UNIQUE KEY `contentId` (`contentId`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Table structure for table `top100`
--

DROP TABLE IF EXISTS `top100`;
CREATE TABLE `top100` (
  `contentId` int(10) unsigned NOT NULL default '0',
  `topDate` datetime NOT NULL default '2007-06-15 00:00:00',
  `accessCount` int(10) unsigned NOT NULL default '0',
  `rank` tinyint(4) unsigned NOT NULL default '0',
  `description` varchar(2000) NOT NULL,
  `duration` varchar(10) NOT NULL default '',
  `url` varbinary(2000) default NULL,
  `iconUrl` varchar(2000) default NULL,
  `insertDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `pageurl` varbinary(2000) NOT NULL,
  UNIQUE KEY `topDate` (`contentId`,`topDate`),
  UNIQUE KEY `rankDate` (`topDate`,`rank`),
  KEY `content_idx` (`pageurl`(767)),
  KEY `content_idx2` (`url`(767))
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_channel`
--

DROP TABLE IF EXISTS `top_channel`;
CREATE TABLE `top_channel` (
  `channel_id` int(11) NOT NULL auto_increment,
  `channel_name` varchar(50) default NULL,
  `region_code_id` int(10) default NULL,
  `show_name` varchar(50) default NULL,
  PRIMARY KEY  (`channel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_channel_map`
--

DROP TABLE IF EXISTS `top_channel_map`;
CREATE TABLE `top_channel_map` (
  `current_channelid` varchar(20) default NULL,
  `top_channel_id` int(11) default NULL,
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=821 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_channel_playlist`
--

DROP TABLE IF EXISTS `top_channel_playlist`;
CREATE TABLE `top_channel_playlist` (
  `top_channel_id` int(11) NOT NULL default '0',
  `playlist_id` int(11) NOT NULL default '0',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `channel_playlist` (`top_channel_id`,`playlist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_channel_playlist_201206`
--

DROP TABLE IF EXISTS `top_channel_playlist_201206`;
CREATE TABLE `top_channel_playlist_201206` (
  `top_channel_id` int(11) NOT NULL default '0',
  `playlist_id` int(11) NOT NULL default '0',
  `id` int(11) NOT NULL auto_increment,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `channel_playlist` (`top_channel_id`,`playlist_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1300 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_search_term`
--

DROP TABLE IF EXISTS `top_search_term`;
CREATE TABLE `top_search_term` (
  `search_term_id` int(10) unsigned NOT NULL auto_increment,
  `search_term` varchar(100) NOT NULL,
  `community_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`search_term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2497 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_search_term_list`
--

DROP TABLE IF EXISTS `top_search_term_list`;
CREATE TABLE `top_search_term_list` (
  `top_search_term_list_id` int(10) unsigned NOT NULL auto_increment,
  `search_term` varchar(100) NOT NULL,
  `display_text` varchar(100) NOT NULL,
  `community_id` int(10) unsigned NOT NULL,
  `display_order` int(10) unsigned default NULL,
  `sid` int(10) default '-1',
  PRIMARY KEY  (`top_search_term_list_id`),
  KEY `top_search_term_list_fk` (`community_id`),
  KEY `sid` (`sid`),
  CONSTRAINT `top_search_term_list_fk` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16457 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_search_terms`
--

DROP TABLE IF EXISTS `top_search_terms`;
CREATE TABLE `top_search_terms` (
  `search_term_id` int(10) unsigned NOT NULL auto_increment,
  `search_term` varchar(100) NOT NULL,
  `region_code_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`search_term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3130 DEFAULT CHARSET=utf8;

--
-- Table structure for table `top_search_trends`
--

DROP TABLE IF EXISTS `top_search_trends`;
CREATE TABLE `top_search_trends` (
  `trend_id` int(10) unsigned NOT NULL auto_increment,
  `term` varchar(100) NOT NULL,
  `countryA2` char(2) NOT NULL,
  `countryName` varchar(255) NOT NULL,
  PRIMARY KEY  (`trend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `toprating`
--

DROP TABLE IF EXISTS `toprating`;
CREATE TABLE `toprating` (
  `cid` bigint(20) unsigned NOT NULL default '0',
  `norating` int(10) unsigned NOT NULL default '0',
  UNIQUE KEY `cid` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `transcoder`
--

DROP TABLE IF EXISTS `transcoder`;
CREATE TABLE `transcoder` (
  `transcoderId` int(10) unsigned NOT NULL default '0',
  `ip` char(15) NOT NULL default '',
  `loads` int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (`transcoderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `twitter_user`
--

DROP TABLE IF EXISTS `twitter_user`;
CREATE TABLE `twitter_user` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `userId` varchar(45) NOT NULL,
  `ttUser` varchar(255) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1779 DEFAULT CHARSET=utf8;

--
-- Table structure for table `UA_Hardware`
--

DROP TABLE IF EXISTS `UA_Hardware`;
CREATE TABLE `UA_Hardware` (
  `UAhash` varchar(32) NOT NULL default '',
  `Vendor` varchar(255) default NULL,
  `Model` varchar(255) default NULL,
  `CPU` varchar(255) default NULL,
  `ColorCapable` varchar(255) default NULL,
  `BitsPerPixel` int(11) default NULL,
  `ScreenWidth` int(11) default NULL,
  `ScreenHeight` int(11) default NULL,
  PRIMARY KEY  (`UAhash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `ua_media`
--

DROP TABLE IF EXISTS `ua_media`;
CREATE TABLE `ua_media` (
  `ua` varchar(255) NOT NULL,
  `media` varchar(10) NOT NULL,
  UNIQUE KEY `ua_media_idx` (`ua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `ua_screen`
--

DROP TABLE IF EXISTS `ua_screen`;
CREATE TABLE `ua_screen` (
  `ua` varchar(255) NOT NULL,
  `width` int(10) unsigned NOT NULL,
  `touch` char(5) NOT NULL,
  UNIQUE KEY `ua_screen_idx` (`ua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `unknown_ua_media`
--

DROP TABLE IF EXISTS `unknown_ua_media`;
CREATE TABLE `unknown_ua_media` (
  `ua` varchar(255) NOT NULL,
  `media` varchar(10) NOT NULL,
  UNIQUE KEY `ua_media_idx` (`ua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `unknown_ua_screen`
--

DROP TABLE IF EXISTS `unknown_ua_screen`;
CREATE TABLE `unknown_ua_screen` (
  `ua` varchar(255) NOT NULL,
  `width` int(10) unsigned NOT NULL,
  `touch` char(5) NOT NULL,
  UNIQUE KEY `ua_media_idx` (`ua`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `upload_content`
--

DROP TABLE IF EXISTS `upload_content`;
CREATE TABLE `upload_content` (
  `ucid` bigint(20) unsigned NOT NULL auto_increment,
  `cid` bigint(20) unsigned NOT NULL,
  `email` varchar(100) NOT NULL default '',
  `caption` varchar(100) NOT NULL default 'vuclip',
  `category` varchar(100) default NULL,
  `description` varchar(2000) default NULL,
  `keyword` varchar(200) default NULL,
  `bitflag` int(10) NOT NULL default '0',
  `flags` int(10) NOT NULL default '0',
  `source` varchar(50) default NULL,
  `pageurl` varbinary(2000) default NULL,
  `thumburl` varbinary(2000) default NULL,
  `location` varchar(200) default NULL,
  `status` int(10) NOT NULL default '0',
  `pagehash` bigint(20) unsigned NOT NULL,
  `fileSize` bigint(20) unsigned NOT NULL,
  `duration` int(10) NOT NULL default '0',
  `thumbinfo` int(10) NOT NULL default '0',
  `create_time` datetime NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY  (`ucid`),
  KEY `cid` (`cid`),
  KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `upload_user`
--

DROP TABLE IF EXISTS `upload_user`;
CREATE TABLE `upload_user` (
  `ucid` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL,
  `cid` bigint(20) unsigned NOT NULL,
  `email` varchar(100) NOT NULL default '',
  `status` int(10) NOT NULL default '0',
  `update_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `flags` int(10) NOT NULL default '0',
  `source` varchar(50) default NULL,
  PRIMARY KEY  (`ucid`),
  KEY `userId` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=REDUNDANT;

--
-- Table structure for table `upload_user_old`
--

DROP TABLE IF EXISTS `upload_user_old`;
CREATE TABLE `upload_user_old` (
  `ucid` bigint(20) unsigned NOT NULL,
  `uid` bigint(20) unsigned NOT NULL,
  `cid` bigint(20) unsigned NOT NULL,
  `email` varchar(100) NOT NULL default '',
  `status` int(10) NOT NULL default '0',
  `update_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`ucid`),
  KEY `userId` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=REDUNDANT;

--
-- Table structure for table `usage_count`
--

DROP TABLE IF EXISTS `usage_count`;
CREATE TABLE `usage_count` (
  `name` varchar(255) NOT NULL default '',
  `count` bigint(20) NOT NULL default '0',
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `userInfo`
--

DROP TABLE IF EXISTS `userInfo`;
CREATE TABLE `userInfo` (
  `userId` int(10) unsigned NOT NULL auto_increment,
  `site` varchar(10) NOT NULL default '',
  `originate` varchar(50) NOT NULL default '',
  `msisdn` varchar(50) NOT NULL default '',
  `ip` varchar(15) NOT NULL default '',
  `ua` varchar(255) NOT NULL default '',
  `filter` varchar(10) default NULL,
  `insertDate` datetime NOT NULL default '0000-00-00 00:00:00',
  `show_preview` tinyint(4) NOT NULL default '1',
  `show_regular_res` tinyint(4) NOT NULL default '1',
  `show_high_res` tinyint(4) NOT NULL default '1',
  `show_parts` tinyint(4) NOT NULL default '1',
  `show_audio_only` tinyint(4) NOT NULL default '1',
  `show_streaming` tinyint(4) NOT NULL default '1',
  `show_streaming_hi_res` tinyint(4) NOT NULL default '0',
  PRIMARY KEY  (`userId`),
  KEY `date` (`insertDate`),
  KEY `ip` (`ip`)
) ENGINE=InnoDB AUTO_INCREMENT=588629517 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_alert_confirm_sms`
--

DROP TABLE IF EXISTS `user_alert_confirm_sms`;
CREATE TABLE `user_alert_confirm_sms` (
  `user_id` int(10) unsigned NOT NULL,
  `count` int(10) unsigned NOT NULL default '1',
  PRIMARY KEY  (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_community`
--

DROP TABLE IF EXISTS `user_community`;
CREATE TABLE `user_community` (
  `user_community_id` int(10) unsigned NOT NULL auto_increment,
  `user_id` int(10) unsigned NOT NULL,
  `community_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`user_community_id`),
  UNIQUE KEY `user_community_uk` (`user_id`),
  KEY `user_community_fk` (`community_id`),
  CONSTRAINT `user_community_fk` FOREIGN KEY (`community_id`) REFERENCES `community` (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1352481 DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_msisdn`
--

DROP TABLE IF EXISTS `user_msisdn`;
CREATE TABLE `user_msisdn` (
  `userId` int(10) unsigned NOT NULL,
  `msisdn` varchar(50) NOT NULL,
  `carrier` varchar(15) NOT NULL default '',
  `insertDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`msisdn`),
  KEY `FK_user_msisdn_1` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `user_msisdn1`
--

DROP TABLE IF EXISTS `user_msisdn1`;
CREATE TABLE `user_msisdn1` (
  `userId` bigint(20) unsigned NOT NULL,
  `msisdn` varchar(50) NOT NULL,
  `carrier` varchar(15) NOT NULL default '',
  `insertDate` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`msisdn`),
  KEY `user_msisdn_1` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_preference`
--

DROP TABLE IF EXISTS `user_preference`;
CREATE TABLE `user_preference` (
  `userId` int(10) unsigned NOT NULL default '0',
  `show_long_video` tinyint(1) default NULL,
  `num_videos_per_page` int(10) unsigned default NULL,
  `theme` enum('white','black','pink','green','blue','red') default NULL,
  `region_code_id` int(10) unsigned default NULL,
  `home_channel_title` varchar(256) default '',
  `search_keyword` varchar(256) default '',
  `sort_option` tinyint(1) NOT NULL default '1',
  `showSearchBar` tinyint(1) NOT NULL default '0',
  `showRecommend` tinyint(1) NOT NULL default '0',
  `postFlag` int(11) NOT NULL default '127',
  PRIMARY KEY  (`userId`),
  KEY `user_preference_region_fk` (`region_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Table structure for table `user_preference_20110329`
--

DROP TABLE IF EXISTS `user_preference_20110329`;
CREATE TABLE `user_preference_20110329` (
  `userId` int(10) unsigned NOT NULL default '0',
  `show_long_video` tinyint(1) default NULL,
  `num_videos_per_page` int(10) unsigned default NULL,
  `theme` enum('white','black','pink','green','blue','red') default NULL,
  `region_code_id` int(10) unsigned default NULL,
  `home_channel_title` varchar(256) default '',
  `search_keyword` varchar(256) default '',
  `sort_option` tinyint(1) NOT NULL default '1',
  PRIMARY KEY  (`userId`),
  KEY `user_preference_region_fk` (`region_code_id`),
  CONSTRAINT `user_preference_region_fk` FOREIGN KEY (`region_code_id`) REFERENCES `region_code` (`region_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `user_preference_old`
--

DROP TABLE IF EXISTS `user_preference_old`;
CREATE TABLE `user_preference_old` (
  `userId` int(10) unsigned NOT NULL default '0',
  `show_long_video` tinyint(1) default NULL,
  `num_videos_per_page` int(10) unsigned default NULL,
  `theme` enum('white','black','pink','green','blue','red') default NULL,
  `region_code_id` int(10) unsigned default NULL,
  `home_channel_title` varchar(256) default '',
  `search_keyword` varchar(256) default '',
  `sort_option` tinyint(1) NOT NULL default '1',
  `showSearchBar` tinyint(1) NOT NULL default '0',
  `showRecommend` tinyint(1) NOT NULL default '0',
  PRIMARY KEY  (`userId`),
  KEY `user_preference_region_fk` (`region_code_id`),
  CONSTRAINT `user_preference_old_ibfk_2` FOREIGN KEY (`region_code_id`) REFERENCES `region_code` (`region_code_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Table structure for table `user_search_history`
--

DROP TABLE IF EXISTS `user_search_history`;
CREATE TABLE `user_search_history` (
  `uid` int(10) unsigned NOT NULL,
  `flag` varchar(1) NOT NULL,
  PRIMARY KEY  (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Table structure for table `user_session`
--

DROP TABLE IF EXISTS `user_session`;
CREATE TABLE `user_session` (
  `userId` int(10) unsigned NOT NULL,
  `session_id` bigint(20) unsigned NOT NULL,
  `status` enum('NL','LI','LO') default NULL,
  PRIMARY KEY  (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_device_config`
--

DROP TABLE IF EXISTS `via_device_config`;
CREATE TABLE `via_device_config` (
  `via_device_config_id` int(10) unsigned NOT NULL auto_increment,
  `via_user_config_id` int(10) unsigned NOT NULL,
  `make` varchar(50) default NULL,
  `model` varchar(50) default NULL,
  PRIMARY KEY  (`via_device_config_id`),
  KEY `via_device_config_fk` (`via_user_config_id`),
  CONSTRAINT `via_device_config_fk` FOREIGN KEY (`via_user_config_id`) REFERENCES `via_user_config` (`via_user_config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_feature`
--

DROP TABLE IF EXISTS `via_feature`;
CREATE TABLE `via_feature` (
  `via_feature_id` int(10) unsigned NOT NULL auto_increment,
  `definition` varchar(500) default NULL,
  `is_deleted` tinyint(4) default '0',
  `via_user_config_id` int(10) unsigned NOT NULL,
  `user_select_type` tinyint(4) default '0',
  `start_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `stop_time` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`via_feature_id`),
  KEY `via_feature_fk` (`via_user_config_id`),
  CONSTRAINT `via_feature_fk` FOREIGN KEY (`via_user_config_id`) REFERENCES `via_user_config` (`via_user_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_feature_page`
--

DROP TABLE IF EXISTS `via_feature_page`;
CREATE TABLE `via_feature_page` (
  `via_feature_page_id` int(10) unsigned NOT NULL auto_increment,
  `via_feature_id` int(10) unsigned NOT NULL,
  `page` varchar(50) default NULL,
  PRIMARY KEY  (`via_feature_page_id`),
  KEY `via_feature_page_fk` (`via_feature_id`),
  CONSTRAINT `via_feature_page_fk` FOREIGN KEY (`via_feature_id`) REFERENCES `via_feature` (`via_feature_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_feature_user`
--

DROP TABLE IF EXISTS `via_feature_user`;
CREATE TABLE `via_feature_user` (
  `via_feature_user_id` int(10) unsigned NOT NULL auto_increment,
  `via_feature_id` int(10) unsigned NOT NULL,
  `via_user_type` tinyint(4) default '0',
  PRIMARY KEY  (`via_feature_user_id`),
  UNIQUE KEY `via_feature_user_uk` (`via_feature_id`,`via_user_type`),
  CONSTRAINT `via_feature_user_fk` FOREIGN KEY (`via_feature_id`) REFERENCES `via_feature` (`via_feature_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_user_config`
--

DROP TABLE IF EXISTS `via_user_config`;
CREATE TABLE `via_user_config` (
  `via_user_config_id` int(10) unsigned NOT NULL auto_increment,
  `country` varchar(500) default NULL,
  `carrier` varchar(200) default NULL,
  `partner_site` varchar(1000) default NULL,
  `cap` int(10) unsigned NOT NULL,
  `duration_per_user` int(10) unsigned default NULL,
  `new_status` tinyint(4) default '0',
  `auth_status` tinyint(4) default '0',
  PRIMARY KEY  (`via_user_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `via_user_list`
--

DROP TABLE IF EXISTS `via_user_list`;
CREATE TABLE `via_user_list` (
  `via_user_list_id` int(10) unsigned NOT NULL auto_increment,
  `via_feature_user_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `start_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`via_user_list_id`),
  UNIQUE KEY `via_user_list_uk` (`user_id`),
  KEY `via_user_list_fk` (`via_feature_user_id`),
  CONSTRAINT `via_user_list_fk` FOREIGN KEY (`via_feature_user_id`) REFERENCES `via_feature_user` (`via_feature_user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8;

--
-- Table structure for table `video_replacetitle`
--

DROP TABLE IF EXISTS `video_replacetitle`;
CREATE TABLE `video_replacetitle` (
  `cid` int(10) unsigned NOT NULL,
  `title` varchar(2000) NOT NULL default '',
  PRIMARY KEY  (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `vodafone_sn`
--

DROP TABLE IF EXISTS `vodafone_sn`;
CREATE TABLE `vodafone_sn` (
  `siteId` varchar(255) NOT NULL default '',
  KEY `vo_fk1` (`siteId`),
  CONSTRAINT `vo_fk1` FOREIGN KEY (`siteId`) REFERENCES `site` (`siteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `vucategory`
--

DROP TABLE IF EXISTS `vucategory`;
CREATE TABLE `vucategory` (
  `vucategory_id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `current_folder_id` int(10) unsigned NOT NULL,
  `static_folder_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`vucategory_id`),
  KEY `vucategory_fk1` (`current_folder_id`),
  KEY `vucategory_fk2` (`static_folder_id`),
  CONSTRAINT `vucategory_fk1` FOREIGN KEY (`current_folder_id`) REFERENCES `folder` (`folderId`),
  CONSTRAINT `vucategory_fk2` FOREIGN KEY (`static_folder_id`) REFERENCES `folder` (`folderId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vuchnl`
--

DROP TABLE IF EXISTS `vuchnl`;
CREATE TABLE `vuchnl` (
  `vuchnl_id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(100) default NULL,
  `email` varchar(100) default NULL,
  `last_alert_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `content_partner_id` int(10) unsigned NOT NULL,
  `country` varchar(500) default NULL,
  `carrier` varchar(200) default NULL,
  `partner_site` varchar(1000) default NULL,
  `vutemplate_id` int(10) unsigned NOT NULL,
  `thumbnail` varchar(45) NOT NULL,
  PRIMARY KEY  (`vuchnl_id`),
  UNIQUE KEY `vuchnl_name_uk` (`name`),
  UNIQUE KEY `vuchnl_email_uk` (`email`),
  KEY `vuchnl_content_partner_fk` (`content_partner_id`),
  KEY `vuchnl_template_fk` (`vutemplate_id`),
  CONSTRAINT `vuchnl_content_partner_fk` FOREIGN KEY (`content_partner_id`) REFERENCES `content_partner` (`content_partner_id`),
  CONSTRAINT `vuchnl_template_fk` FOREIGN KEY (`vutemplate_id`) REFERENCES `vutemplate` (`vutemplate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vuchnl_alert`
--

DROP TABLE IF EXISTS `vuchnl_alert`;
CREATE TABLE `vuchnl_alert` (
  `vuchnl_alert_id` int(10) unsigned NOT NULL auto_increment,
  `vuchnl_alert_setup_id` int(10) unsigned NOT NULL,
  `vuchnl_id` int(10) unsigned NOT NULL,
  `vuchnl_content_id` int(10) unsigned NOT NULL,
  `content_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `deliver_status` enum('created','pending','delivered','failed') default NULL,
  `deliver_message` varchar(500) default NULL,
  `msisdn` varchar(20) default NULL,
  `message` varchar(200) default NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `dispatch_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `deliver_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `carrier_id` int(10) unsigned default NULL,
  `partner_site_id` int(10) unsigned default NULL,
  `country_id` char(2) default NULL,
  PRIMARY KEY  (`vuchnl_alert_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vuchnl_alert_setup`
--

DROP TABLE IF EXISTS `vuchnl_alert_setup`;
CREATE TABLE `vuchnl_alert_setup` (
  `vuchnl_alert_setup_id` int(10) unsigned NOT NULL auto_increment,
  `vuchnl_id` int(10) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `status` enum('pending','confirmed','inactive') default NULL,
  `msisdn` varchar(20) default NULL,
  `carrier_id` int(10) unsigned default NULL,
  `partner_site_id` int(10) unsigned default NULL,
  `country_id` char(2) default NULL,
  `create_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `confirm_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `last_alert_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `last_approval_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`vuchnl_alert_setup_id`),
  KEY `vuchnl_alert_setup_fk` (`vuchnl_id`),
  CONSTRAINT `vuchnl_alert_setup_fk` FOREIGN KEY (`vuchnl_id`) REFERENCES `vuchnl` (`vuchnl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vuchnl_content`
--

DROP TABLE IF EXISTS `vuchnl_content`;
CREATE TABLE `vuchnl_content` (
  `vuchnl_content_id` int(10) unsigned NOT NULL auto_increment,
  `vuchnl_id` int(10) unsigned NOT NULL,
  `content_id` int(10) unsigned NOT NULL,
  `insert_date` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `publicity` enum('public','private') default NULL,
  `approval_date` timestamp NOT NULL default '0000-00-00 00:00:00',
  `is_alerted` tinyint(3) unsigned default '0',
  PRIMARY KEY  (`vuchnl_content_id`),
  KEY `vuchnl_content_vuchnl_fk` (`vuchnl_id`),
  CONSTRAINT `vuchnl_content_vuchnl_fk` FOREIGN KEY (`vuchnl_id`) REFERENCES `vuchnl` (`vuchnl_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vufolder`
--

DROP TABLE IF EXISTS `vufolder`;
CREATE TABLE `vufolder` (
  `vufolder_id` int(10) unsigned NOT NULL auto_increment,
  `name` varchar(40) NOT NULL,
  `folder_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`vufolder_id`),
  KEY `vufolder_fk` (`folder_id`),
  CONSTRAINT `vufolder_fk` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`folderId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Table structure for table `vutemplate`
--

DROP TABLE IF EXISTS `vutemplate`;
CREATE TABLE `vutemplate` (
  `vutemplate_id` int(10) unsigned NOT NULL auto_increment,
  `template` varchar(200) default NULL,
  PRIMARY KEY  (`vutemplate_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Table structure for table `wapSamplerRequest`
--

DROP TABLE IF EXISTS `wapSamplerRequest`;
CREATE TABLE `wapSamplerRequest` (
  `userId` int(10) unsigned NOT NULL default '0',
  `contentId` int(10) unsigned NOT NULL default '0',
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  KEY `usage_fk1` (`userId`),
  KEY `usage_fk2` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `wapSamplerUsage`
--

DROP TABLE IF EXISTS `wapSamplerUsage`;
CREATE TABLE `wapSamplerUsage` (
  `userId` int(10) unsigned NOT NULL default '0',
  `contentId` int(10) unsigned NOT NULL default '0',
  `startTime` datetime NOT NULL default '0000-00-00 00:00:00',
  `endTime` datetime NOT NULL default '0000-00-00 00:00:00',
  KEY `usage_fk1` (`userId`),
  KEY `usage_fk2` (`contentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `widget`
--

DROP TABLE IF EXISTS `widget`;
CREATE TABLE `widget` (
  `widgetId` int(10) unsigned NOT NULL auto_increment,
  `url` varbinary(2000) NOT NULL,
  `type` int(11) NOT NULL default '0',
  `contentId` int(11) NOT NULL default '0',
  `ip` varchar(15) NOT NULL default '',
  `ua` varchar(255) NOT NULL default '',
  `date` datetime NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`widgetId`),
  KEY `widget_idx` (`url`(767))
) ENGINE=InnoDB AUTO_INCREMENT=5683 DEFAULT CHARSET=utf8;

--
-- Table structure for table `xid`
--

DROP TABLE IF EXISTS `xid`;
CREATE TABLE `xid` (
  `userId` int(10) unsigned NOT NULL default '0',
  `xid` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`xid`),
  KEY `xid_fk1` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `xl_sub_activity`
--

DROP TABLE IF EXISTS `xl_sub_activity`;
CREATE TABLE `xl_sub_activity` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `userId` int(10) unsigned NOT NULL,
  `sid` bigint(20) unsigned NOT NULL,
  `msisdn` varchar(20) NOT NULL,
  `key_code` varchar(50) NOT NULL,
  `cid` varchar(45) NOT NULL,
  `size` float NOT NULL,
  `result` tinyint(4) NOT NULL default '0',
  `time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2642 DEFAULT CHARSET=latin1;

--
-- Table structure for table `xsubno`
--

DROP TABLE IF EXISTS `xsubno`;
CREATE TABLE `xsubno` (
  `userId` int(10) unsigned NOT NULL default '0',
  `xsubno` varchar(255) NOT NULL default '',
  PRIMARY KEY  (`xsubno`),
  KEY `xsubno_fk1` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;