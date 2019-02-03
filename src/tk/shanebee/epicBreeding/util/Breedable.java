package tk.shanebee.epicBreeding.util;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Breedable {

    public static boolean isBreedable(Entity e) {
        EntityType type = e.getType();
        return (type == EntityType.SHEEP || type == EntityType.COW || type == EntityType.PIG || type == EntityType.CHICKEN ||
                type == EntityType.LLAMA || type == EntityType.RABBIT || type == EntityType.HORSE || type == EntityType.MUSHROOM_COW ||
                type == EntityType.WOLF || type == EntityType.OCELOT || type == EntityType.TURTLE);
    }

    public static Gender getGender(Entity e) {
        if (e.getName().contains("♂")) return Gender.MALE;
        return Gender.FEMALE;
    }

    public static boolean isPregnant(Entity e) {
        if (getGender(e) == Gender.FEMALE) {
            return (e.getName().contains("♀+"));
        } return false;
    }

    public static boolean isBreedingTool(Material mat, Entity entity) {
        EntityType type = entity.getType();
        switch (type) {
            case HORSE:
                if (mat == Material.GOLDEN_APPLE || mat == Material.GOLDEN_CARROT) return true;
                break;
            case SHEEP:
            case COW:
            case MUSHROOM_COW:
                if (mat == Material.WHEAT) return true;
                break;
            case PIG:
                switch (mat) {
                    case NETHER_WART:
                    case CARROT:
                    case POTATO:
                    case BEETROOT:
                        return true;
                }
                break;
            case CHICKEN:
                switch (mat) {
                    case WHEAT_SEEDS:
                    case PUMPKIN_SEEDS:
                    case BEETROOT_SEEDS:
                    case MELON_SEEDS:
                        return true;
                }
                break;
            case WOLF:
                switch (mat) {
                    case CHICKEN:
                    case PORKCHOP:
                    case BEEF:
                    case RABBIT:
                    case MUTTON:
                    case ROTTEN_FLESH:
                    case COOKED_PORKCHOP:
                    case COOKED_BEEF:
                    case COOKED_CHICKEN:
                    case COOKED_RABBIT:
                    case COOKED_MUTTON:
                        return true;
                }
                break;
            case OCELOT:
                switch (mat) {
                    case SALMON:
                    case COD:
                    case PUFFERFISH:
                    case TROPICAL_FISH:
                        return true;
                }
                break;
            case RABBIT:
                switch (mat) {
                    case DANDELION:
                    case CARROT:
                    case GOLDEN_CARROT:
                        return true;
                }
                break;
            case LLAMA:
                if (mat == Material.HAY_BLOCK) return true;
                break;
            case TURTLE:
                if (mat == Material.SEAGRASS) return true;
                break;
        }
        return false;
    }

}
