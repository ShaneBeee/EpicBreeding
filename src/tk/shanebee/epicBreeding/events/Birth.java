package tk.shanebee.epicBreeding.events;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import tk.shanebee.epicBreeding.config.Config;

import java.util.Calendar;
import java.util.Date;

public class Birth {
    private FileConfiguration config = Config.getConfig();

    public void checkDueDates() {
        for (String uuid : config.getStringList("Mothers")) {
            for (World world : Bukkit.getWorlds()) {
                for (Chunk chunk : world.getLoadedChunks()) {
                    for (Entity entity : chunk.getEntities()) {
                        if (entity.getUniqueId().equals(uuid)) {
                            String dataS = config.getString("Mothers." + uuid + ".Due Date");
                            Date dueDate = new Date(dataS);
                            Calendar cal = Calendar.getInstance();
                            Date today = new Date(cal.getTimeInMillis());
                            if (dueDate.after(today)) {
                                EntityType type = entity.getType();
                                Location loc = entity.getLocation();
                                ((Ageable) entity.getWorld().spawnEntity(loc, type)).setBaby();
                                config.set("Mothers." + uuid, null);
                            }
                        }
                    }
                }
            }
        }
    }

}
