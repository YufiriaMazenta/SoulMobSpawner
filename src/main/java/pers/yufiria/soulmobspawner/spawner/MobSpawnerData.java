package pers.yufiria.soulmobspawner.spawner;

import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pers.yufiria.soulmobspawner.SoulMobSpawner;

public class MobSpawnerData {

    public static final NamespacedKey MOB_SPAWNER_DATA_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_data");
    public static final NamespacedKey MOB_SPAWNER_DURABILITY_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "durability");
    public static final NamespacedKey MOB_SPAWNER_EXTRAS_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_extras");

    private MobSpawnerSetting spawnerSetting;
    private Integer durability;

    public void spawnMob() {
        //TODO
    }

    public boolean setToSpawner(CreatureSpawner spawner) {
        if (spawner == null) {
            return false;
        }
        PersistentDataContainer rootData = spawner.getPersistentDataContainer();
        if (!rootData.has(MOB_SPAWNER_DATA_KEY)) {
            return false;
        }
        PersistentDataContainer spawnerData = rootData.getOrDefault(
            MOB_SPAWNER_DATA_KEY,
            PersistentDataType.TAG_CONTAINER,
            rootData.getAdapterContext().newPersistentDataContainer()
        );
        spawnerData.set(MOB_SPAWNER_DURABILITY_KEY, PersistentDataType.INTEGER, durability);
        return true;
    }

    public static MobSpawnerData fromItem(ItemStack item) {

    }

    public static MobSpawnerData fromMobSpawner(CreatureSpawner spawner) {

    }

}
