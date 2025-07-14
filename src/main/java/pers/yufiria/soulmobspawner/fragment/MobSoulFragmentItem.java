package pers.yufiria.soulmobspawner.fragment;

import crypticlib.util.MaterialHelper;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public record MobSoulFragmentItem(
    @NotNull Material material,
    @Nullable String name,
    @NotNull List<String> lore,
    @Nullable Integer customModelData,
    @Nullable NamespacedKey itemModel
) {

    public ItemStack toItem() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setItemName(name);
        meta.setLore(lore);
        meta.setCustomModelData(customModelData);

        meta.setItemModel(itemModel);
        item.setItemMeta(meta);
        return item;
    }

    public static MobSoulFragmentItem fromConfig(ConfigurationSection config) throws NullPointerException {
        if (config == null)
            return null;
        Material material = MaterialHelper.matchMaterial(config.getString("material", "minecraft:stone"));
        String name = config.getString("name");
        List<String> lore = config.getStringList("lore");
        Integer customModelData = config.getInt("custom_model_data");
        NamespacedKey itemModel;
        if (config.contains("item_model")) {
            itemModel = NamespacedKey.fromString(Objects.requireNonNull(config.getString("item_model")));
        } else {
            itemModel = null;
        }
        return new MobSoulFragmentItem(Objects.requireNonNull(material), name, lore, customModelData, itemModel);
    }

}
