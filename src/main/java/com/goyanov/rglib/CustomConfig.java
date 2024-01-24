package com.goyanov.rglib;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig
{
    private JavaPlugin plugin;
    private File file;
    private FileConfiguration config;

    public CustomConfig(String configName, boolean fromJar, JavaPlugin plugin)
    {
        if (!configName.endsWith(".yml")) configName += ".yml";

        this.plugin = plugin;
        this.plugin.getDataFolder().mkdir();
        this.file = new File(this.plugin.getDataFolder() + File.separator + configName);
        if (!this.file.exists())
        {
            if (fromJar)
            {
                this.plugin.saveResource(configName, false);
            }
            else
            {
                try {
                    this.file.createNewFile();
                } catch (IOException e) { e.printStackTrace(); }
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void save()
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

    public void save(boolean onlyIfExists)
    {
        if (onlyIfExists && !file.exists()) return;
        save();
    }

    public void saveAsync(boolean onlyIfExists)
    {
        if (onlyIfExists && !file.exists()) return;
        new Thread(this::save).start();
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