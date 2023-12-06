package com.goyanov.rglib;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class MobsManager
{
    public static LivingEntity createCustomMob(EntityType type, Location loc, Material headMaterial, int customModelData)
    {
        ItemStack head = ItemsManager.createCustomItem(headMaterial, customModelData);
        Entity ent = loc.getWorld().spawnEntity(loc, type);

        if (!(ent instanceof LivingEntity)) return null;

        LivingEntity entity = (LivingEntity) ent;
        entity.setInvisible(true);
        entity.setSilent(true);

        EntityEquipment inv = entity.getEquipment();
        inv.setHelmet(head);
        inv.setChestplate(new ItemStack(Material.AIR));
        inv.setLeggings(new ItemStack(Material.AIR));
        inv.setBoots(new ItemStack(Material.AIR));
        inv.setItemInMainHand(new ItemStack(Material.AIR));
        inv.setItemInOffHand(new ItemStack(Material.AIR));

        return entity;
    }
}
