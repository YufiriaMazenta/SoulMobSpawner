package pers.yufiria.soulmobspawner.recipe;

import crypticlib.lifecycle.AutoTask;
import crypticlib.lifecycle.BukkitLifeCycleTask;
import crypticlib.lifecycle.LifeCycle;
import crypticlib.lifecycle.TaskRule;
import crypticlib.util.IOHelper;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import pers.yufiria.soulmobspawner.SoulMobSpawner;
import pers.yufiria.soulmobspawner.fragment.MobSoulFragment;
import pers.yufiria.soulmobspawner.fragment.MobSoulFragmentsHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@AutoTask(rules = {
    @TaskRule(lifeCycle = LifeCycle.ENABLE, priority = 1),
    @TaskRule(lifeCycle = LifeCycle.RELOAD, priority = 1)
})
public enum RecipeRegister implements BukkitLifeCycleTask {

    INSTANCE;
    //配方key的前缀,例如僵尸的key就是'soulmobspawner:spawner_recipe_zombie'
    public static final String RECIPE_KEY_PREFIX = "spawner_recipe_";
    private final Set<NamespacedKey> registeredRecipes = new HashSet<>();

    @Override
    public void lifecycle(Plugin plugin, LifeCycle lifeCycle) {
        registeredRecipes.forEach(it -> Bukkit.removeRecipe(it, true));
        registeredRecipes.clear();

        Map<EntityType, MobSoulFragment> mobSoulFragments = MobSoulFragmentsHandler.INSTANCE.mobSoulFragments();
        mobSoulFragments.forEach((type, mobSoulFragment) -> {
            try {
                NamespacedKey recipeKey = new NamespacedKey(SoulMobSpawner.INSTANCE, RECIPE_KEY_PREFIX + type.name().toLowerCase());
                ShapelessRecipe shapelessRecipe = new ShapelessRecipe(Nam)
            } catch (Throwable throwable) {
                IOHelper.info("&cLoad recipe for " + type + " failed");
                throwable.printStackTrace();
            }
        });
    }
}
