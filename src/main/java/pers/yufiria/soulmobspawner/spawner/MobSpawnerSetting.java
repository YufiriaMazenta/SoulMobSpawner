package pers.yufiria.soulmobspawner.spawner;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import pers.yufiria.soulmobspawner.SoulMobSpawner;

public record MobSpawnerSetting(
    EntityType mobType,
    Integer maxDurability
) {

    public static final NamespacedKey MOB_SPAWNER_ID_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_id");

    public static MobSpawnerSetting fromConfig(String mobTypeStr, ConfigurationSection config) {
        EntityType mobType = EntityType.valueOf(mobTypeStr.toUpperCase());
        Integer maxDurability = config.getInt("max_durability");
        return new MobSpawnerSetting(mobType, maxDurability);
    }

    public ItemStack getSpawnerItem() {
        //todo
        return null;
    }

    public boolean placeSpawner(Location location) {

    }

}
