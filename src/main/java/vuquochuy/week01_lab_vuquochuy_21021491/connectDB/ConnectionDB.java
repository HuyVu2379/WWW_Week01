package vuquochuy.week01_lab_vuquochuy_21021491.connectDB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            String url = "jdbc:mariadb://localhost:3306/week1";
            connection = DriverManager.getConnection(url, "root", "12345678");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}