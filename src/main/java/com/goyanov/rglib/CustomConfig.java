package com.goyanov.rglib;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CustomConfig
{
    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration config;

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
                try { this.file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    private void save()
    {
        plugin.getDataFolder().mkdir();
        if (!file.exists())
        {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }

    public void save(boolean onlyIfExists)
    {
        if (onlyIfExists && !file.exists()) return;
        save();
    }

    public File getFile()
    {
        return file;
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public String getColoredConfigString(String configKey)
    {
        String message = getConfig().getString(configKey);
        if (message != null) message = RGLib.getColoredMessage(message);
        return message;
    }

    public List<String> getColoredConfigStringList(String configKey)
    {
        List<String> messages = getConfig().getStringList(configKey);
        messages.replaceAll(RGLib::getColoredMessage);
        return messages;
    }
}