package io.github.portlek.configs;

import java.io.File;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

public final class TestBukkitPlugin extends JavaPlugin {

    public TestBukkitPlugin() {
    }

    public TestBukkitPlugin(final JavaPluginLoader loader, final PluginDescriptionFile description,
                            final File dataFolder, final File file) {
        super(loader, description, new File("build"), file);
    }

    @Override
    public void onEnable() {

    }

}