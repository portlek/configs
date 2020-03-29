package io.github.portlek.configs;

import java.util.Map;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BukkitLinkedManaged extends LinkedManagedBase {

    @SafeVarargs
    protected BukkitLinkedManaged(@NotNull final String chosen,
                                  @NotNull final Map.Entry<String, Object>... objects) {
        super(chosen, objects);
        this.addCustomValue(ItemStack.class, new BukkitItemStackProvider());
    }

    @NotNull
    public final Optional<ItemStack> getItemStack() {
        return this.getItemStack("");
    }

    public final void setItemStack(@NotNull final ItemStack itemstack) {
        this.setItemStack("", itemstack);
    }

    public final void setItemStack(@NotNull final String path, @NotNull final ItemStack itemstack) {
        final ConfigSection section;
        if (path.isEmpty()) {
            section = this;
        } else {
            section = this.getOrCreateSection(path);
        }
        this.getManaged().getCustomValue(ItemStack.class)
            .ifPresent(provided -> provided.set(itemstack, section, path));
    }

    @NotNull
    public final Optional<ItemStack> getItemStack(@NotNull final String path) {
        final ConfigSection section;
        if (path.isEmpty()) {
            section = this;
        } else {
            section = this.getOrCreateSection(path);
        }
        return this.getManaged().getCustomValue(ItemStack.class)
            .flatMap(provided -> provided.get(section, path));
    }

}
