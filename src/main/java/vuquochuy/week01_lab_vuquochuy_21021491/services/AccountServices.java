package vuquochuy.week01_lab_vuquochuy_21021491.services;

import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.AccountRepository;

import java.sql.SQLException;

public class AccountServices {
    AccountRepository accountRepository = new AccountRepository();
    public int checkLogin(String username, String password) {
        try {
            Account account = accountRepository.findByEmail(username);
            if(account == null){
                return -1;
            }
            if(checkValidPassword(password,account.getPassword())){
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public boolean checkValidPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        return false;
    }
}
