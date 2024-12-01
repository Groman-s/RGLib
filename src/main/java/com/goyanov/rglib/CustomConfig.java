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
    private final String configName;
    private final boolean fromJar;
    private final JavaPlugin plugin;

    private File file;
    private FileConfiguration config;

    public CustomConfig(String configName, boolean fromJar, JavaPlugin plugin)
    {
        this.configName = configName.endsWith(".yml") ? configName : configName + ".yml";

        this.fromJar = fromJar;
        this.plugin = plugin;

        reload();
    }

    private void createFileIfNotExists()
    {
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
    }

    public void reload()
    {
        createFileIfNotExists();
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save()
    {
        createFileIfNotExists();
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
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