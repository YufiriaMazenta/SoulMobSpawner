package pers.yufiria.soulmobspawner;

import com.github.yufiriamazenta.craftorithm.item.ItemManager;
import crypticlib.BukkitPlugin;
import org.bukkit.Bukkit;
import pers.yufiria.soulmobspawner.hook.CraftorithmItemProvider;

public class SoulMobSpawner extends BukkitPlugin {

    public static SoulMobSpawner INSTANCE;

    public SoulMobSpawner() {
        INSTANCE = this;
    }

    @Override
    public void enable() {
        if (Bukkit.getPluginManager().isPluginEnabled("Craftorithm")) {
            ItemManager.INSTANCE.regItemProvider(CraftorithmItemProvider.INSTANCE);
        }
    }
}