/*

* Assignment: Gradebook Project Part 2

* Name: Nicholas Clark

*/
package Project.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    private static Connection connection;
    
    private DBUtil() {}

    public static synchronized Connection getConnection(String username, String password) throws SQLException {
        if (connection != null) {
            return connection;
        }
        else {
            try {
                // set the db url
                String url = "jdbc:mysql://showcreatedb.c3m97x9cisan.us-east-1.rds.amazonaws.com/Gradebook";
                // get and return connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        url, username, password);
                return connection;
            } catch (SQLException | ClassNotFoundException e) {
                throw new SQLException(e);
            }            
        }
    }

    public static synchronized void closeConnection() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            } finally {
                connection = null;
            }
        }
    }
}
