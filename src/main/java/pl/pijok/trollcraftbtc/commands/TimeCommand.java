package pl.pijok.trollcraftbtc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.pijok.trollcraftbtc.Storage;
import pl.pijok.trollcraftbtc.utils.TimeCountUtils;
import pl.pijok.trollcraftbtc.essentials.ChatUtils;
import pl.pijok.trollcraftbtc.essentials.Debug;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){

            if(args.length == 2) {
                if (args[0].equalsIgnoreCase("zobacz")) {
                    String nickname = args[1];
                    TimeCountUtils.getPlayerTime(nickname, true);
                }
                else{
                    Debug.log("Komenda tylko dla graczy!");
                }
            }
            else {
                Debug.log("Komenda tylko dla graczy!");
            }
            return true;
        }

        Player player = (Player) sender;


        if(args.length == 0){
            if(player.hasPermission(Storage.getTime_count_own())){
                TimeCountUtils.getPlayerTime(player, player.getName());
            }
            else{
                ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
            }
            return true;
        }

        if(args.length == 1){
            if(args[0].equalsIgnoreCase("top")){
                if(player.hasPermission(Storage.getTime_count_top())){
                    TimeCountUtils.getTop(player);

                }
                else{
                    ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("showall")){
                if(player.hasPermission(Storage.getTime_count_other())){
                    TimeCountUtils.getAllPlayersTime(player);
                }
                else{
                    ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("resetall")){
                if(player.hasPermission(Storage.getTime_count_reset())){
                    TimeCountUtils.resetAllPlayers();
                    ChatUtils.sendMessage(player, "&cZresetowano czas!");
                }
                else{
                    ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
                }
                return true;
            }
        }

        if(args.length == 2){
            if(args[0].equalsIgnoreCase("zobacz")){
                if(player.hasPermission(Storage.getTime_count_other())){
                    String nickname = args[1];
                    TimeCountUtils.getPlayerTime(player, nickname);
                }
                else{
                    ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
                }
                return true;
            }
            if(args[0].equalsIgnoreCase("reset")){
                if(player.hasPermission(Storage.getTime_count_reset())){
                    String nickname = args[1];
                    TimeCountUtils.resetPlayer(nickname);
                    ChatUtils.sendMessage(player, "&cZresetowano czas!");
                }
                else{
                    ChatUtils.sendMessage(player, Storage.NO_PERMISSION);
                }
                return true;
            }
        }


        ChatUtils.sendMessage(player, "&7/" + label + " top"); //Done
        ChatUtils.sendMessage(player, "&7/" + label + " zobacz <nick>"); //Done
        ChatUtils.sendMessage(player, "&7/" + label + " showall"); //Done
        ChatUtils.sendMessage(player, "&7/" + label + " reset <nick>"); //Done
        ChatUtils.sendMessage(player, "&7/" + label + " resetall"); //Done
        return true;
    }
}
