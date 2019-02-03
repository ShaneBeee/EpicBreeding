package tk.shanebee.epicBreeding;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.shanebee.epicBreeding.commands.ReloadConfig;
import tk.shanebee.epicBreeding.config.Config;
import tk.shanebee.epicBreeding.events.Birth;
import tk.shanebee.epicBreeding.events.Breeding;
import tk.shanebee.epicBreeding.events.Spawning;

public class EpicBreeding extends JavaPlugin {

    @Override
    public void onEnable() {
        Config config = new Config(this);
        enableEvents();
        enableCommands();
        config.loadConfig();
        config.createMotherConfig();

        int delay = this.getConfig().getInt("Options.Birth Delay");
        runSchedule(delay);
    }

    @Override
    public void onDisable() {
    }

    private void enableEvents() {
        Bukkit.getPluginManager().registerEvents(new Spawning(this), this);
        Bukkit.getPluginManager().registerEvents(new Breeding(this), this);
    }

    private void enableCommands() {
        Bukkit.getPluginCommand("epicbreeding").setExecutor(new ReloadConfig(this));
    }

    private void runSchedule(int min) {
        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Birth birth = new Birth();
            birth.checkDueDates();
        }, 20 * 60 * 5, 20 * 60 * min);
    }
}
