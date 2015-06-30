package jieyun.jjlink.com.bean.detail;

/**
 * Created by lu on 2015/6/26.
 */
public class Rows {
    private String username;
    private String fullname;
    private String service_name;
    private Integer acctstarttime;
    private Integer amount_ipv4;

    @Override
    public String toString() {
        return "Rows{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", service_name='" + service_name + '\'' +
                ", acctstarttime=" + acctstarttime +
                ", amount_ipv4=" + amount_ipv4 +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public Integer getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(Integer acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public Integer getAmount_ipv4() {
        return amount_ipv4;
    }

    public void setAmount_ipv4(Integer amount_ipv4) {
        this.amount_ipv4 = amount_ipv4;
    }
}
