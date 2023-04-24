package uk.co.doomfly.orereviver.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.co.doomfly.orereviver.OreReviver;

public class OreGive implements CommandExecutor {

    NamespacedKey key;
    Plugin plugin;

    public OreGive(OreReviver plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginCommand("oregive").setExecutor(this);
        this.key = new NamespacedKey(plugin, "oreType");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (!(sender instanceof Player)) { return false; }
        if (args.length <= 0) { return false; }
        if (Material.getMaterial(args[0].toUpperCase()) == null) { return false; }
        ItemStack item = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
        ItemMeta itemM = item.getItemMeta();
        itemM.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', args[0].toUpperCase())));
        itemM.getPersistentDataContainer().set(key, PersistentDataType.STRING, args[0].toUpperCase());
        item.setItemMeta(itemM);
        Player player = (Player) sender;
        player.getInventory().addItem(item);
        return false;
    }
}
