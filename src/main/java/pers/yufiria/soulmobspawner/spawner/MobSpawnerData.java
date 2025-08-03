package pers.yufiria.soulmobspawner.spawner;

import crypticlib.util.ItemHelper;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pers.yufiria.soulmobspawner.SoulMobSpawner;

import java.util.Optional;

public class MobSpawnerData {

    public static final NamespacedKey MOB_SPAWNER_DATA_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_data");
    public static final NamespacedKey MOB_SPAWNER_DURABILITY_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "durability");
    public static final NamespacedKey MOB_SPAWNER_EXTRAS_KEY = new NamespacedKey(SoulMobSpawner.INSTANCE, "mob_spawner_extras");

    private final MobSpawnerSetting spawnerSetting;
    private Integer durability;

    public MobSpawnerData(MobSpawnerSetting spawnerSetting, Integer durability) {
        this.spawnerSetting = spawnerSetting;
        this.durability = durability;
    }

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
        spawnerData.set(MobSpawnerSetting.MOB_SPAWNER_ID_KEY, PersistentDataType.STRING, spawnerSetting.mobType().getKey().toString());
        spawnerData.set(MOB_SPAWNER_DURABILITY_KEY, PersistentDataType.INTEGER, durability);
        return true;
    }

    public MobSpawnerSetting spawnerSetting() {
        return spawnerSetting;
    }

    public Integer durability() {
        return durability;
    }

    public void setDurability(Integer durability) {
        this.durability = durability;
    }

    public static MobSpawnerData fromMobSpawner(CreatureSpawner spawner) {
        if (spawner == null) {
            return null;
        }
        PersistentDataContainer rootData = spawner.getPersistentDataContainer();
        if (!rootData.has(MobSpawnerSetting.MOB_SPAWNER_ID_KEY)) {
            return null;
        }

        NamespacedKey spawnerId = NamespacedKey.fromString(rootData.get(MobSpawnerSetting.MOB_SPAWNER_ID_KEY, PersistentDataType.STRING));
        Optional<MobSpawnerSetting> mobSpawnerSettingOpt = MobSpawnerHandler.INSTANCE.getMobSpawner(Registry.ENTITY_TYPE.get(spawnerId));
        if (mobSpawnerSettingOpt.isEmpty()) {
            return null;
        }
        MobSpawnerSetting spawnerSetting = mobSpawnerSettingOpt.get();
        PersistentDataContainer spawnerData = rootData.get(
            MOB_SPAWNER_DATA_KEY,
            PersistentDataType.TAG_CONTAINER
        );
        int durability = spawnerData.getOrDefault(MOB_SPAWNER_DURABILITY_KEY, PersistentDataType.INTEGER, spawnerSetting.maxDurability());
        return new MobSpawnerData(spawnerSetting, durability);
    }

}
