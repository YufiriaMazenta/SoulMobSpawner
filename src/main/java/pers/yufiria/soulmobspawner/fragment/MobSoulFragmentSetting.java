package pers.yufiria.soulmobspawner.fragment;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public record MobSoulFragmentSetting(
    EntityType mobType,
    Double probability,
    MobSoulFragmentItem item
) {

    public static MobSoulFragmentSetting fromConfig(String mobTypeStr, ConfigurationSection config) {
        EntityType mobType = EntityType.valueOf(mobTypeStr.toUpperCase());
        double probability = config.getDouble("probability");
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        MobSoulFragmentItem item = MobSoulFragmentItem.fromConfig(mobType, itemConfig);
        return new MobSoulFragmentSetting(mobType, probability, item);
    }

}
