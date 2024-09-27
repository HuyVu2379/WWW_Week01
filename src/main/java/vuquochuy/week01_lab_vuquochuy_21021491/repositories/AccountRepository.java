package vuquochuy.week01_lab_vuquochuy_21021491.repositories;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.GrantAccess;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    Connection connection = ConnectionDB.getConnection();

    public Account findByEmail(String email) throws SQLException {
        String sql = "Select * from Account where email = ?";
        Account account = new Account();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                account.setAccount_id(rs.getString("account_id"));
                account.setFull_name(rs.getString("full_name"));
                account.setEmail(rs.getString("email"));
                account.setPassword(rs.getString(("password")));
                account.setPhone(rs.getString("phone"));
                account.setSatatus(rs.getInt("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public List<Account> findAll() {
        String sql = "Select * from Account";
        List<Account> accounts = new ArrayList<Account>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String account_id = rs.getString("account_id");
                String full_name = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String phone = rs.getString("phone");
                int status = rs.getInt("status");
                Account account = new Account(account_id, full_name, email, password, phone, status);
                accounts.add(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void add(Account account) throws SQLException {
        String sql = "Insert into Account(account_id,full_name,email,password,phone,status) values(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getAccount_id());
            statement.setString(2, account.getFull_name());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getPassword());
            statement.setString(5, account.getPhone());
            statement.setInt(6, account.getSatatus());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void update(Account account) throws SQLException {
        String sql = "Update Account set full_name = ?, email = ?, password = ?, phone = ?, status = ? where account_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account.getFull_name());
            statement.setString(2, account.getEmail());
            statement.setString(3, account.getPassword());
            statement.setString(4, account.getPhone());
            statement.setInt(5, account.getSatatus());
            statement.setString(6, account.getAccount_id());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<GrantAccess> getRoleForAccount(String account_id) {
        String sql = "Select * from grant_access where account_id = ?";
        List<GrantAccess> grantAccesses = new ArrayList<GrantAccess>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String role_id = rs.getString("role_id");
                String account_id1 = rs.getString("account_id");
                GrantAccess grantAccess = new GrantAccess(account_id1, role_id);
                grantAccesses.add(grantAccess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return grantAccesses;
    }

    public boolean delete(String account_id) throws SQLException {
        String sql = "Delete from Account where account_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account_id);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
