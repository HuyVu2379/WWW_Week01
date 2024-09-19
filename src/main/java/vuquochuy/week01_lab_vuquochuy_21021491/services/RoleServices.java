package vuquochuy.week01_lab_vuquochuy_21021491.services;

import vuquochuy.week01_lab_vuquochuy_21021491.connectDB.ConnectionDB;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.Account;
import vuquochuy.week01_lab_vuquochuy_21021491.entities.GrantAccess;
import vuquochuy.week01_lab_vuquochuy_21021491.repositories.RoleRepository;

import java.sql.Connection;

public class RoleServices {
    RoleRepository grantAccessRepository = new RoleRepository();
    public boolean checkRole(Account account) {
        GrantAccess grantAccess = grantAccessRepository.getRole(account.getAccount_id());
        if(grantAccess.getRole_id().equals("admin") && grantAccess.isIs_grant()){
            return true;
        }
        if(grantAccess.getRole_id().equals("user") && grantAccess.isIs_grant()){
            return false;
        }
        return false;
    }
}
