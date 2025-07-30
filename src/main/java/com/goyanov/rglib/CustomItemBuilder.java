package com.goyanov.rglib;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CustomItemBuilder
{
    private final ItemStack item;
    private final ItemMeta meta;

    public CustomItemBuilder(Material type)
    {
        item = new ItemStack(type);
        meta = item.getItemMeta();
    }

    public CustomItemBuilder withCustomModelData(int modelData)
    {
        meta.setCustomModelData(modelData);
        return this;
    }

    public CustomItemBuilder withCustomModelData(String modelData)
    {
        CustomModelDataComponent component = meta.getCustomModelDataComponent();
        component.setStrings(List.of(modelData));
        meta.setCustomModelDataComponent(component);
        return this;
    }

    public CustomItemBuilder withDisplayName(String displayName)
    {
        meta.setDisplayName(displayName);
        return this;
    }

    public CustomItemBuilder withLore(List<String> lore)
    {
        meta.setLore(lore);
        return this;
    }

    public CustomItemBuilder withAmount(int amount)
    {
        item.setAmount(amount);
        return this;
    }

    public ItemStack build()
    {
        item.setItemMeta(meta);
        return item;
    }

    public CustomItemBuilder addLoreLine(String line)
    {
        if (meta.getLore() == null) meta.setLore(new ArrayList<>());
        meta.getLore().add(line);
        return this;
    }

    public CustomItemBuilder editMeta(Consumer<ItemMeta> metaConsumer)
    {
        if (metaConsumer == null) return this;
        metaConsumer.accept(meta);
        return this;
    }
}