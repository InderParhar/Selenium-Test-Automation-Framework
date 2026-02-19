package client_sanity.DB_Configuration;

import org.sql2o.Sql2o;

import static client_sanity.DB_Configuration.DB_Connection.ds;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Old_DB_Operations {

    private int rows_affected;
    public List<Map<String,Object>> results;

    public List<Map<String, Object>> read_query(String query) {
        
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);
        org.sql2o.Connection conn = null;
        try {
            conn = sql2o.open();
            return conn.createQuery(query).executeAndFetchTable().asList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public boolean update(String query) throws SQLException {
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);

        try(org.sql2o.Connection conn = sql2o.open()) {
            rows_affected = conn.createQuery(query).executeUpdate().getResult();
            return rows_affected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void read_results(List<Map<String, Object>> results) {
        this.results = results;
        for (Map<String, Object> result : results) {
            for (Map.Entry<String, Object> entry : result.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    public Object read_specific_results(List<Map<String, Object>> results,int row,String col) {
        this.results = results;
        return results.get(row-1).get(col);
    }
}
