package io.github.thebusybiscuit.extragear;

import java.util.Arrays;
import java.util.List;

import io.github.thebusybiscuit.extragear.items.Axes;
import io.github.thebusybiscuit.extragear.items.ExtraTool;
import io.github.thebusybiscuit.extragear.items.ExtraWeapon;
import io.github.thebusybiscuit.extragear.items.Forge;
import io.github.thebusybiscuit.slimefun4.core.researching.Research;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.bstats.bukkit.Metrics;
import me.mrCookieSlime.Slimefun.cscorelib2.collections.Pair;
import me.mrCookieSlime.Slimefun.cscorelib2.config.Config;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.updater.GitHubBuildsUpdater;

public class ExtraGear extends JavaPlugin implements SlimefunAddon {

    public static ExtraGear instance;

    private int researchId = 3300;
    private int delId = 5002500;
    private Category category;
    private Category extratools;
    private Forge forge;

    @Override
    public void onEnable() {
        instance = this;
        Config cfg = new Config(this);

        if (cfg.getBoolean("options.auto-update")) {
            new GitHubBuildsUpdater(this, getFile(), "DeathlyPanda/ExtraGearX/master").start();
        }

        new Metrics(this, 6469);

        category = new Category(new NamespacedKey(this, "items"), new CustomItem(Material.DIAMOND_SWORD, "&6ExtraGear"), 1);
        extratools = new Category(new NamespacedKey(this, "extra_tools"), new CustomItem(Material.DIAMOND_PICKAXE, "&6ExtraTools"), 1);

        forge = new Forge(this, extratools);
        forge.register(this);

        Research forgeResearch = new Research(new NamespacedKey(this, "tool_forge"), 600, "Tool Forge", 30);
        forgeResearch.addItems(forge);
        forgeResearch.register();

        SlimefunItemStack itemStack = new SlimefunItemStack("REINFORCED_DIAMOND", Material.DIAMOND, "&aReinforced Diamond", "", "&7The One to Rule them All");

        // A 3x3 shape representing our recipe
        ItemStack[] recipe = {
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.REINFORCED_ALLOY_INGOT,
                SlimefunItems.SYNTHETIC_DIAMOND, SlimefunItems.CARBONADO, SlimefunItems.SYNTHETIC_DIAMOND,
                SlimefunItems.REINFORCED_ALLOY_INGOT, SlimefunItems.SYNTHETIC_DIAMOND,SlimefunItems.REINFORCED_ALLOY_INGOT
        };

        SlimefunItem sfItem = new SlimefunItem(category, itemStack, RecipeType.SMELTERY, recipe);
        sfItem.register(this);

        NamespacedKey researchKey = new NamespacedKey(this,"reinforced_diamond");
        Research research = new Research(researchKey, 8467483, "What a beautiful gem!", 50);
        research.addItems(sfItem);
        research.register();

        registerWeapon(ExtraWeapon.IRON, "COPPER", SlimefunItems.COPPER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 2)), 5);
        registerArmor(ArmorSet.LEATHER, "COPPER", SlimefunItems.COPPER_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "TIN", SlimefunItems.TIN_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 1)), 5);
        registerArmor(ArmorSet.IRON, "TIN", SlimefunItems.TIN_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 3)), 5);

        registerWeapon(ExtraWeapon.IRON, "SILVER", SlimefunItems.SILVER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2)), 5);
        registerArmor(ArmorSet.IRON, "SILVER", SlimefunItems.SILVER_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "ALUMINUM", SlimefunItems.ALUMINUM_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 3)), 5);
        registerArmor(ArmorSet.IRON, "ALUMINUM", SlimefunItems.ALUMINUM_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 2), new Pair<>(Enchantment.DURABILITY, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "LEAD", SlimefunItems.LEAD_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 8)), 5);
        registerArmor(ArmorSet.IRON, "LEAD", SlimefunItems.LEAD_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), new Pair<>(Enchantment.DURABILITY, 8)), 5);

        registerWeapon(ExtraWeapon.IRON, "ZINC", SlimefunItems.ZINC_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2)), 5);
        registerArmor(ArmorSet.IRON, "ZINC", SlimefunItems.ZINC_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 5);

        registerWeapon(ExtraWeapon.IRON, "MAGNESIUM", SlimefunItems.MAGNESIUM_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerArmor(ArmorSet.IRON, "MAGNESIUM", SlimefunItems.MAGNESIUM_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), new Pair<>(Enchantment.DURABILITY, 5)), 5);

        registerWeapon(ExtraWeapon.IRON, "STEEL", SlimefunItems.STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 5), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerArmor(ArmorSet.IRON, "STEEL", SlimefunItems.STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), new Pair<>(Enchantment.DURABILITY, 4)), 5);

        registerWeapon(ExtraWeapon.IRON, "BRONZE", SlimefunItems.BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "DURALUMIN", SlimefunItems.DURALUMIN_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "BILLON", SlimefunItems.BILLON_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 4), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "BRASS", SlimefunItems.BRASS_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 4), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "ALUMINUM_BRASS", SlimefunItems.ALUMINUM_BRASS_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 4), new Pair<>(Enchantment.DURABILITY, 4)), 5);
        registerWeapon(ExtraWeapon.IRON, "ALUMINUM_BRONZE", SlimefunItems.ALUMINUM_BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 4), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "CORINTHIAN_BRONZE", SlimefunItems.CORINTHIAN_BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 5), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "SOLDER", SlimefunItems.SOLDER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 4), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "DAMASCUS_STEEL", SlimefunItems.DAMASCUS_STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 6), new Pair<>(Enchantment.DURABILITY, 7)), 5);
        registerWeapon(ExtraWeapon.IRON, "HARDENED", SlimefunItems.HARDENED_METAL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 7), new Pair<>(Enchantment.DURABILITY, 10)), 5);
        registerWeapon(ExtraWeapon.IRON, "REINFORCED", SlimefunItems.REINFORCED_ALLOY_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 8), new Pair<>(Enchantment.DURABILITY, 8)), 5);
        registerWeapon(ExtraWeapon.IRON, "FERROSILICON", SlimefunItems.FERROSILICON, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 8), new Pair<>(Enchantment.DURABILITY, 4)), 5);
        registerWeapon(ExtraWeapon.GOLD, "GILDED_IRON", SlimefunItems.GILDED_IRON, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 8), new Pair<>(Enchantment.DURABILITY, 10)), 5);
        registerWeapon(ExtraWeapon.IRON, "NICKEL", SlimefunItems.NICKEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 6), new Pair<>(Enchantment.DURABILITY, 5)), 5);

        registerWeapon(ExtraWeapon.IRON, "COBALT", SlimefunItems.COBALT_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 7), new Pair<>(Enchantment.DURABILITY, 7)), 5);
        registerArmor(ArmorSet.IRON, "COBALT", SlimefunItems.COBALT_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 7), new Pair<>(Enchantment.DURABILITY, 7)), 5);

        registerWeapon(ExtraWeapon.DIAMOND, "REINFORCED_DIAMOND", itemStack, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 9), new Pair<>(Enchantment.DURABILITY, 9)), 25);
        registerArmor(ArmorSet.DIAMOND, "REINFORCED_DIAMOND", itemStack, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 9), new Pair<>(Enchantment.DURABILITY, 9)), 25);

        registerWeapon(ExtraWeapon.IRON, "COPPER", SlimefunItems.COPPER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 2)), 5);
        registerArmor(ArmorSet.LEATHER, "COPPER", SlimefunItems.COPPER_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "TIN", SlimefunItems.TIN_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 1)), 5);
        registerArmor(ArmorSet.IRON, "TIN", SlimefunItems.TIN_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 3)), 5);

        registerWeapon(ExtraWeapon.IRON, "SILVER", SlimefunItems.SILVER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2)), 5);
        registerArmor(ArmorSet.IRON, "SILVER", SlimefunItems.SILVER_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "ALUMINUM", SlimefunItems.ALUMINUM_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 3)), 5);
        registerArmor(ArmorSet.IRON, "ALUMINUM", SlimefunItems.ALUMINUM_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_EXPLOSIONS, 2), new Pair<>(Enchantment.DURABILITY, 2)), 5);

        registerWeapon(ExtraWeapon.IRON, "LEAD", SlimefunItems.LEAD_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 8)), 5);
        registerArmor(ArmorSet.IRON, "LEAD", SlimefunItems.LEAD_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), new Pair<>(Enchantment.DURABILITY, 8)), 5);

        registerWeapon(ExtraWeapon.IRON, "ZINC", SlimefunItems.ZINC_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2)), 5);
        registerArmor(ArmorSet.IRON, "ZINC", SlimefunItems.ZINC_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3)), 5);

        registerWeapon(ExtraWeapon.IRON, "MAGNESIUM", SlimefunItems.MAGNESIUM_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 2), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerArmor(ArmorSet.IRON, "MAGNESIUM", SlimefunItems.MAGNESIUM_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 2), new Pair<>(Enchantment.DURABILITY, 5)), 5);

        registerWeapon(ExtraWeapon.IRON, "STEEL", SlimefunItems.STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 5), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerArmor(ArmorSet.IRON, "STEEL", SlimefunItems.STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.PROTECTION_ENVIRONMENTAL, 3), new Pair<>(Enchantment.DURABILITY, 4)), 5);

        registerWeapon(ExtraWeapon.IRON, "BRONZE", SlimefunItems.BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "DURALUMIN", SlimefunItems.DURALUMIN_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 3), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "BILLON", SlimefunItems.BILLON_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 4), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "BRASS", SlimefunItems.BRASS_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 4), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "ALUMINUM_BRASS", SlimefunItems.ALUMINUM_BRASS_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 4), new Pair<>(Enchantment.DURABILITY, 4)), 5);
        registerWeapon(ExtraWeapon.IRON, "ALUMINUM_BRONZE", SlimefunItems.ALUMINUM_BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 4), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "CORINTHIAN_BRONZE", SlimefunItems.CORINTHIAN_BRONZE_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 5), new Pair<>(Enchantment.DURABILITY, 5)), 5);
        registerWeapon(ExtraWeapon.IRON, "SOLDER", SlimefunItems.SOLDER_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 4), new Pair<>(Enchantment.DURABILITY, 6)), 5);
        registerWeapon(ExtraWeapon.IRON, "DAMASCUS_STEEL", SlimefunItems.DAMASCUS_STEEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 6), new Pair<>(Enchantment.DURABILITY, 7)), 5);
        registerWeapon(ExtraWeapon.IRON, "HARDENED", SlimefunItems.HARDENED_METAL_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 7), new Pair<>(Enchantment.DURABILITY, 10)), 5);
        registerWeapon(ExtraWeapon.IRON, "REINFORCED", SlimefunItems.REINFORCED_ALLOY_INGOT, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ALL, 8), new Pair<>(Enchantment.DURABILITY, 8)), 5);
        registerWeapon(ExtraWeapon.IRON, "FERROSILICON", SlimefunItems.FERROSILICON, Arrays.asList(new Pair<>(Enchantment.DAMAGE_UNDEAD, 8), new Pair<>(Enchantment.DURABILITY, 4)), 5);
        registerWeapon(ExtraWeapon.GOLD, "GILDED_IRON", SlimefunItems.GILDED_IRON, Arrays.asList(new Pair<>(Enchantment.DAMAGE_ARTHROPODS, 8), new Pair<>(Enchantment.DURABILITY, 10)), 5);
        registerWeapon(ExtraWeapon.IRON, "NICKEL", SlimefunItems.NICKEL_INGOT, Arrays.asList(new Pair<>(Enchantment.DIG_SPEED, 6), new Pair<>(Enchantment.DURABILITY, 5)), 5);

        registerWeapon(ExtraWeapon.IRON, "COBALT", SlimefunItems.COBALT_INGOT, Arrays.asList(new Pair<>(Enchantment.DIG_SPEED, 7), new Pair<>(Enchantment.DURABILITY, 7)), 5);
        registerArmor(ArmorSet.IRON, "COBALT", SlimefunItems.COBALT_INGOT, Arrays.asList(new Pair<>(Enchantment.DIG_SPEED, 7), new Pair<>(Enchantment.DURABILITY, 7)), 5);

        registerPick(Material.IRON_PICKAXE,"COPPER", SlimefunItems.COPPER_INGOT, Arrays.asList(new Pair<>(Enchantment.DIG_SPEED,2)), 5);
    }

    private void registerWeapon(ExtraWeapon extraweapon, String component, ItemStack item, List<Pair<Enchantment, Integer>> enchantments, Integer cost) {
        String humanizedComponent = ChatUtils.humanize(component);
        SlimefunItemStack[] weapon = { new SlimefunItemStack(component + "_AXE", extraweapon.getAxe(), "&f" + humanizedComponent + " Axe"),
                new SlimefunItemStack(component + "_SWORD", extraweapon.getSword(), "&f" + humanizedComponent + " Sword") };

        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            for (ItemStack is : weapon) {
                is.addUnsafeEnchantment(enchantment.getFirstValue(), enchantment.getSecondValue());
            }
        }

        SlimefunItem axe = new ExtraTool(category, weapon[0], new ItemStack[] { item, item, null, item, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null });
        axe.register(this);

        delId++;

        Research research = new Research(new NamespacedKey(this, component.toLowerCase() + "_axe"), delId, humanizedComponent + " Axe", cost);
        research.addItems(axe);
        research.register();

        SlimefunItem sword = new ExtraTool(category, weapon[1], new ItemStack[] { null, item, null, null, item, null, null, new ItemStack(Material.STICK), null });
        sword.register(this);

        researchId++;

        Research research2 = new Research(new NamespacedKey(this, component.toLowerCase() + "_sword"), researchId, humanizedComponent + " Sword", cost);
        research2.addItems(sword);
        research2.register();
    }

    private void registerArmor(ArmorSet armorset, String component, ItemStack item, List<Pair<Enchantment, Integer>> enchantments, Integer cost) {
        String humanizedComponent = ChatUtils.humanize(component);
        SlimefunItemStack[] armor = { new SlimefunItemStack(component + "_HELMET", armorset.getHelmet(), "&f" + humanizedComponent + " Helmet"),
                new SlimefunItemStack(component + "_CHESTPLATE", armorset.getChestplate(), "&f" + humanizedComponent + " Chestplate"),
                new SlimefunItemStack(component + "_LEGGINGS", armorset.getLeggings(), "&f" + humanizedComponent + " Leggings"),
                new SlimefunItemStack(component + "_BOOTS", armorset.getBoots(), "&f" + humanizedComponent + " Boots") };

        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            for (ItemStack is : armor) {
                is.addUnsafeEnchantment(enchantment.getFirstValue(), enchantment.getSecondValue());
            }
        }

        SlimefunItem helmet = new SlimefunItem(category, armor[0], RecipeType.ARMOR_FORGE, new ItemStack[] { item, item, item, item, null, item, null, null, null });
        helmet.register(this);

        SlimefunItem chestplate = new SlimefunItem(category, armor[1], RecipeType.ARMOR_FORGE, new ItemStack[] { item, null, item, item, item, item, item, item, item });
        chestplate.register(this);

        SlimefunItem leggings = new SlimefunItem(category, armor[2], RecipeType.ARMOR_FORGE, new ItemStack[] { item, item, item, item, null, item, item, null, item });
        leggings.register(this);

        SlimefunItem boots = new SlimefunItem(category, armor[3], RecipeType.ARMOR_FORGE, new ItemStack[] { null, null, null, item, null, item, item, null, item });
        boots.register(this);

        researchId++;

        Research research = new Research(new NamespacedKey(this, component.toLowerCase() + "_armor"), researchId, humanizedComponent + " Armor", cost);
        research.addItems(helmet, chestplate, leggings, boots);
        research.register();
    }

    private void registerPick(Material type, String component, ItemStack item, List<Pair<Enchantment, Integer>> enchantments, Integer cost) {
        SlimefunItemStack is = new SlimefunItemStack(component + "_PICKAXE", type, "&f" + ChatUtils.humanize(component) + " Pickaxe");

        for (Pair<Enchantment, Integer> enchantment : enchantments) {
            is.addUnsafeEnchantment(enchantment.getFirstValue(), enchantment.getSecondValue());
        }

        SlimefunItem slimefunItem = new ExtraTool(extratools, is, new ItemStack[] { item, item, item, null, new ItemStack(Material.STICK), null, null, new ItemStack(Material.STICK), null });
        slimefunItem.register(this);

        delId++;

        Research research = new Research(new NamespacedKey(this, component.toLowerCase() + "_pickaxe"), researchId, ChatUtils.humanize(component) + " Pickaxe", cost);
        research.addItems(slimefunItem);
        research.register();
    }

    public static ExtraGear getInstance() {
        return instance;
    }

    public static Forge getForge() {
        return instance.forge;
    }

    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/DeathlyPanda/ExtraGearX/issues";
    }

}
