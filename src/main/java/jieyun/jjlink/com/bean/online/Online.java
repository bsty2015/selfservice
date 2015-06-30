package jieyun.jjlink.com.bean.online;

import java.util.List;

/**
 * Created by lu on 2015/6/25.
 */
public class Online {
    private Integer reply_code;
    private String request_uri;
    private Long request_time;
    private String reply_msg;
    private List<Rows> rows;

    @Override
    public String toString() {
        return "Online{" +
                "reply_code=" + reply_code +
                ", request_uri='" + request_uri + '\'' +
                ", request_time=" + request_time +
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

    public String getRequest_uri() {
        return request_uri;
    }

    public void setRequest_uri(String request_uri) {
        this.request_uri = request_uri;
    }

    public Long getRequest_time() {
        return request_time;
    }

    public void setRequest_time(Long request_time) {
        this.request_time = request_time;
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
