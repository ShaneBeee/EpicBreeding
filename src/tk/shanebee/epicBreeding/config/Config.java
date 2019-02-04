package tk.shanebee.epicBreeding.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import tk.shanebee.epicBreeding.EpicBreeding;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class Config {

    private EpicBreeding main;

    public Config(EpicBreeding main) {
        this.main = main;
    }

    public void loadConfig() {
        this.main.saveDefaultConfig();
    }

    public void createMother(Entity entity, EpicBreeding main) {
        int min = main.getConfig().getInt("Options.Breeding Days.Min");
        int max = main.getConfig().getInt("Options.Breeding Days.Max");

        long RANDOM_DAY = ThreadLocalRandom.current().nextInt(min, max) * 20 * 60000;

        Calendar date = Calendar.getInstance();
        long t = date.getTimeInMillis();
        Date newDate = new Date(t + RANDOM_DAY);

        File file = new File("plugins/EpicBreeding", "Mothers.yml");
        FileConfiguration motherConfig = YamlConfiguration.loadConfiguration(file);

        motherConfig.set("Mothers." + entity.getUniqueId().toString() + ".Due Date", newDate);
        try {
            motherConfig.save(file);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "ERROR SAVING MOTHER");
        }
    }

    public static FileConfiguration getConfig() {
        File file = new File("plugins/EpicBreeding", "Mothers.yml");
        return YamlConfiguration.loadConfiguration(file);
    }

    public void createMotherConfig() {
        File file = new File("plugins/EpicBreeding", "Mothers.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileConfiguration mothers = YamlConfiguration.loadConfiguration(file);
            mothers.options().header("This is the file file, no need to touch this, it is used internally");
            mothers.options().copyHeader(true);
            try {
                mothers.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
