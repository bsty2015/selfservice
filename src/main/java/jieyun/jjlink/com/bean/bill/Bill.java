package jieyun.jjlink.com.bean.bill;

import java.util.List;

/**
 * Created by lu on 2015/6/23.
 */
public class Bill {
    private String reply_msg;
    private List<Rows> rows;

    @Override
    public String toString() {
        return "Bill{" +
                "reply_msg='" + reply_msg + '\'' +
                ", rows=" + rows +
                '}';
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
