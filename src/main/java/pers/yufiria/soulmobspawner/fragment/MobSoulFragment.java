package pers.yufiria.soulmobspawner.fragment;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public record MobSoulFragment(
    EntityType mobType,
    Double probability,
    MobSoulFragmentItem item
) {

    public static MobSoulFragment fromConfig(String mobTypeStr, ConfigurationSection config) {
        EntityType mobType = EntityType.valueOf(mobTypeStr.toUpperCase());
        double probability = config.getDouble("probability");
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        MobSoulFragmentItem item = MobSoulFragmentItem.fromConfig(itemConfig);
        return new MobSoulFragment(mobType, probability, item);
    }

}
