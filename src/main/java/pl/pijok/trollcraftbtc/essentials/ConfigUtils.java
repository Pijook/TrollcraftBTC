package pl.pijok.trollcraftbtc.essentials;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import pl.pijok.trollcraftbtc.Main;

import java.io.File;
import java.io.IOException;

public class ConfigUtils {

    /**
     * Loads specified config from resources or plugin folder
     * @param configName Name of a config
     * @param plugin Plugin that loads config
     * @return Returns loaded config file or error if it doesn't exist
     */
    public static YamlConfiguration load(String configName, Plugin plugin){
        YamlConfiguration config;
        File file = new File(plugin.getDataFolder() + File.separator + configName);
        if (!file.exists())
            plugin.saveResource(configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    /**
     * Saves given config file
     * @param c Yaml file
     * @param file File name
     */
    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(Main.getInstance().getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets location from config
     * @param configuration Configuration file
     * @param path Path to location
     * @return Returns ready location
     */
    public static Location getLocationFromConfig(YamlConfiguration configuration, String path){
        double locationX = configuration.getDouble(path + ".x");
        double locationY = configuration.getDouble(path + ".y");
        double locationZ = configuration.getDouble(path + ".z");
        String worldName = configuration.getString(path + ".world");

        Location location = new Location(Bukkit.getWorld(worldName), locationX, locationY, locationZ);

        return location;
    }

    /**
     * Saves location to config
     * @param configuration Configuration file
     * @param path Path to location where to save
     * @param location Location to save
     */
    public static void saveLocationToConfig(YamlConfiguration configuration, String path, Location location){
        configuration.set(path + ".x", location.getX());
        configuration.set(path + ".y", location.getY());
        configuration.set(path + ".z", location.getZ());
        configuration.set(path + ".world", location.getWorld().getName());
    }


}
