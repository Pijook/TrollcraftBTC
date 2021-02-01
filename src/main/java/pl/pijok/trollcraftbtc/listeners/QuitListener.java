package pl.pijok.trollcraftbtc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.pijok.trollcraftbtc.Storage;
import pl.pijok.trollcraftbtc.utils.TimeCountUtils;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        if(player.hasPermission(Storage.getTime_count_perm())){
            TimeCountUtils.savePlayerTime(player.getName());
        }

    }
}
