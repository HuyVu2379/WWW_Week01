package vuquochuy.week01_lab_vuquochuy_21021491.entities;

import java.sql.Date;

public class Log {
    private Long log_id;
    private String account_id;
    private Date login_date;
    private Date logout_date;
    private String description;

    public Log() {
    }

    public Log(String account_id, Date login_date, Date logout_date, String description) {
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

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }

    public Date getLogout_date() {
        return logout_date;
    }

    public void setLogout_date(Date logout_date) {
        this.logout_date = logout_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
