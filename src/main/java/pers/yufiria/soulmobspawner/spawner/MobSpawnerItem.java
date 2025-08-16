package pers.yufiria.soulmobspawner.spawner;

import crypticlib.util.MaterialHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.soulmobspawner.SoulMobSpawner;

import java.util.List;
import java.util.Objects;

public record MobSpawnerItem(
    @NotNull EntityType entityType,
    @Nullable String name,
    @NotNull List<String> lore,
    @Nullable Integer customModelData,
    @Nullable NamespacedKey itemModel
) {

    public ItemStack toItem() {
        ItemStack item = new ItemStack(Material.SPAWNER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setItemName(name);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);

        meta.setItemModel(itemModel);
        BlockStateMeta blockStateMeta = (BlockStateMeta) meta;
        BlockState blockState = blockStateMeta.getBlockState();
        CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;
        PersistentDataContainer dataContainer = creatureSpawner.getPersistentDataContainer();
        dataContainer.set(MobSpawnerSetting.MOB_SPAWNER_ID_KEY, PersistentDataType.STRING, entityType.getKey().toString());
        creatureSpawner.setSpawnedType(entityType);
        blockStateMeta.setBlockState(creatureSpawner);
        item.setItemMeta(meta);
        return item;
    }

    public static MobSpawnerItem fromConfig(EntityType entityType, ConfigurationSection config) throws NullPointerException {
        if (config == null)
            return null;
        String name = config.getString("name");
        List<String> lore = config.getStringList("lore");
        Integer customModelData = config.getInt("custom_model_data");
        NamespacedKey itemModel;
        if (config.contains("item_model")) {
            itemModel = NamespacedKey.fromString(Objects.requireNonNull(config.getString("item_model")));
        } else {
            itemModel = null;
        }
        return new MobSpawnerItem(entityType, name, lore, customModelData, itemModel);
    }

}
