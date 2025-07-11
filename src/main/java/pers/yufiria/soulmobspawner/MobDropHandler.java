package pers.yufiria.soulmobspawner;

import crypticlib.listener.EventListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

@EventListener
public enum MobDropHandler implements Listener {

    INSTANCE;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.isCancelled()) {
            return;
        }

    }


}
