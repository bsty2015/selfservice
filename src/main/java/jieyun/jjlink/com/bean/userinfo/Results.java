package jieyun.jjlink.com.bean.userinfo;

/**
 * Created by lu on 2015/6/25.
 */
public class Results {
    private String username;
    private String fullname;
    private String alias;
    private String certification_no;
    private String mobile;
    private String service_name;
    private String next_service_name;
    private String remark;
    private Integer createtime;
    private Integer expirydate;
    private String status;
    private Double account_balance;
    private Integer pay_type;

    @Override
    public String toString() {
        return "Results{" +
                "username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", alias='" + alias + '\'' +
                ", certification_no='" + certification_no + '\'' +
                ", mobile='" + mobile + '\'' +
                ", service_name='" + service_name + '\'' +
                ", next_service_name='" + next_service_name + '\'' +
                ", remark='" + remark + '\'' +
                ", createtime=" + createtime +
                ", expirydate=" + expirydate +
                ", status=" + status +
                ", balance=" + account_balance +
                ", pay_type=" + pay_type +
                '}';
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCertification_no() {
        return certification_no;
    }

    public void setCertification_no(String certification_no) {
        this.certification_no = certification_no;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getNext_service_name() {
        return next_service_name;
    }

    public void setNext_service_name(String next_service_name) {
        this.next_service_name = next_service_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Integer getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Integer expirydate) {
        this.expirydate = expirydate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getBalance() {
        return account_balance;
    }

    public void setBalance(Double balance) {
        this.account_balance = balance;
    }

    public Integer getPay_type() {
        return pay_type;
    }

    public void setPay_type(Integer pay_type) {
        this.pay_type = pay_type;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
