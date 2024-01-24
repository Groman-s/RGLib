package com.goyanov.rglib;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager
{
    public static void loadDefaultConfig(JavaPlugin plugin)
    {
        File config = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

    public static void addOptionIfNotExists(CustomConfig config, String key, Object value)
    {
        if (!config.getConfig().contains(key))
        {
            config.getConfig().set(key, value);
            config.save(true);
        }
    }

    public static void addOptionsIfNotExist(CustomConfig config, HashMap<String, Object> keysAndValues)
    {
        for (Map.Entry<String, Object> entry : keysAndValues.entrySet())
        {
            if (!config.getConfig().contains(entry.getKey()))
            {
                config.getConfig().set(entry.getKey(), entry.getValue());
                config.save(true);
            }
        }
        config.save(true);
    }
}
