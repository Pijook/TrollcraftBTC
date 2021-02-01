package pl.pijok.trollcraftbtc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pijok.trollcraftbtc.Storage;
import pl.pijok.trollcraftbtc.utils.TimeCountUtils;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(player.hasPermission(Storage.getTime_count_perm())){
            TimeCountUtils.addPlayer(player.getName());
        }
    }
}
