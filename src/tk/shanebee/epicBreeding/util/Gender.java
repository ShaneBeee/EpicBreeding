package tk.shanebee.epicBreeding.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public enum Gender {

    MALE("male", "him"),
    FEMALE("female", "her"),
    ;

    private String name;
    private String pronoun;

    Gender(String name, String pronoun) {
        this.name = name;
        this.pronoun = pronoun;
    }

    public String getName() {
        return name;
    }

    public String getPronoun() {
        return pronoun;
    }

    public static void setGender(Entity entity, Gender gender) {
        switch (gender) {
            case FEMALE:
                entity.setCustomName(ChatColor.LIGHT_PURPLE + "♀");
                break;
            case MALE:
                entity.setCustomName(ChatColor.AQUA + "♂");
        }
    }

}
