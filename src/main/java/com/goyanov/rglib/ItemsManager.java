package com.goyanov.rglib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemsManager
{
    public static ItemStack createCustomItem(Material material, int customModelData)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createCustomItem(Material material, String displayName, int customModelData)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setCustomModelData(customModelData);
        item.setItemMeta(meta);
        return item;
    }
}
