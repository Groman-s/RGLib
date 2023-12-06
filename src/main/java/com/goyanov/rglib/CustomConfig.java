package com.goyanov.rglib;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig
{
    private File file;
    private FileConfiguration config;

    public CustomConfig(String configName, boolean fromJar, JavaPlugin plugin)
    {
        if (!configName.endsWith(".yml")) configName += ".yml";

        plugin.getDataFolder().mkdir();
        file = new File(plugin.getDataFolder() + File.separator + configName);
        if (!file.exists())
        {
            if (fromJar)
            {
                plugin.saveResource(configName, false);
            }
            else
            {
                try {
                    file.createNewFile();
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void save(JavaPlugin plugin)
    {
        plugin.getDataFolder().mkdir();
        if (!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) { e.printStackTrace(); }
        }
        try {
            config.save(file);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void save(JavaPlugin plugin, boolean onlyIfExists)
    {
        if (onlyIfExists && !file.exists()) return;

        save(plugin);
    }

    public void saveAsync(JavaPlugin plugin, boolean onlyIfExists)
    {
        if (onlyIfExists && !file.exists()) return;

        new Thread(() -> save(plugin)).start();
    }

    public File getFile()
    {
        return file;
    }

    public FileConfiguration getConfig()
    {
        return config;
    }
}