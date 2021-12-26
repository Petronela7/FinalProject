package uni.finalproject.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    final String DB_URL = "jdbc:mysql://root@127.0.0.1:3306/Labor6";
    final String USER = "root";
    final String PASS = "lutianxing7";

    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    //root@localhost:3306
    //

}
