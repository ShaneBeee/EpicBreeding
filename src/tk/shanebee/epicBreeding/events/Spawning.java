package tk.shanebee.epicBreeding.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import tk.shanebee.epicBreeding.EpicBreeding;
import tk.shanebee.epicBreeding.util.Breedable;
import tk.shanebee.epicBreeding.util.Gender;

import java.util.concurrent.ThreadLocalRandom;

public class Spawning implements Listener {

    private EpicBreeding main;

    public Spawning(EpicBreeding main) {
        this.main = main;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        int percentFemale = main.getConfig().getInt("Options.Female Chance");
        Entity entity = e.getEntity();
        if (!Breedable.isBreedable(entity)) return;
        int chance = ThreadLocalRandom.current().nextInt(1, 101);
        if (chance <= percentFemale) {
            Gender.setGender(entity, Gender.FEMALE);
        } else {
            Gender.setGender(entity, Gender.MALE);
        }
    }

}
