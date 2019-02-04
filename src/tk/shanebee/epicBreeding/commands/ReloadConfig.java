package tk.shanebee.epicBreeding.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import tk.shanebee.epicBreeding.EpicBreeding;

public class ReloadConfig implements CommandExecutor {

    private EpicBreeding instance;

    public ReloadConfig(EpicBreeding main) {
        this.instance = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                String prefix = instance.getConfig().getString("Messages.Prefix");
                String reloadMsg = instance.getConfig().getString("Messages.Reload Config");
                instance.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + " " + reloadMsg));
            }
        }
        return true;
    }
}
