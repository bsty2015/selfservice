package jieyun.jjlink.com.bean.online;

/**
 * Created by lu on 2015/6/25.
 */
public class Rows {
    private Long id;
    private Long user_ipv4;
    private String username;
    private String fullname;
    private String area_name;
    private Integer area_type;
    private String service_name;
    private int acctstarttime;

    public Long getSrc_ip() {
        return src_ip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSrc_ip(Long src_ip) {
        this.src_ip = src_ip;
    }

    private Long src_ip;

    @Override
    public String toString() {
        return "Rows{" +
                "id=" + id +
                ", user_ipv4=" + user_ipv4 +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", area_name='" + area_name + '\'' +
                ", area_type=" + area_type +
                ", service_name='" + service_name + '\'' +
                ", acctstarttime=" + acctstarttime +
                ", src_ip=" + src_ip +
                '}';
    }

    public int getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(int acctstarttime) {
        this.acctstarttime = acctstarttime;
    }


    public Long getUser_ipv4() {
        return user_ipv4;
    }

    public void setUser_ipv4(Long user_ipv4) {
        this.user_ipv4 = user_ipv4;
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

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public Integer getArea_type() {
        return area_type;
    }

    public void setArea_type(Integer area_type) {
        this.area_type = area_type;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }
}
