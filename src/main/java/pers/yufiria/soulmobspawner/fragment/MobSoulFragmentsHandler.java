package pers.yufiria.soulmobspawner.fragment;

import crypticlib.config.node.impl.bukkit.ConfigSectionConfig;
import crypticlib.lifecycle.AutoTask;
import crypticlib.lifecycle.BukkitLifeCycleTask;
import crypticlib.lifecycle.LifeCycle;
import crypticlib.lifecycle.TaskRule;
import crypticlib.listener.EventListener;
import crypticlib.util.IOHelper;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;
import pers.yufiria.soulmobspawner.config.Configs;

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
    private final Map<NamespacedKey, MobSoulFragment> mobSoulFragmentsMap = new ConcurrentHashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }
        NamespacedKey mobKey = event.getEntity().getType().getKey();
        if (!mobSoulFragmentsMap.containsKey(mobKey)) {
            return;
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
                mobSoulFragmentsMap.put(fragment.mobId(), fragment);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
