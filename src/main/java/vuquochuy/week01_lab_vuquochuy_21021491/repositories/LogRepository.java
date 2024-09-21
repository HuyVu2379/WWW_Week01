package vuquochuy.week01_lab_vuquochuy_21021491.repositories;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

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
    public List<Log> findAll() {
        String sql = "Select * from Log";
        List<Log> logs = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Log log = new Log();
                log.setAccount_id(rs.getString("account_id"));
                log.setLogin_date(rs.getDate("login_time"));
                log.setLogout_date(rs.getDate("logout_time"));
                log.setDescription(rs.getString("notes"));
                logs.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
    public boolean updateLogoutTime(Date date, String account_id) {
        String sql = "Update log set logout_time = ? where account_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, date);
            statement.setString(2, account_id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
