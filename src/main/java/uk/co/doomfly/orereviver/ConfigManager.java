package uk.co.doomfly.orereviver;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigManager {

    private static OreReviver plugin = OreReviver.getPlugin(OreReviver.class);

    public static HashMap<String, String> configMessages = new HashMap<>();
    public static File file;
    public static FileConfiguration filecfg;

    public static void Setup() throws IOException {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        plugin.saveResource("config.yml", false);

        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            plugin.saveResource("config.yml", true);
        }
        filecfg = YamlConfiguration.loadConfiguration(file);
        Load();
    }

    public static void Load() {
        configMessages.clear();
        filecfg = YamlConfiguration.loadConfiguration(file);
        for (String path : filecfg.getKeys(false)) {
            configMessages.put(path, filecfg.getString(path));
        }
    }

    public static Boolean saveConfig() {
        try {
            filecfg.save(file);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void deleteConfig() {
        file.delete();
    }

    public static void setStringConfig(String path, String value) {
        filecfg.set(path, value);
        saveConfig();
    }

    public static void setIntConfig(String path, int value) {
        filecfg.set(path, value);
        saveConfig();
    }

    public static void setBooleanConfig(String path, boolean value) {
        filecfg.set(path, value);
        saveConfig();
    }

    public static String getStringConfig(String path) {
        return filecfg.getString(path);
    }

    public static int getIntConfig(String path) {
        return filecfg.getInt(path);
    }

    public static Boolean getBooleanConfig(String path) {
        return filecfg.getBoolean(path);
    }
}
