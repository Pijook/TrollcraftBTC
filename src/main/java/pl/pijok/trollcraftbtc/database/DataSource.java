package pl.pijok.trollcraftbtc.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.pijok.trollcraftbtc.Main;
import pl.pijok.trollcraftbtc.essentials.ConfigUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    private static String username;
    private static String password;
    private static String host;
    private static String port;
    private static String database;

    public static void setData(){
        YamlConfiguration configuration = ConfigUtils.load("config.yml", Main.getInstance());

        username = configuration.getString("username");
        password = configuration.getString("password");
        host = configuration.getString("host");
        port = configuration.getString("port");
        database = configuration.getString("database");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.addDataSourceProperty( "autoReconnect",true);
        //config.addDataSourceProperty("useServerPrepStmts", true);
        //config.addDataSourceProperty("cacheResultSetMetadata", true);

        ds = new HikariDataSource(config);

    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
