package uk.co.doomfly.orereviver;

import java.io.File;
import java.io.IOException;

public class Init {
    public static File file = new File("./plugins/OreReviver/locations.yml");

    public Init(OreReviver plugin) throws IOException {
        if (file.exists()) { return; } else {
            file.createNewFile();
        }
    }
}
