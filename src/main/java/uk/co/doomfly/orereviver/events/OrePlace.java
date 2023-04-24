package uk.co.doomfly.orereviver.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import uk.co.doomfly.orereviver.ConfigManager;
import uk.co.doomfly.orereviver.Init;
import uk.co.doomfly.orereviver.OreReviver;

import java.io.IOException;

public class OrePlace implements Listener {

    NamespacedKey key;

    public OrePlace(OreReviver plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.key = new NamespacedKey(plugin, "oreType");
    }

    @EventHandler
    public void onOrePlaceEvent(PlayerInteractEvent event) throws IOException {
        ItemStack item = event.getItem();
        Block block = event.getClickedBlock();
        if (item == null || item.equals(Material.BEDROCK)) { return; }
        if (block == null || block.equals(Material.AIR)) { return; }
        ItemMeta itemM = item.getItemMeta();
        if (itemM == null) { return; }
        PersistentDataContainer container = itemM.getPersistentDataContainer();
        if (!container.has(key, PersistentDataType.STRING)) { return; }
        String oreTypeString = container.get(key, PersistentDataType.STRING);
        Material oreType = Material.getMaterial(oreTypeString.toUpperCase());
        event.setCancelled(true);
        Location loc = block.getLocation().add(0, 1, 0);
        loc.getBlock().setType(oreType);
        YamlConfiguration fileloc = YamlConfiguration.loadConfiguration(Init.file);
        fileloc.set(loc.toString(), oreType.toString().toUpperCase());
        fileloc.save(Init.file);
    }
}
