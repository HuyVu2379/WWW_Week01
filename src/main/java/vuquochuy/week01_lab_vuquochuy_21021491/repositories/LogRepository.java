package vuquochuy.week01_lab_vuquochuy_21021491.repositories;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogRepository {
    Connection connection = ConnectionDB.getConnection();

    public int add(Log log) {
        String sql = "Insert into Log (account_id, login_time, logout_time, notes) values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, log.getAccount_id());
            statement.setTimestamp(2, log.getLogin_date());
            statement.setTimestamp(3, log.getLogout_date());
            statement.setString(4, log.getDescription());
            return statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
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
                log.setLogin_date(rs.getTimestamp("login_time"));
                log.setLogout_date(rs.getTimestamp("logout_time"));
                log.setDescription(rs.getString("notes"));
                logs.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }
    public boolean updateLogoutTime(Timestamp date, String account_id) {
        String sql = "Update log set logout_time = ? where account_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, date);
            statement.setString(2, account_id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean delete(Timestamp date) {
        String sql = "Delete from Log where login_time = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, date);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
