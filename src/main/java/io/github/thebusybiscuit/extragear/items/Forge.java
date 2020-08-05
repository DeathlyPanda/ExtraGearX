package io.github.thebusybiscuit.extragear.items;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.mrCookieSlime.Slimefun.cscorelib2.inventory.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import io.github.thebusybiscuit.extragear.ExtraGear;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import me.mrCookieSlime.CSCoreLibPlugin.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Forge extends MultiBlockMachine {

    private ExtraGear plugin;

    public Forge(ExtraGear plugin, Category category) {
        super(category, new SlimefunItemStack("TOOL_FORGE", Material.ANVIL, "&eTool Forge", "", "&a&oYou can craft many new tools here!", "&a&oThe result goes in the Furnace output slot"), new ItemStack[] { new CustomItem(Material.STONE_BRICK_STAIRS), new CustomItem(Material.IRON_BLOCK), new ItemStack(Material.STONE_BRICK_STAIRS), new ItemStack(Material.IRON_BARS), new ItemStack(Material.IRON_TRAPDOOR), new ItemStack(Material.IRON_BARS), new ItemStack(Material.FURNACE), new ItemStack(Material.DISPENSER), new ItemStack(Material.ANVIL) }, new ItemStack[0], BlockFace.SELF);

        this.plugin = plugin;
    }

    @Override
    public void onInteract(Player p, Block b) {
        Block dispenser = b.getRelative(BlockFace.DOWN);

        Furnace furnace = locateFurnace(dispenser);
        FurnaceInventory furnaceInventory = furnace.getInventory();

        Inventory inv = ((Dispenser) dispenser.getState()).getInventory();
        List<ItemStack[]> inputs = RecipeType.getRecipeInputList(this);

        recipe:
        for (ItemStack[] input : inputs) {
            for (int i = 0; i < inv.getContents().length; i++) {
                if (!SlimefunUtils.isItemSimilar(inv.getContents()[i], input[i], true)) continue recipe;
            }

            ItemStack adding = RecipeType.getRecipeOutputList(this, input);

            if (Slimefun.hasUnlocked(p, adding, true)) {
                boolean canFit = furnaceInventory.getResult() == null || (furnaceInventory.getResult().getAmount() + adding.getAmount() <= 1 && SlimefunUtils.isItemSimilar(furnaceInventory.getResult(), adding, true));

                if (!canFit) {
                    SlimefunPlugin.getLocalization().sendMessage(p, "machines.full-inventory", true);
                    return;
                }

                for (int i = 0; i < inv.getContents().length; i++) {
                    ItemStack item = inv.getItem(i);

                    if (item != null) {
                        ItemUtils.consumeItem(item, item.getType() == Material.MILK_BUCKET);
                    }
                }

                Bukkit.getScheduler().runTaskLater(plugin, () -> p.getWorld().playSound(furnace.getLocation(), Sound.BLOCK_ANVIL_USE, 1F, 1F), 55L);

                for (int i = 1; i < 7; i++) {
                    Bukkit.getScheduler().runTaskLater(plugin, () -> p.getWorld().playSound(furnace.getLocation(), Sound.BLOCK_METAL_PLACE, 7F, 1F), i * 5L);
                }

                if (furnaceInventory.getResult() == null) {
                    furnaceInventory.setResult(adding);
                }
                else {
                    furnaceInventory.getResult().setAmount(furnaceInventory.getResult().getAmount() + adding.getAmount());
                }
            }

            return;
        }

        SlimefunPlugin.getLocalization().sendMessage(p, "machines.pattern-not-found", true);
    }

    private static Furnace locateFurnace(Block b) {
        if (b.getRelative(BlockFace.EAST).getType() == Material.FURNACE) {
            return (Furnace) b.getRelative(BlockFace.EAST).getState();
        }
        else if (b.getRelative(BlockFace.WEST).getType() == Material.FURNACE) {
            return (Furnace) b.getRelative(BlockFace.WEST).getState();
        }
        else if (b.getRelative(BlockFace.NORTH).getType() == Material.FURNACE) {
            return (Furnace) b.getRelative(BlockFace.NORTH).getState();
        }
        else {
            return (Furnace) b.getRelative(BlockFace.SOUTH).getState();
        }
    }
}
