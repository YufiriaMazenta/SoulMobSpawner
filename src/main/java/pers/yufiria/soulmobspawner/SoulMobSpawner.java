package pers.yufiria.soulmobspawner;

import crypticlib.BukkitPlugin;
import crypticlib.listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SoulMobSpawner extends BukkitPlugin {

    public static SoulMobSpawner INSTANCE;

    public SoulMobSpawner() {
        INSTANCE = this;
    }

}