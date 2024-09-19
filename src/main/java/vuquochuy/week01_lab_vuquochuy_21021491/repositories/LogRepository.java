package vuquochuy.week01_lab_vuquochuy_21021491.repositories;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogRepository {
    Connection connection = ConnectionDB.getConnection();

    public int add(Log log) {
        String sql = "Insert into Log (account_id, login_time, logout_time, notes) values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, log.getAccount_id());
            statement.setDate(2, log.getLogin_date());
            statement.setDate(3, log.getLogout_date());
            statement.setString(4, log.getDescription());
            statement.executeUpdate();
            return statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
}
