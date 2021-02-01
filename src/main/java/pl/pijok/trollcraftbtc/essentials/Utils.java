package pl.pijok.trollcraftbtc.essentials;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Random;

public class Utils {

    /**
     * Checks if given arg is Integer
     * @param a String to check
     * @return Returns false if arg is integer
     */
    public static boolean isInteger(final String a) {
        try {
            Integer.parseInt(a);
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Checks if given arg is Material
     * @param a String to check
     * @return Returns true if arg is material
     */
    public static boolean isMaterial(final String a){
        try {
            Material.valueOf(a);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Checks if given arg is Double
     * @param a String to check
     * @return Returns false if arg is double
     */
    public static boolean isDouble(final String a) {
        try {
            Double.parseDouble(a);
            return false;
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * Checks if given arg is Mob
     * @param mob_name String to check
     * @return Returns false if arg is Mob
     */
    public static boolean isMob(String mob_name){
        try{
            EntityType.valueOf(mob_name);
            return false;
        }
        catch (Exception e){
            return true;
        }
    }

    /**
     * Checks if given arg is Enchantment Name
     * @param a String to check
     * @return Returns true if arg is Enchantment
     */
    public static boolean isEnchantment(String a){
        try{
            Enchantment.getByName(a);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Changes miliseconds to hours
     * @param milis Miliseconds
     * @return Returns hours
     */
    public static int milisToHours(long milis){
        int time = (int) (((milis/1000)/60)/60);
        return time;
    }

    /**
     * Counts entities in specified chunk
     * @param entityType Types of entity to count
     * @param chunk Chunk where function will count entity
     * @return Return amount of entities
     */
    public static int getEntitiesInChunk(EntityType entityType, Chunk chunk){
        int sum = 0;
        for(Entity entity : chunk.getEntities()){
            if(entity.getType().equals(entityType)){
                sum++;
            }
        }
        return sum;
    }

    /**
     * Returns random number in range
     * @param min Lowest possible number
     * @param max Highest possible number
     * @return Returns random number
     */
    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
