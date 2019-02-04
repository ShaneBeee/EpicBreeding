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

        // If entity is not Ageable, ignore
        if (!(entity instanceof Ageable)) {
            return;
        }

        // If entity can not breed, ignore
        if (!((Ageable) entity).canBreed()) {
            return;
        }

        // TODO fix this up, temp holder (was firing event twice)
        EquipmentSlot slot = e.getHand();
        if (!slot.equals(EquipmentSlot.HAND)) {
            return;
        }

        // Check if player is holding a breeding tool
        if (Breedable.isBreedingTool(player.getItemInHand().getType(), entity)) {
            e.setCancelled(true);

            // If entity is already pregnant, return
            if (Breedable.isPregnant(entity)) {
                player.sendMessage("She is already pregnant");
                return;
            }

            // Initiate breeding sequence if not already started
            if (!entityMAP.containsKey(player)) {
                entityMAP.put(player, entity);
                genderMAP.put(player, Breedable.getGender(entity));

                player.sendMessage(ChatColor.GREEN + "You have started breeding a " + gender.getName() + " " + entity
                        .getType().toString().toLowerCase());

            // If breeding sequence has started, check for next entity and make sure its not the same entity
            } else if (entityMAP.get(player) != entity) {

                // Makes sure this entity is the same type as the first entity
                if ((entityMAP.get(player)).getType() == entity.getType()) {

                    // Makes sure this entity is not the same gender as the first
                    if (genderMAP.get(player) != Breedable.getGender(entity)) {
                        player.sendMessage(ChatColor.GREEN + "You have successfully bred 2 " + entity
                                .getType().toString().toLowerCase() + "'s");
                        if (Breedable.getGender(entity) == Gender.FEMALE) {
                            entity.setCustomName(ChatColor.LIGHT_PURPLE + "♀+");
                            Location locF = entity.getLocation();
                            locF.setY(locF.getY() + 1.5D);
                            entity.getWorld().spawnParticle(Particle.HEART, locF, 1);
                            entity.getWorld().spawn(locF, ExperienceOrb.class).setExperience(3);
                            config.createMother(entity, main);
                            ((Ageable) entityMAP.get(player)).setBreed(false);
                        } else {
                            entityMAP.get(player).setCustomName(ChatColor.LIGHT_PURPLE + "♀+");
                            Location locM = (entityMAP.get(player)).getLocation();
                            locM.setY(locM.getY() + 1.5D);
                            entityMAP.get(player).getWorld().spawnParticle(Particle.HEART, locM, 1);
                            entityMAP.get(player).getWorld().spawn(locM, ExperienceOrb.class).setExperience(3);
                            config.createMother(entityMAP.get(player), main);
                            ((Ageable) entity).setBreed(false);
                        }
                        genderMAP.remove(player);
                        entityMAP.remove(player);

                    // If the gender is the same, cancel breeding sequence
                    } else {
                        genderMAP.remove(player);
                        entityMAP.remove(player);

                        player.sendMessage(ChatColor.RED + "You can not breed 2 " + gender.getName() + "s");
                    }

                // If entity types are different, cancel breeding sequence
                } else {
                    player.sendMessage(ChatColor.RED + "You can not breed a " + gender.getName() + " and a " + this.genderMAP.get(player));
                    genderMAP.remove(player);
                    entityMAP.remove(player);
                }

            // If entity is clicked twice, cancel breeding sequence
            } else {
                player.sendMessage(ChatColor.RED + "You can not breed this " +
                        entity.getType().toString().toLowerCase() + " with " + gender.getPronoun() + "self");
                genderMAP.remove(player);
                entityMAP.remove(player);
            }
        }
    }
}
