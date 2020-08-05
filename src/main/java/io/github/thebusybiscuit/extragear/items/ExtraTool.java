package io.github.thebusybiscuit.extragear.items;

import io.github.thebusybiscuit.extragear.ExtraGearRecipeTypes;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class ExtraTool extends SlimefunItem implements NotPlaceable {

    public ExtraTool(Category category, SlimefunItemStack item, ItemStack[] recipe) {
        super(category, item, ExtraGearRecipeTypes.TOOLFORGE, recipe);
    }

}
