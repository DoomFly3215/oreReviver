package uk.co.doomfly.orereviver;

import org.bukkit.plugin.java.JavaPlugin;
import uk.co.doomfly.orereviver.commands.OreGive;
import uk.co.doomfly.orereviver.events.OreMine;
import uk.co.doomfly.orereviver.events.OrePlace;

import java.io.IOException;

public final class OreReviver extends JavaPlugin {

    @Override
    public void onEnable() {
        try { ConfigManager.Setup(); } catch (IOException e) { throw new RuntimeException(e);  }
        try { new Init(this); } catch (IOException e) { throw new RuntimeException(e); }
        new OrePlace(this);
        new OreMine(this);
        new OreGive(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
