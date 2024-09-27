package vuquochuy.week01_lab_vuquochuy_21021491.entities;

import java.sql.Timestamp;

public class Log {
    private Long log_id;
    private String account_id;
    private Timestamp login_date;
    private Timestamp logout_date;
    private String description;

    public Log() {
    }

    public Log(String account_id, Timestamp login_date, Timestamp logout_date, String description) {
        this.account_id = account_id;
        this.login_date = login_date;
        this.logout_date = logout_date;
        this.description = description;
    }

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public Timestamp getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Timestamp login_date) {
        this.login_date = login_date;
    }

    public Timestamp getLogout_date() {
        return logout_date;
    }

    public void setLogout_date(Timestamp logout_date) {
        this.logout_date = logout_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
