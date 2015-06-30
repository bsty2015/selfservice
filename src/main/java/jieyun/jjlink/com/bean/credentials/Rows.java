package jieyun.jjlink.com.bean.credentials;

/**
 * Created by lu on 2015/6/25.
 */
public class Rows {
    private String username;
    private String area_name;
    private String area_type;
    private int datetime;
    private String reply_msg;

    @Override
    public String toString() {
        return "Rows{" +
                "username='" + username + '\'' +
                ", area_name='" + area_name + '\'' +
                ", area_type='" + area_type + '\'' +
                ", datetime=" + datetime +
                ", reply_msg='" + reply_msg + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getArea_type() {
        return area_type;
    }

    public void setArea_type(String area_type) {
        this.area_type = area_type;
    }

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }
}
