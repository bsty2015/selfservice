package jieyun.jjlink.com.bean.credentials;

import java.util.List;

/**
 * Created by lu on 2015/6/25.
 */
public class Credentials {
    private Integer reply_code;
    private String reply_msg;
    private List<Rows> rows;

    @Override
    public String toString() {
        return "Credentials{" +
                "reply_code=" + reply_code +
                ", reply_msg='" + reply_msg + '\'' +
                ", rows=" + rows +
                '}';
    }

    public Integer getReply_code() {
        return reply_code;
    }

    public void setReply_code(Integer reply_code) {
        this.reply_code = reply_code;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }

    public List<Rows> getRows() {
        return rows;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }
}
