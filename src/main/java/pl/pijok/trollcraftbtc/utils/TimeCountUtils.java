package pl.pijok.trollcraftbtc.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pijok.trollcraftbtc.Main;
import pl.pijok.trollcraftbtc.Storage;
import pl.pijok.trollcraftbtc.database.DataSource;
import pl.pijok.trollcraftbtc.essentials.ChatUtils;
import pl.pijok.trollcraftbtc.essentials.Debug;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

public class TimeCountUtils {

    private static HashMap<String, Long> timeOnServer = new HashMap<>();

    public static void addPlayer(String nickname){
        new BukkitRunnable(){

            @Override
            public void run() {

                timeOnServer.put(nickname, System.currentTimeMillis());

                String sql = Storage.INSERT_NICKNAME;

                sql = sql.replace("%nickname%", nickname);
                sql = sql.replace("%time%", "0");

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){
                    preparedStatement.executeUpdate();

                    Debug.log("&aInserted new player!");

                } catch (SQLException throwables) {
                    Debug.log("&aPlayer is already in database!");
                }
            }


        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void removePlayer(String nickname){
        timeOnServer.remove(nickname);
    }

    public static Long getTimeOnServer(String nickname){
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - timeOnServer.get(nickname));
    }

    public static void savePlayerTime(String nickname){
        new BukkitRunnable(){

            @Override
            public void run() {
                long serverTime = getTimeOnServer(nickname);

                Debug.log("Saving time " + nickname);


                String sql = Storage.SEND_TIME;
                sql = sql.replace("%nickname%", nickname);
                sql = sql.replace("%time%", String.valueOf(serverTime));

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    preparedStatement.executeUpdate();

                    Debug.log("Saved time!");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                LogsUtils.insertLog(nickname, getTimeOnServer(nickname));

            }
        }.runTaskAsynchronously(Main.getInstance());


    }

    public static void getPlayerTime(Player player, String target){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.GET_TIME;

                sql = sql.replace("%nickname%", target);

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    resultSet = preparedStatement.executeQuery();


                    if(resultSet.next()){
                        Debug.log("Has next");
                        long time = resultSet.getLong("time");

                        showTimePlayer(player, target, time);

                    }
                    else{
                        TimeCountUtils.cannotFindPlayer(player);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void getPlayerTime(String target, boolean console){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.GET_TIME;

                sql = sql.replace("%nickname%", target);

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    resultSet = preparedStatement.executeQuery();


                    if(resultSet.next()){
                        Debug.log("Has next");
                        long time = resultSet.getLong("time");

                        if(console){
                            showTimePlayerToConsole(target, time);
                        }


                    }
                    else{
                        //TimeCountUtils.cannotFindPlayer(player);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void resetPlayer(String nickname){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.CLEAR_NICKNAME;

                sql = sql.replace("%nickname%", nickname);

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    preparedStatement.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void resetAllPlayers(){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.CLEAR_ALL;

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    preparedStatement.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }

        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void getAllPlayersTime(Player player){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.GET_ALL;

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql); ){
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        Debug.log("Has next");
                        long time = resultSet.getLong("time");
                        String nickname = resultSet.getString("nickname");
                        showTimePlayer(player, nickname, time);
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void getTop(Player player){
        new BukkitRunnable(){

            @Override
            public void run() {

                String sql = Storage.GET_TOP;

                //Connection connection = null;
                //PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try(Connection connection = DataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql);){

                    //preparedStatement = connection.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();

                    int i = 1;

                    LinkedHashMap<String, Long> places = new LinkedHashMap<>();

                    while(resultSet.next()){
                        String nickname = resultSet.getString("nickname");
                        long time = resultSet.getLong("time");

                        //showTopPlace(player, nickname, time, i);
                        places.put(nickname, time);
                        i++;
                    }

                    showTop(player, places);

                }
                catch (SQLException throwables){
                    throwables.printStackTrace();
                    //Debug.log("&cSomething went wrong while getting top");
                }


            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void showTop(Player player, LinkedHashMap<String, Long> places){

        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                int i = 1;

                for(String nickname : places.keySet()){
                    int hours = (int) (places.get(nickname) / 3600);
                    int minutes = (int) ((places.get(nickname) % 3600) / 60);
                    ChatUtils.sendMessage(player,"&6&l" + i + ". &e" + nickname + ": &7" + hours + " h " + minutes + " m");
                    i++;
                }

            }
        });

    }

    public static void showTimePlayer(Player player, String nickname, long time){
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                int hours = (int) (time / 3600);
                int minutes = (int) ((time % 3600) / 60);

                ChatUtils.sendMessage(player, "&e" + nickname + ": &7" + hours + " h " + minutes + " m");
            }
        });
    }

    public static void showTimePlayerToConsole(String nickname, long time){
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                int hours = (int) (time / 3600);
                int minutes = (int) ((time % 3600) / 60);

                Debug.log( "&e" + nickname + ": &7" + hours + " h " + minutes + " m");
            }
        });
    }

    public static void cannotFindPlayer(Player player){
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                ChatUtils.sendMessage(player, "&cNie znaleziono gracza w bazie danych!");
            }
        });
    }
}
