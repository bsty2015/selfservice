package jieyun.jjlink.com.bean.userinfo;

import java.util.List;

/**
 * Created by lu on 2015/6/25.
 */
public class UserInfo {
    private Integer reply_code;
    private String reply_msg;
    private List<Results> results;


    @Override
    public String toString() {
        return "UserInfo{" +
                "reply_code=" + reply_code +
                ", reply_msg='" + reply_msg + '\'' +
                ", results=" + results +
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

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
