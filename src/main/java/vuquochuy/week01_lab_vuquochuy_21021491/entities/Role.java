package vuquochuy.week01_lab_vuquochuy_21021491.entities;

public class Role {
    private long account_id;
    private String role_name;
    private String description;
    private int status;

    public Role(long account_id, String role_name, String description, int status) {
        this.account_id = account_id;
        this.role_name = role_name;
        this.description = description;
        this.status = status;
    }

    public Role() {
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Role{" +
                "account_id=" + account_id +
                ", role_name='" + role_name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
