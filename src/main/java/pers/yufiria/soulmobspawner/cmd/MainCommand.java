package pers.yufiria.soulmobspawner.cmd;

import crypticlib.command.*;
import crypticlib.command.annotation.Command;
import crypticlib.command.annotation.Subcommand;
import crypticlib.perm.PermInfo;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.yufiria.soulmobspawner.SoulMobSpawner;
import pers.yufiria.soulmobspawner.spawner.MobSpawnerHandler;
import pers.yufiria.soulmobspawner.spawner.MobSpawnerSetting;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Command
public class MainCommand extends CommandTree {

    public static final MainCommand INSTANCE = new MainCommand();

    private MainCommand() {
        super(CommandInfo
            .builder("soulmobspawner")
            .permission(new PermInfo("soulmobspawner.command"))
            .aliases(Arrays.asList("sms"))
            .build()
        );
    }

    @Subcommand
    CommandNode reload = new CommandNode("reload") {
        @Override
        public void execute(@NotNull CommandInvoker invoker, @NotNull List<String> args) {
            SoulMobSpawner.INSTANCE.reloadPlugin();
            invoker.sendMsg("Plugin reload.");
        }
    };

    @Subcommand
    CommandNode mobSpawner = new CommandNode("mobspawner") {
        @Subcommand
        CommandNode get = new CommandNode("get") {
            @Override
            public void execute(@NotNull CommandInvoker invoker, @NotNull List<String> args) {
                if (args.isEmpty()) {
                    sendDescriptions(invoker);
                    return;
                }
                if (!invoker.isPlayer()) {
                    invoker.sendMsg("&cOnly player can use this command.");
                    return;
                }
                PlayerCommandInvoker player = invoker.asPlayer();
                Optional<MobSpawnerSetting> mobSpawnerOpt = MobSpawnerHandler.INSTANCE.getMobSpawner(EntityType.valueOf(args.get(0).toUpperCase()));
                mobSpawnerOpt.ifPresentOrElse(
                    spawnerSetting -> {
                        ItemStack spawnerItem = spawnerSetting.spawnerItem().toItem();
                        ((Player) player.getPlatformPlayer()).getInventory().addItem(spawnerItem);
                    },
                    () -> {
                        invoker.sendMsg("&cUnknown entity type: " + args.get(0));
                    }
                );
            }

            @Override
            public @Nullable List<String> tab(@NotNull CommandInvoker invoker, @NotNull List<String> args) {
                return MobSpawnerHandler.INSTANCE.mobSpawnerMap().keySet().stream().map(Enum::name).toList();
            }
        };
    };

}
