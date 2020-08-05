package io.github.thebusybiscuit.extragear;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;


public final class ExtraGearRecipeTypes {

    private ExtraGearRecipeTypes() {}

    public static final RecipeType TOOLFORGE = new RecipeType(new NamespacedKey(ExtraGear.instance, "tool_forge"), new SlimefunItemStack("TOOL_FORGE", Material.ANVIL, "&eTool Forge"), "", "&fThis item must be made", "&fin a Tool Forge");

}
