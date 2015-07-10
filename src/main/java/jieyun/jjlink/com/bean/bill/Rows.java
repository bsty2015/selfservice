package jieyun.jjlink.com.bean.bill;

/**
 * Created by lu on 2015/6/26.
 */
public class Rows {
    private String account_no;
    //初期余额
    private Long beginning_balance;
    //本期充值金额
    private Long recharge_amount;
    //本期使用金额
    private Long costs_amount;
    //期末余额
    private Long ending_balance;
    //创建时间
    private Integer createtime;
    //开始日期
    private Integer startdate;
    //结束日期
    private Integer enddate;

    @Override
    public String toString() {
        return "Rows{" +
                "account_no='" + account_no + '\'' +
                ", beginning_balance=" + beginning_balance +
                ", recharge_amount=" + recharge_amount +
                ", costs_amount=" + costs_amount +
                ", ending_balance=" + ending_balance +
                ", createtime=" + createtime +
                ", startdate=" + startdate +
                ", enddate=" + enddate +
                '}';
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public Long getBeginning_balance() {
        return beginning_balance;
    }

    public void setBeginning_balance(Long beginning_balance) {
        this.beginning_balance = beginning_balance;
    }

    public Long getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(Long recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public Long getCosts_amount() {
        return costs_amount;
    }

    public void setCosts_amount(Long costs_amount) {
        this.costs_amount = costs_amount;
    }

    public Long getEnding_balance() {
        return ending_balance;
    }

    public void setEnding_balance(Long ending_balance) {
        this.ending_balance = ending_balance;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Integer getStartdate() {
        return startdate;
    }

    public void setStartdate(Integer startdate) {
        this.startdate = startdate;
    }

    public Integer getEnddate() {
        return enddate;
    }

    public void setEnddate(Integer enddate) {
        this.enddate = enddate;
    }
}
