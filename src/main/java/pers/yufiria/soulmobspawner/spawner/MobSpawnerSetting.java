package pers.yufiria.soulmobspawner.spawner;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pers.yufiria.soulmobspawner.SoulMobSpawner;

public record MobSpawnerSetting(
    EntityType mobType,
    MobSpawnerItem spawnerItem,
    Integer maxDurability
) {

    public static final NamespacedKey MOB_SPAWNER_ID_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_id");

    public static MobSpawnerSetting fromConfig(String mobTypeStr, ConfigurationSection config) {
        EntityType mobType = EntityType.valueOf(mobTypeStr.toUpperCase());
        Integer maxDurability = config.getInt("max_durability");
        ConfigurationSection itemConfig = config.getConfigurationSection("item");
        MobSpawnerItem spawnerItem = MobSpawnerItem.fromConfig(mobType, itemConfig);
        return new MobSpawnerSetting(mobType, spawnerItem, maxDurability);
    }

    public boolean setToSpawner(CreatureSpawner spawner) {
        MobSpawnerData mobSpawnerData = new MobSpawnerData(this, maxDurability);
        return mobSpawnerData.setToSpawner(spawner);
    }

}
