package io.github.portlek.configs.util;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import io.github.portlek.configs.CfgSection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class BukkitItemStackProvider implements Provided<ItemStack> {

    private static final BukkitVersion BUKKIT_VERSION = new BukkitVersion();

    @NotNull
    private static String putDot(@NotNull final String text) {
        final String fnltext;
        if (text.isEmpty() || text.charAt(text.length() - 1) == '.') {
            fnltext = text;
        } else {
            fnltext = text + '.';
        }
        return fnltext;
    }

    @Override
    public void set(@NotNull final ItemStack itemStack, @NotNull final CfgSection section,
                    @NotNull final String path) {
        final String fnlpath = BukkitItemStackProvider.putDot(path);
        section.set(fnlpath + "material", itemStack.getType().name());
        section.set(fnlpath + "amount", itemStack.getAmount());
        if (BukkitItemStackProvider.BUKKIT_VERSION.minor() < 13) {
            Optional.ofNullable(itemStack.getData()).ifPresent(materialData ->
                section.set(fnlpath + "data", (int) materialData.getData()));
        }
        if (itemStack.getDurability() != 0) {
            section.set(fnlpath + "damage", itemStack.getDurability());
        }
        Optional.ofNullable(itemStack.getItemMeta()).ifPresent(itemMeta -> {
            if (itemMeta.hasDisplayName()) {
                section.set(
                    fnlpath + "display-name",
                    itemMeta.getDisplayName().replace("§", "&")
                );
            }
            Optional.ofNullable(itemMeta.getLore()).ifPresent(lore ->
                section.set(
                    fnlpath + "lore",
                    lore.stream()
                        .map(s -> s.replace("§", "&"))
                        .collect(Collectors.toList())
                )
            );
            section.set(fnlpath + "flags", itemMeta.getItemFlags().stream()
                .map(Enum::name)
                .collect(Collectors.toList()));
        });
        itemStack.getEnchantments().forEach((enchantment, integer) ->
            section.set(fnlpath + "enchants." + enchantment.getName(), integer)
        );
    }

    @NotNull
    @Override
    public Optional<ItemStack> get(@NotNull final CfgSection section, @NotNull final String path) {
        final String fnlpath = BukkitItemStackProvider.putDot(path);
        final Optional<String> optional = section.getString(fnlpath + "material");
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        final String mtrlstrng = optional.get();
        final Material material;
        if (BukkitItemStackProvider.BUKKIT_VERSION.minor() > 7) {
            final Optional<XMaterial> xmaterialoptional = XMaterial.matchXMaterial(mtrlstrng);
            if (!xmaterialoptional.isPresent()) {
                return Optional.empty();
            }
            final Optional<Material> mtrloptnl = Optional.ofNullable(xmaterialoptional.get().parseMaterial());
            if (!mtrloptnl.isPresent()) {
                return Optional.empty();
            }
            material = mtrloptnl.get();
        } else {
            material = Material.getMaterial(mtrlstrng);
        }
        final int amount = section.getInt(fnlpath + "amount");
        final int fnlamnt;
        if (amount == 0) {
            fnlamnt = 1;
        } else {
            fnlamnt = amount;
        }
        final ItemStack itemStack;
        if (BukkitItemStackProvider.BUKKIT_VERSION.minor() < 13) {
            itemStack = new ItemStack(
                material,
                fnlamnt,
                (short) section.getInt(fnlpath + "damage"),
                (byte) section.getInt(fnlpath + "data")
            );
        } else {
            itemStack = new ItemStack(
                material,
                fnlamnt,
                (short) section.getInt(fnlpath + "damage")
            );
        }
        Optional.ofNullable(itemStack.getItemMeta()).ifPresent(itemMeta -> {
            section.getString(fnlpath + "display-name").ifPresent(s ->
                itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', s))
            );
            itemMeta.setLore(
                section.getStringList(fnlpath + "lore").stream()
                    .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                    .collect(Collectors.toList()));
            section.getSection(fnlpath + "enchants").map(enchsection ->
                enchsection.getKeys(false)
            ).ifPresent(set ->
                set.forEach(s ->
                    XEnchantment.matchXEnchantment(s).flatMap(xEnchantment ->
                        Optional.ofNullable(xEnchantment.parseEnchantment())
                    ).ifPresent(enchantment ->
                        itemMeta.addEnchant(enchantment, section.getInt(fnlpath + "enchants." + s), true)
                    )
                )
            );
            section.getStringList(fnlpath + "flags").stream()
                .map(ItemFlag::valueOf)
                .forEach(itemMeta::addItemFlags);
            itemStack.setItemMeta(itemMeta);
        });
        return Optional.of(itemStack);
    }

}
