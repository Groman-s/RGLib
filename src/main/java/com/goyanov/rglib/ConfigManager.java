package com.goyanov.rglib;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigManager
{
    public static void loadDefaultConfig(JavaPlugin plugin)
    {
        File config = new File(plugin.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }
}
