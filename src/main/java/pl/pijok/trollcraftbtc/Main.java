package pl.pijok.trollcraftbtc;

import org.bukkit.plugin.java.JavaPlugin;
import pl.pijok.trollcraftbtc.commands.TimeCommand;
import pl.pijok.trollcraftbtc.database.DataSource;
import pl.pijok.trollcraftbtc.listeners.JoinListener;
import pl.pijok.trollcraftbtc.listeners.QuitListener;
import pl.pijok.trollcraftbtc.utils.LogsUtils;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        loadStuff();

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);

        getCommand("aktywnosc").setExecutor(new TimeCommand());
    }

    @Override
    public void onDisable() {

    }

    public static void loadStuff(){

        DataSource.setData();
        LogsUtils.loadSettings();

    }

    public static Main getInstance() {
        return instance;
    }
}
