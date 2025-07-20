package pers.yufiria.soulmobspawner.hook;

import com.github.yufiriamazenta.craftorithm.item.ItemProvider;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.soulmobspawner.fragment.MobSoulFragmentSetting;
import pers.yufiria.soulmobspawner.fragment.MobSoulFragmentItem;
import pers.yufiria.soulmobspawner.fragment.MobSoulFragmentsHandler;

import java.util.Optional;

public enum CraftorithmItemProvider implements ItemProvider {

    INSTANCE;

    @Override
    public @NotNull String namespace() {
        return "soul_mob_spawner";
    }

    @Override
    public @Nullable String getItemName(ItemStack itemStack, boolean ignoreAmount) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        String itemId = null;
        if (persistentDataContainer.has(MobSoulFragmentItem.MOB_SOUL_FRAGMENT_ITEM_ID_KEY)) {
            itemId = "soul_fragment:" + persistentDataContainer.get(MobSoulFragmentItem.MOB_SOUL_FRAGMENT_ITEM_ID_KEY, PersistentDataType.STRING);
        }

        return ignoreAmount || itemStack.getAmount() <= 1 ? itemId : itemId + " " + itemStack.getAmount();
    }

    @Override
    public @Nullable ItemStack getItem(String itemName) {
        String[] split = itemName.split(":");
        if (split.length != 2) {
            return null;
        }
        switch (split[0]) {
            case "soul_fragment" -> {
                EntityType entityType = EntityType.valueOf(split[1].toUpperCase());
                Optional<MobSoulFragmentSetting> mobFragmentOpt = MobSoulFragmentsHandler.INSTANCE.getMobFragment(entityType);
                if (mobFragmentOpt.isEmpty()) {
                    return null;
                }
                MobSoulFragmentSetting mobSoulFragmentSetting = mobFragmentOpt.get();
                return mobSoulFragmentSetting.item().toItem();
            }
        }
        return null;
    }

    @Override
    public @Nullable ItemStack getItem(String itemName, @Nullable OfflinePlayer offlinePlayer) {
        return getItem(itemName);
    }
}
