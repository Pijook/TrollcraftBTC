package pl.pijok.trollcraftbtc.utils;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.pijok.trollcraftbtc.Main;
import pl.pijok.trollcraftbtc.Storage;
import pl.pijok.trollcraftbtc.database.DataSource;
import pl.pijok.trollcraftbtc.essentials.ChatUtils;
import pl.pijok.trollcraftbtc.essentials.Debug;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

public class LogsUtils {

    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    private static ArrayList<String> allowedNicknames = new ArrayList<String>();
    private static String serverName = Main.getInstance().getServer().getMotd();

    public static void loadSettings(){
        allowedNicknames.add("Pijok_");
        allowedNicknames.add("Szatan__");
        allowedNicknames.add("Profesor_Kation");

        Debug.log("[TimeCount] Laduje ustawienia");
        Debug.log("Server: " + serverName);
    }

    public static void insertLog(String nickname, long time){
        new BukkitRunnable(){

            @Override
            public void run() {

                Date date = new Date(System.currentTimeMillis());
                String date_text = formatter.format(date);

                String sql = Storage.INSERT_TO_LOGS;

                sql = sql.replace("%nickname%", nickname);
                sql = sql.replace("%time%", String.valueOf(time));
                sql = sql.replace("%date%", date_text);
                sql = sql.replace("%server_name%", serverName);

                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try{
                    connection = DataSource.getConnection();
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.executeUpdate();

                } catch (SQLException throwables) {
                    Debug.log("&cSomething went wrong while saving log!");
                }

                TimeCountUtils.removePlayer(nickname);

            }
        }.runTaskAsynchronously(Main.getInstance());
    }

    public static void getLogs(Player player, String nickname){
        new BukkitRunnable(){

            @Override
            public void run() {
                String sql = Storage.SHOW_LOGS;
                sql = sql.replace("%nickname%", nickname);

                Connection connection = null;
                PreparedStatement preparedStatement = null;
                ResultSet resultSet = null;

                try{
                    connection = DataSource.getConnection();
                    preparedStatement = connection.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();

                    LinkedList<Log> logs = new LinkedList<>();

                    while(resultSet.next()){
                        Log log = new Log(
                            resultSet.getString("nickname"),
                            resultSet.getLong("time"),
                            resultSet.getString("date"),
                            resultSet.getString("server")
                        );

                        logs.add(log);
                    }

                    showLogs(player, nickname, logs);

                } catch (SQLException throwables) {
                    Debug.log("&aPlayer is already in database!");
                }
            }

        }.runTaskAsynchronously(Main.getInstance());

    }

    public static boolean isAllowed(String nickname){
        return allowedNicknames.contains(nickname);
    }

    public static String getServerName() {
        return serverName;
    }

    public static void showLogs(Player player, String nickname, LinkedList<Log> logs){
        Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {

                ChatUtils.sendMessage(player, "&7Wyniki dla &e&l" + nickname);
                ChatUtils.sendMessage(player, "&7==================");

                for(Log log : logs){

                    String nickname = log.getNickname();
                    String server = log.getServerName();
                    String date = log.getDate();
                    int hours = (int) (log.getTime() / 3600);
                    int minutes = (int) ((log.getTime() % 3600) / 60);

                    ChatUtils.sendMessage(player, "&7" + server + " | &e" + date + " &7| &7" + hours + " h " + minutes + " m");
                }

            }
        });
    }

}
