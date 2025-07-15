package pers.yufiria.soulmobspawner.fragment;

import crypticlib.lifecycle.AutoTask;
import crypticlib.lifecycle.BukkitLifeCycleTask;
import crypticlib.lifecycle.LifeCycle;
import crypticlib.lifecycle.TaskRule;
import crypticlib.listener.EventListener;
import io.lumine.mythic.bukkit.MythicBukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Unmodifiable;
import pers.yufiria.soulmobspawner.config.Configs;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@AutoTask(
    rules = {
        @TaskRule(lifeCycle = LifeCycle.ENABLE),
        @TaskRule(lifeCycle = LifeCycle.RELOAD)
    }
)
@EventListener
public enum MobSoulFragmentsHandler implements Listener, BukkitLifeCycleTask {

    INSTANCE;
    private final Map<EntityType, MobSoulFragment> mobSoulFragmentsMap = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        LivingEntity entity = event.getEntity();
        if (MythicBukkit.inst().getMobManager().isMythicMob(entity)) {
            //是mm怪物,不做处理,直接返回
            return;
        }
        EntityType mobType = entity.getType();
        if (!mobSoulFragmentsMap.containsKey(mobType)) {
            return;
        }
        MobSoulFragment mobSoulFragment = mobSoulFragmentsMap.get(mobType);
        double randomNum = Math.random();
        if (randomNum < mobSoulFragment.probability()) {
            //概率符合,掉落
            event.getDrops().add(mobSoulFragment.item().toItem());
        }
    }

    @Override
    public void lifecycle(Plugin plugin, LifeCycle lifeCycle) {
        mobSoulFragmentsMap.clear();
        ConfigurationSection soulFragmentConfigs = Configs.SOUL_FRAGMENT_SETTINGS.value();
        for (String key : soulFragmentConfigs.getKeys(false)) {
            ConfigurationSection soulFragmentConfig = soulFragmentConfigs.getConfigurationSection(key);
            try {
                MobSoulFragment fragment = MobSoulFragment.fromConfig(key, Objects.requireNonNull(soulFragmentConfig));
                mobSoulFragmentsMap.put(fragment.mobType(), fragment);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public @Unmodifiable Map<EntityType, MobSoulFragment> mobSoulFragments() {
        return Collections.unmodifiableMap(mobSoulFragmentsMap);
    }
}
