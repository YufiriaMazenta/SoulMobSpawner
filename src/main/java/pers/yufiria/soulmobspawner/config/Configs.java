package pers.yufiria.soulmobspawner.config;

import crypticlib.PlatformSide;
import crypticlib.config.ConfigHandler;
import crypticlib.config.node.impl.bukkit.ConfigSectionConfig;

@ConfigHandler(path = "config.yml", platforms = {PlatformSide.BUKKIT})
public class Configs {

    public static final ConfigSectionConfig SOUL_FRAGMENT_SETTINGS = new ConfigSectionConfig("soul_fragment_settings");

}
