package com.goyanov.rglib;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public static Player getRandomPlayer()
    {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (players.size() == 0) return null;
        return (Player) players.toArray()[(int)(Math.random()*players.size())];
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

    public static void moveEntityInDirectionOfEntity(Entity from, Entity to, double speed)// ДВИГАЕМ СУЩНОСТЬ В СТОРОНУ ДРУГОЙ СУЩНОСТИ С ЗАДАННОЙ СКОРОСТЬЮ
    {
        Location fromL = from.getLocation();
        Location toL = to.getLocation();

        Vector direction = new Vector(toL.getX() - fromL.getX(), toL.getY() - fromL.getY(), toL.getZ() - fromL.getZ());
        if (direction.length() != 0) direction = direction.normalize().multiply(speed);
        fromL.add(direction);
        from.teleport(fromL);
    }

    public static void forceEntityLookAtEntity(Entity who, Entity atWho)
    {
        Location from = who.getLocation();
        Location to = atWho.getLocation();

        Vector direction = new Vector(to.getX() - from.getX(), to.getY() - from.getY(), to.getZ() - from.getZ());
        from.setDirection(direction);

        who.teleport(from);
    }

    public static Location getLocationInFrontOfEntity(Entity ent, float distance)// ПОЛУЧАЕМ ЛОКАЦИЮ ПРЯМО ПЕРЕД (ЗА) СУЩНОСТЬЮ В НЕСКОЛЬКИХ БЛОКАХ
    {
        Location pLoc = ent.getLocation();
        pLoc.setPitch(0);
        Vector viewLoc = pLoc.getDirection().normalize().multiply(distance);
        pLoc = pLoc.add(viewLoc.getX(), 0, viewLoc.getZ());
        if (distance > 0) pLoc.setYaw(pLoc.getYaw()-180);
        return pLoc;
    }

    public static List<Block> getNearbyBlocks(Player p, int x, int y, int z)
    {
        List<Block> list = new ArrayList<>();
        Location loc = p.getLocation();
        World w = loc.getWorld();

        for (int i = -x; i <= x; i++)
            for (int j = -y; j <= y; j++)
                for (int k = -z; k <= z; k++)
                {
                    list.add(w.getBlockAt(loc.clone().add(i,j,k)));
                }

        return list;
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