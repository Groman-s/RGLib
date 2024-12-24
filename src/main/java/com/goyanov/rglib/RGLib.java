package com.goyanov.rglib;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RGLib
{
    public static class Constants
    {
        public static final Vector STAND_VELOCITY = new Vector(0, -0.0784000015258789, 0);
    }

    public static double roundDoubleValue(double value, int signsAfterDots) // округление до signsAfterDots знаков после запятой (10^signsAfterDots)
    {
        long coef = 1;
        for (int i = 0; i < signsAfterDots; i++)
        {
            coef *= 10;
        }
        return Math.round(value * coef) / (double) coef;
    }

    public static boolean isNight()
    {
        long time = Bukkit.getWorlds().get(0).getTime();
        return time >= 13000 && time <= 23000;
    }

    public static Player getRandomPlayer()
    {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        if (players.isEmpty()) return null;
        return (Player) players.toArray()[(int)(Math.random()*players.size())];
    }

    public static String formatWithSimpleColors(String msg)
    {
        return msg.replace("&", "§");
    }

    public static String getColoredMessage(String message)
    {
        message = message.replace("&", "§");
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        for (Matcher matcher = pattern.matcher(message); matcher.find(); matcher = pattern.matcher(message))
        {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color) + "");
        }

        return message;
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

    public static void sendActionBarMessage(Player p, String msg) // ОТПРАВИТЬ ИГРОКУ СООБЩЕНИЕ В ACTIONBAR
    {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(msg).create());
    }

    public static boolean isPlayerLookingAtEntity(Player p, Entity ent, float delta) // ЕСЛИ ИГРОК СМОТРИТ НА СУЩНОСТЬ
    {
        Vector who = p.getLocation().getDirection().normalize();
        who.setY(0);
        Vector atWho = ent.getLocation().getDirection().normalize();
        atWho.setY(0);

        return who.add(atWho).length() < delta;
    }

    public static void moveEntityInDirectionOfEntity(Entity from, Entity to, double speed) // ДВИГАЕМ СУЩНОСТЬ В СТОРОНУ ДРУГОЙ СУЩНОСТИ С ЗАДАННОЙ СКОРОСТЬЮ
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

    public static Location getLocationInFrontOfEntity(Entity ent, float distance) // ПОЛУЧАЕМ ЛОКАЦИЮ ПРЯМО ПЕРЕД (ЗА) СУЩНОСТЬЮ В НЕСКОЛЬКИХ БЛОКАХ
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
        List<Block> list = new ArrayList<>(2*x + 2*y + 2*z);
        Location loc = p.getLocation();
        World w = loc.getWorld();

        for (int i = -x; i <= x; i++)
            for (int j = -y; j <= y; j++)
                for (int k = -z; k <= z; k++)
                    list.add(w.getBlockAt(loc.clone().add(i,j,k)));

        return list;
    }

    @SafeVarargs
    public static <T> T randomOf(T... objects)
    {
        if (objects.length == 0) return null;
        return objects[(int) (Math.random() * objects.length)];
    }

    @SafeVarargs
    public static <T> T randomOf(ProbableObject<T>... probableObjects)
    {
        double commonProb = Arrays.stream(probableObjects).mapToDouble(ProbableObject::getProbability).sum();
        double random = Math.random() * commonProb;

        double currentUp = probableObjects[0].getProbability();
        for (int i = 0; i < probableObjects.length; i++)
        {
            ProbableObject<T> objAndProb = probableObjects[i];
            if (random < currentUp) return objAndProb.getObject();
            else currentUp += probableObjects[i + 1].getProbability();
        }

        return null;
    }

    public static boolean locationIsSafe(Location location)
    {
        location = location.clone();
        return  (location.add(0,-1,0).getBlock().getType().isSolid() || location.getBlock().getType() == Material.WATER) &&
                !location.add(0,1,0).getBlock().getType().isSolid() && location.getBlock().getType() != Material.WATER &&
                !location.add(0,1,0).getBlock().getType().isSolid() && location.getBlock().getType() != Material.WATER ;
    }

    public static Location findNearestSafeLocation(Location location)
    {
        if (locationIsSafe(location)) return location;

        Location foundSafeLoc = location.clone();

        boolean searchUpper =
                foundSafeLoc.getBlock().getType().isSolid() ||
                foundSafeLoc.getBlock().getType() == Material.WATER ||
                foundSafeLoc.clone().add(0,1,0).getBlock().getType().isSolid() ||
                foundSafeLoc.clone().add(0,1,0).getBlock().getType() == Material.WATER;

        World world = location.getWorld();

        int yAdd = searchUpper ? 1 : -1;
        Predicate<Location> conditionToStop = (loc) -> searchUpper ? loc.getY() >= world.getMaxHeight() : loc.getY() <= world.getMinHeight();

        do
        {
            foundSafeLoc.add(0,yAdd,0);
            if (conditionToStop.test(foundSafeLoc)) return location;
        }
        while (!locationIsSafe(foundSafeLoc));

        foundSafeLoc.setY((int) foundSafeLoc.getY());

        return foundSafeLoc;
    }
}