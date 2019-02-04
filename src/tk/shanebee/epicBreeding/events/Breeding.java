package tk.shanebee.epicBreeding.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import tk.shanebee.epicBreeding.EpicBreeding;
import tk.shanebee.epicBreeding.config.Config;
import tk.shanebee.epicBreeding.util.Breedable;
import tk.shanebee.epicBreeding.util.Gender;

import java.util.HashMap;

public class Breeding implements Listener {

    private HashMap<Player, Gender> genderMAP = new HashMap<>();
    private HashMap<Player, Entity> entityMAP = new HashMap<>();
    private EpicBreeding main;

    public Breeding(EpicBreeding main) {
        this.main = main;
    }

    private Config config = new Config(this.main);

    @EventHandler
    public void onBreed(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        Player player = e.getPlayer();
        Gender gender = Breedable.getGender(entity);
        if (!(entity instanceof Ageable)) {
            return;
        }
        if (!((Ageable) entity).canBreed()) {
            return;
        }
        EquipmentSlot slot = e.getHand();
        if (!slot.equals(EquipmentSlot.HAND)) {
            return;
        }
        if (Breedable.isBreedingTool(player.getItemInHand().getType(), entity)) {
            e.setCancelled(true);
            if (Breedable.isPregnant(entity)) {
                player.sendMessage("She is already pregnant");
                return;
            }
            if (!entityMAP.containsKey(player)) {
                entityMAP.put(player, entity);
                genderMAP.put(player, Breedable.getGender(entity));

                player.sendMessage(ChatColor.GREEN + "You have started breeding a " + gender.getName() + " " + entity
                        .getType().toString().toLowerCase());
            } else if (entityMAP.get(player) != entity) {
                if ((entityMAP.get(player)).getType() == entity.getType()) {
                    if (genderMAP.get(player) != Breedable.getGender(entity)) {
                        player.sendMessage(ChatColor.GREEN + "You have successfully bred 2 " + entity
                                .getType().toString().toLowerCase() + "'s");
                        if (Breedable.getGender(entity) == Gender.FEMALE) {
                            entity.setCustomName(ChatColor.LIGHT_PURPLE + "♀+");
                            Location locF = entity.getLocation();
                            locF.setY(locF.getY() + 2.0D);
                            entity.getWorld().spawnParticle(Particle.HEART, locF, 1);
                            entity.getWorld().spawn(locF, ExperienceOrb.class).setExperience(3);
                            config.createMother(entity, main);
                            ((Ageable) entityMAP.get(player)).setBreed(false);
                        } else {
                            (entityMAP.get(player)).setCustomName(ChatColor.LIGHT_PURPLE + "♀+");
                            Location locM = (entityMAP.get(player)).getLocation();
                            locM.setY(locM.getY() + 2.0D);
                            (entityMAP.get(player)).getWorld().spawnParticle(Particle.HEART, locM, 1);
                            (entityMAP.get(player).getWorld().spawn(locM, ExperienceOrb.class)).setExperience(3);
                            config.createMother(entityMAP.get(player), main);
                            ((Ageable) entity).setBreed(false);
                        }
                        genderMAP.remove(player);
                        entityMAP.remove(player);
                    } else {
                        genderMAP.remove(player);
                        entityMAP.remove(player);

                        player.sendMessage(ChatColor.RED + "You can not breed 2 " + gender.getName() + "s");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "You can not breed a " + gender.getName() + " and a " + this.genderMAP.get(player));
                    genderMAP.remove(player);
                    entityMAP.remove(player);
                }
            } else {
                player.sendMessage(ChatColor.RED + "You can not breed this " +
                        entity.getType().toString().toLowerCase() + " with " + gender.getPronoun() + "self");
                genderMAP.remove(player);
                entityMAP.remove(player);
            }
        }
    }
}
