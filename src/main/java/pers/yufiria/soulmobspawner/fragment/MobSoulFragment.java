package pers.yufiria.soulmobspawner.fragment;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;

public record MobSoulFragment(
    NamespacedKey mobId,
    Double probability,
    MobSoulFragmentItem item
) {

    public static MobSoulFragment fromConfig(String mobIdStr, ConfigurationSection config) {
        NamespacedKey mobId = NamespacedKey.fromString(mobIdStr);
        double probability = config.getDouble("probability");
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        MobSoulFragmentItem item = MobSoulFragmentItem.fromConfig(itemConfig);
        return new MobSoulFragment(mobId, probability, item);
    }

}
