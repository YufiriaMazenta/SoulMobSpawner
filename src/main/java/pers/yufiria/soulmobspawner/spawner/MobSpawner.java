package pers.yufiria.soulmobspawner.spawner;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;

public record MobSpawner(
    EntityType mobType,
    Integer maxDurability
) {

    public static MobSpawner fromConfig(ConfigurationSection config) {
        //TODO
    }

}
