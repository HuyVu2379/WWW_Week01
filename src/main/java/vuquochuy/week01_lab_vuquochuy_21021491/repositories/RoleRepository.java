package vuquochuy.week01_lab_vuquochuy_21021491.repositories;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.GrantAccess;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Role;
import vuquochuy.week01_lab_vuquochuy_21021491.services.AccountServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleRepository {
    Connection connection = ConnectionDB.getConnection();
    AccountServices accountServices = new AccountServices();
    public GrantAccess getRole(String account_id) {
        String sql = "Select * from grant_access join role on grant_access.role_id = role.role_id where account_id = ?";
        GrantAccess grantAccess = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, account_id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                grantAccess.setAccount_id(account_id);
                grantAccess.setRole_id(rs.getString("role_id"));
                grantAccess.setIs_grant(rs.getBoolean("is_grant"));
                grantAccess.setNote(rs.getString("note"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return grantAccess;
    }
    public List<Role> findAll(){
        String sql = "Select * from role";
        List<Role> roles = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String role_id = rs.getString("role_id");
                String role_name = rs.getString("role_name");
                String description = rs.getString("description");
                int status = rs.getInt("status");
                Role role = new Role(role_id, role_name, description, status);
                roles.add(role);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roles;
    }

    public void addGrantAccess(GrantAccess grantAccess){
        String sql = "Insert into grant_access (account_id, role_id, is_grant, note) values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, grantAccess.getAccount_id());
            statement.setString(2, grantAccess.getRole_id());
            statement.setBoolean(3, grantAccess.isIs_grant());
            statement.setString(4, grantAccess.getNote());
            System.out.println(statement);
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public boolean addRole(Role role){
        String sql = "Insert into role (role_id, role_name, description, status) values (?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.getRole_id());
            statement.setString(2, role.getRole_name());
            statement.setString(3, role.getDescription());
            statement.setInt(4, role.getStatus());
            statement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(Role role) {
        String sql = "Update role set role_name = ?, description = ?, status = ? where role_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role.getRole_name());
            statement.setString(2, role.getDescription());
            statement.setInt(3, role.getStatus());
            statement.setString(4, role.getRole_id());
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String role_id) {
        String sql = "Delete from role where role_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role_id);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
