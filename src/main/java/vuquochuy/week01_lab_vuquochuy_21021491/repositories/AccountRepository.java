package vuquochuy.week01_lab_vuquochuy_21021491.repositories;
import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AccountRepository {
    Connection connection = ConnectionDB.getConnection();
    public Account findByEmail(String email) throws SQLException {
        String sql = "Select * from Account where email = ?";
        Account account = null;
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
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }
}
