package client_sanity.DB_Configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.sql2o.Sql2o;

public class SQL2o_Connection {

    private SQL2o_Connection(){}

    private static Sql2o sql2o_instance;

    public static Sql2o get_connection(HikariDataSource data_source){
        if(sql2o_instance==null){
        sql2o_instance = new Sql2o(data_source);
            return sql2o_instance;
        }
        else {
            return sql2o_instance;
        }
    }
}
