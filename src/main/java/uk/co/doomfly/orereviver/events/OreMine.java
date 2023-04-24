package uk.co.doomfly.orereviver.events;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import uk.co.doomfly.orereviver.ConfigManager;
import uk.co.doomfly.orereviver.Init;
import uk.co.doomfly.orereviver.OreReviver;

public class OreMine implements Listener {
    NamespacedKey key;
    Plugin plugin;

    public OreMine(OreReviver plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.key = new NamespacedKey(plugin, "oreType");
    }

    @EventHandler
    public void onOreMineEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        YamlConfiguration fileloc = YamlConfiguration.loadConfiguration(Init.file);
        if (fileloc.get(block.getLocation().toString()) == null) { return; }
        Player player = event.getPlayer();
        event.setCancelled(true);
        Material type = block.getType();
        if (player.getInventory().getItemInMainHand() == null) { return; }
        block.breakNaturally(player.getInventory().getItemInMainHand());
        block.setType(Material.BEDROCK);

        player.sendMessage(type.name().toUpperCase());
        if (ConfigManager.configMessages.containsKey(type.name().toUpperCase())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.setType(type);
                    this.cancel();
                }
            }.runTaskTimer(plugin, (Integer.parseInt(ConfigManager.configMessages.get(type.name().toUpperCase())) * 20), 1);
        } else {
            new BukkitRunnable() {
                @Override
                public void run() {
                    block.setType(type);
                    this.cancel();
                }
            }.runTaskTimer(plugin, (Integer.parseInt(ConfigManager.configMessages.get("default-timer")) * 20), 1);
        }
    }
}
