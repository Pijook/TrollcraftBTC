package pl.pijok.trollcraftbtc;

public class Storage {

    // ----- -----
    public static String SEND_TIME = "UPDATE time_count SET time=time + %time% WHERE nickname='%nickname%';";
    public static String INSERT_NICKNAME = "INSERT INTO time_count (nickname, time) VALUES ('%nickname%', '%time%');";
    public static String GET_TIME = "SELECT * FROM time_count WHERE nickname='%nickname%';";
    public static String GET_TOP = "SELECT * FROM time_count ORDER BY `time` DESC LIMIT 10;";
    public static String GET_ALL = "SELECT * FROM time_count;";
    public static String CLEAR_NICKNAME = "DELETE FROM time_count WHERE nickname='%nickname%';";
    public static String CLEAR_ALL = "TRUNCATE TABLE time_count;";
    // ----- -----
    private static String time_count_perm = "timecount.count";
    private static String time_count_own = "timecount.own";
    private static String time_count_other = "timecount.other";
    private static String time_count_reset = "timecount.reset";
    private static String time_count_top = "timecount.top";
    // ----- -----
    public static String INSERT_TO_LOGS = "INSERT INTO logs (nickname, time, date, server) VALUES ('%nickname%', '%time%', '%date%', '%server_name%');";
    public static String SHOW_LOGS = "SELECT * FROM logs WHERE nickname = '%nickname%' ORDER BY id ASC";
    // ----- -----
    public static String NO_PERMISSION = "&cNie masz dostepu do tej komendy!";

    public static String getTime_count_perm() {
        return time_count_perm;
    }

    public static String getTime_count_own() {
        return time_count_own;
    }

    public static String getTime_count_other() {
        return time_count_other;
    }

    public static String getTime_count_reset() {
        return time_count_reset;
    }

    public static String getTime_count_top() {
        return time_count_top;
    }

}
