package jieyun.jjlink.com.bean.bill;

/**
 * Created by lu on 2015/6/26.
 */
public class Rows {
    private String account_no;
    //初期余额
    private Integer beginning_banlance;
    //本期充值金额
    private Integer recharge_amount;
    //本期使用金额
    private Integer costs_amount;
    //期末余额
    private Integer ending_banlance;
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
                ", beginning_banlance=" + beginning_banlance +
                ", recharge_amount=" + recharge_amount +
                ", costs_amount=" + costs_amount +
                ", ending_banlance=" + ending_banlance +
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

    public Integer getBeginning_banlance() {
        return beginning_banlance;
    }

    public void setBeginning_banlance(Integer beginning_banlance) {
        this.beginning_banlance = beginning_banlance;
    }

    public Integer getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(Integer recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public Integer getCosts_amount() {
        return costs_amount;
    }

    public void setCosts_amount(Integer costs_amount) {
        this.costs_amount = costs_amount;
    }

    public Integer getEnding_banlance() {
        return ending_banlance;
    }

    public void setEnding_banlance(Integer ending_banlance) {
        this.ending_banlance = ending_banlance;
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
