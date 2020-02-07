/*
 * MIT License
 *
 * Copyright (c) 2019 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package io.github.portlek.configs.util;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class BukkitItemBuilder extends ItemStack {

    private BukkitItemBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
    }

    public BukkitItemBuilder name(@NotNull String displayName) {
        return name(displayName, true);
    }

    public BukkitItemBuilder name(@NotNull String displayName, boolean colored) {
        if (getItemMeta() == null) {
            return this;
        }

        if (colored) {
            getItemMeta().setDisplayName(
                ColorUtil.colored(displayName)
            );
        } else {
            getItemMeta().setDisplayName(displayName);
        }

        return this;
    }

    public BukkitItemBuilder data(int data) {
        return data((byte) data);
    }

    public BukkitItemBuilder data(byte data) {
        final MaterialData materialData = getData();

        materialData.setData(data);
        setData(materialData);

        return this;
    }

    public BukkitItemBuilder lore(@NotNull String... lore) {
        return lore(
            Arrays.asList(
                lore
            ),
            true
        );
    }

    public BukkitItemBuilder lore(@NotNull List<String> lore, boolean colored) {
        if (getItemMeta() == null) {
            return this;
        }

        if (colored) {
            getItemMeta().setLore(
                ColorUtil.colored(lore)
            );
        } else {
            getItemMeta().setLore(
                lore
            );
        }

        return this;
    }

    public BukkitItemBuilder enchantments(@NotNull String... enchantments) {
        for (String s : enchantments) {
            final String[] split = s.split(":");
            final String enchantment;
            final int level;

            if (split.length == 1) {
                enchantment = split[0];
                level = 1;
            } else {
                enchantment = split[0];
                level = getInt(split[1]);
            }

            XEnchantment.matchXEnchantment(enchantment).ifPresent(xEnchantment -> enchantments(xEnchantment, level));
        }

        return this;
    }

    public BukkitItemBuilder enchantments(@NotNull XEnchantment enchantment, int level) {
        final Optional<Enchantment> enchantmentOptional = Optional.ofNullable(enchantment.parseEnchantment());

        if (enchantmentOptional.isPresent()) {
            return enchantments(enchantmentOptional.get(), level);
        }

        return this;
    }

    public BukkitItemBuilder enchantments(@NotNull Enchantment enchantment, int level) {
        final Map<Enchantment, Integer> map = new HashMap<>();

        map.put(enchantment, level);

        return enchantments(map);
    }

    public BukkitItemBuilder enchantments(@NotNull Map<Enchantment, Integer> enchantments) {
        addUnsafeEnchantments(enchantments);

        return this;
    }

    private int getInt(@NotNull String string) {
        try {
            return Integer.parseInt(string);
        } catch (Exception ignored) {
            // ignored
        }

        return 0;
    }

    public static BukkitItemBuilder of(@NotNull XMaterial xMaterial) {
        final Material material = xMaterial.parseMaterial();

        if (material == null) {
            throw new IllegalStateException("Material of the " + xMaterial.name() + " cannot be null!");
        }

        return new BukkitItemBuilder(
            new ItemStack(material)
        );
    }

    public static BukkitItemBuilder of(@NotNull Material material) {
        return new BukkitItemBuilder(
            new ItemStack(material)
        );
    }

    public static BukkitItemBuilder of(@NotNull ItemStack itemStack) {
        return new BukkitItemBuilder(itemStack);
    }

}