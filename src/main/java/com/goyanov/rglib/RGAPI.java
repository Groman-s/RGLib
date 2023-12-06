package com.goyanov.rglib;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RGAPI
{
    public static class Constants
    {
        public static final Vector STAND_VELOCITY = new Vector(0, -0.0784000015258789, 0);
    }

    public static boolean isNight()
    {
        long time = Bukkit.getWorlds().get(0).getTime();
        return time >= 13000 && time <= 23000;
    }

    public static String formatWithColors(String msg)
    {
        return msg.replace("&", "§");
    }

    public static void playLoudSound(Player p, Sound sound, int volumeMultiply, float pitch)
    {
        Location loc = p.getLocation();
        for (int i = 0; i < volumeMultiply; i++)
        {
            p.playSound(loc, sound, 1, pitch);
        }
    }

    public static void playLoudSound(Player p, String soundName, int multiply, float pitch)
    {
        Location loc = p.getLocation();
        for (int i = 0; i < multiply; i++)
        {
            p.playSound(loc, soundName, 10, pitch);
        }
    }

    public static void playLoudSound(Location loc, Sound sound, int volumeMultiply, float pitch)
    {
        for (int i = 0; i < volumeMultiply; i++)
        {
            loc.getWorld().playSound(loc, sound, 1, pitch);
        }
    }

    public static void playLoudSound(Location loc, String soundName, int volumeMultiply, float pitch)
    {
        for (int i = 0; i < volumeMultiply; i++)
        {
            loc.getWorld().playSound(loc, soundName, 1, pitch);
        }
    }

    public static void sendActionBarMessage(Player p, String msg)// ОТПРАВИТЬ ИГРОКУ СООБЩЕНИЕ В ACTIONBAR
    {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(msg).create());
    }

    public static boolean isPlayerLookingAtEntity(Player p, Entity ent, float delta)// ЕСЛИ ИГРОК СМОТРИТ НА СУЩНОСТЬ
    {
        Vector who = p.getLocation().getDirection().normalize();
        who.setY(0);
        Vector atWho = ent.getLocation().getDirection().normalize();
        atWho.setY(0);

        if (who.add(atWho).length() < delta) return true;

        return false;
    }

    public static Location getTheNearestEmptyLocation(Location loc)
    {
        boolean findLower = false;
        Location lon = loc.clone().add(0, -1, 0);
        lon.setY((int)lon.getY());

        Block m1,m2;

        if(!lon.getBlock().getType().isSolid() && !lon.getBlock().getType().toString().contains("WATER"))
        {
            findLower = true;
        }

        if (findLower)
        {
            do
            {
                m1 = lon.add(0, -1, 0).getBlock();
            }
            while (!m1.getType().isSolid() && lon.getY() > 0);
            lon.add(0, 1, 0);
        }
        else
        {
            do
            {
                m1 = lon.getBlock();
                m2 = lon.add(0, 1, 0).getBlock();
            }
            while ((m1.getType().isSolid() || m1.getType().toString().contains("WATER")) && (m2.getType().isSolid() || m2.getType().toString().contains("WATER")));
        }

        return lon;
    }
}