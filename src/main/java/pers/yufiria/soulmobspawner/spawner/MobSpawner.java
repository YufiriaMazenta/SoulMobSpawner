package pers.yufiria.soulmobspawner.spawner;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public record MobSpawner(
    EntityType mobType,
    Integer maxDurability
) {

    public static MobSpawner fromConfig(String mobTypeStr, ConfigurationSection config) {
        EntityType mobType = EntityType.valueOf(mobTypeStr.toUpperCase());
        Integer maxDurability = config.getInt("max_durability");
        return new MobSpawner(mobType, maxDurability);
    }

    public ItemStack getSpawnerItem() {
        //todo
        return null;
    }

    public void setSpawnerData(CreatureSpawner spawner) {
        //TODO
    }

}
