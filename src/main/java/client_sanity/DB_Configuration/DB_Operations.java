package client_sanity.DB_Configuration;

import static client_sanity.DB_Configuration.DB_Connection.ds;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

public class DB_Operations {

    public List<Map<String, Object>> read_query(String query) {
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);

        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query).executeAndFetchTable().asList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute query: " + query, e);
        }
    }

    public List<Map<String, Object>> read_query(String r_query, Map<String, Object> params) {
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);

        try (Connection conn = sql2o.open()) {
            Query q = conn.createQuery(r_query);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                q.addParameter(entry.getKey(), entry.getValue());
            }
            return q.executeAndFetchTable().asList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute query: " + r_query + " params: " + params, e);
        }
    }

    public Object read_single_value(String query) {

        List<Map<String, Object>> results = read_query(query);
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> firstRow = results.get(0);
        if (firstRow.isEmpty()) {
            return null;
        }

        // this returns cell [1][1]
        return firstRow.values().iterator().next();
    }

    public Object read_single_value(String r_query, Map<String, Object> params) {

        List<Map<String, Object>> results = read_query(r_query, params);
        if (results.isEmpty()) {
            return null;
        }

        Map<String, Object> first_row = results.get(0);
        if (first_row.isEmpty()) {
            return null;
        }

        // this returns cell [1][1]
        return first_row.values().iterator().next();
    }

    public boolean check_record_exists(String query) {

        Object result = read_single_value(query);
        if (result == null) {
            return false;
        }

        if (result instanceof Long) {
            return (Long) result > 0;
        } else if (result instanceof Integer) {
            return (Integer) result > 0;
        } else if (result instanceof Boolean) {
            return (Boolean) result;
        }

        return false;
    }

    public boolean check_record_exists(String r_query, Map<String, Object> params) {

        Object result = read_single_value(r_query, params);
        if (result == null) {
            return false;
        }

        if (result instanceof Long) {
            return (Long) result > 0;
        } else if (result instanceof Integer) {
            return (Integer) result > 0;
        } else if (result instanceof Boolean) {
            return (Boolean) result;
        }

        return false;
    }

    public Object read_coloumn(String query, String colName) {
        List<Map<String, Object>> results = read_query(query);
        if (results.isEmpty()) {
            return null;
        }
        // get [1][colname] value
        return results.get(0).get(colName);
    }

    public Object read_cell(List<Map<String, Object>> results, int row, String colName) {
        if (results == null || results.isEmpty()) {
            return null;
        }
        if (row < 1 || row > results.size()) {
            throw new IndexOutOfBoundsException("Row/Results out of bounds");
        }

        Map<String, Object> rowData = results.get(row - 1);
        if (!rowData.containsKey(colName)) {
            throw new IllegalArgumentException("Coloumn not found in the " + rowData.keySet());
        }
        // Get specific cell value [row][colName]
        return rowData.get(colName);
    }

    public int update(String query) throws SQLException {
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);

        try (Connection conn = sql2o.open()) {
            return conn.createQuery(query).executeUpdate().getResult();
        } catch (Exception e) {
            throw new SQLException("Failed to execute" + query, e);
        }
    }

    public int update(String u_query, Map<String, Object> params) throws SQLException {
        Sql2o sql2o = SQL2o_Connection.get_connection(ds);

        try (Connection conn = sql2o.open()) {

            Query query = conn.createQuery(u_query);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.addParameter(entry.getKey(), entry.getValue());
            }
            return query.executeUpdate().getResult();

        } catch (Exception e) {
            throw new SQLException("Failed to execute " + u_query + " with params " + params, e);
        }
    }

    public void print_results(List<Map<String, Object>> results) {
        if (results.isEmpty()) {
            System.out.println("Results empty");
        }
        System.out.println("Results size: " + results.size());

        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> row = results.get(i);
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.printf(entry.getKey(), entry.getValue());
            }
        }
    }

    public void print_row(Map<String, Object> row) {
        if (row.isEmpty()) {
            System.out.println("row is empty");
        }
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            System.out.printf(entry.getKey(), entry.getValue());
        }
    }
}