package vuquochuy.week01_lab_vuquochuy_21021491.entities;

import java.util.Objects;

public class Account {
    private String account_id;
    private String full_name;
    private String password;
    private String email;
    private String phone;
    private int satatus;

    public Account(String account_id, String full_name, String password, String email, String phone, int satatus) {
        this.account_id = account_id;
        this.full_name = full_name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.satatus = satatus;
    }

    public Account() {
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(account_id, account.account_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSatatus() {
        return satatus;
    }

    public void setSatatus(int satatus) {
        this.satatus = satatus;
    }

}
