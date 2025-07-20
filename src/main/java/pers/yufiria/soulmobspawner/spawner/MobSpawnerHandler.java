package pers.yufiria.soulmobspawner.spawner;

import crypticlib.lifecycle.AutoTask;
import crypticlib.lifecycle.BukkitLifeCycleTask;
import crypticlib.lifecycle.LifeCycle;
import crypticlib.lifecycle.TaskRule;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Unmodifiable;
import pers.yufiria.soulmobspawner.config.Configs;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@AutoTask(
    rules = {
        @TaskRule(lifeCycle = LifeCycle.ENABLE),
        @TaskRule(lifeCycle = LifeCycle.RELOAD)
    }
)
public enum MobSpawnerHandler implements BukkitLifeCycleTask {

    INSTANCE;
    private final Map<EntityType, MobSpawnerSetting> mobSpawnerMap = new ConcurrentHashMap<>();

    public Optional<MobSpawnerSetting> getMobSpawner(EntityType entityType) {
        return Optional.ofNullable(mobSpawnerMap.get(entityType));
    }

    public @Unmodifiable Map<EntityType, MobSpawnerSetting> mobSpawnerMap() {
        return Collections.unmodifiableMap(mobSpawnerMap);
    }

    @Override
    public void lifecycle(Plugin plugin, LifeCycle lifeCycle) {
        mobSpawnerMap.clear();
        ConfigurationSection mobSpawnerSettings = Configs.MOB_SPAWNER_SETTINGS.value();
        for (String key : mobSpawnerSettings.getKeys(false)) {
            ConfigurationSection mobSpawnerSetting = mobSpawnerSettings.getConfigurationSection(key);
            try {
                MobSpawnerSetting mobSpawner = MobSpawnerSetting.fromConfig(key, mobSpawnerSetting);
                mobSpawnerMap.put(mobSpawner.mobType(), mobSpawner);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

}
