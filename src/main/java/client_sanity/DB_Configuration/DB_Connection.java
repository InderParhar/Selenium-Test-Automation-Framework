package client_sanity.DB_Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class DB_Connection{

    protected static HikariDataSource ds;
    private static DB_Connection db_instance;
    private Connection conn;

    Old_DB_Operations db_ops =  new Old_DB_Operations();

    static {
        HikariConfig config = new HikariConfig("dbconfig.properties");
        ds = new HikariDataSource(config);
    }

    private DB_Connection() {
    }

    public static DB_Connection getInstance() {
        if (db_instance == null) {
            db_instance = new DB_Connection();
        }
        return db_instance;
    }

    public Connection get_connection() throws SQLException {
        return ds.getConnection();
    }

    public void close_connection() throws SQLException {
        if (conn.isValid(10) && conn != null) {
            conn.close();
        }
    }
}