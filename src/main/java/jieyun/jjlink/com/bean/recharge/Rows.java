package jieyun.jjlink.com.bean.recharge;

/**
 * Created by lu on 2015/6/26.
 */
public class Rows {
    private String remark;
    private Integer oper_time;
    private Long amount;
    private String account_no;


    @Override
    public String toString() {
        return "Rows{" +
                "remark='" + remark + '\'' +
                ", oper_time=" + oper_time +
                ", amount=" + amount +
                ", account_no='" + account_no + '\'' +
                '}';
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getOper_time() {
        return oper_time;
    }

    public void setOper_time(Integer oper_time) {
        this.oper_time = oper_time;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
