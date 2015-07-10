package jieyun.jjlink.com.beforedemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;

import java.util.List;

import jieyun.jjlink.com.bean.userinfo.Results;
import jieyun.jjlink.com.bean.userinfo.UserInfo;
import jieyun.jjlink.com.utils.FormatUtils;


public class UserInfoActivity extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/userinfo/getinfo";
    private static final int FULL_NAME = R.string.fullname;
    private static final int MOBILE = R.string.mobile;
    private static final int BIND_MAC = R.string.bind_mac;
    private static final int SERVICE_NAME = R.string.service_name;
    private static final int ACCOUNT = R.string.account;
    TextView mUsername;
    TextView mFullname;
    TextView mAlias;
    TextView mCardNo;
    TextView mMobile;
    TextView mStatus;
    TextView mCreateTime;
    TextView mExpirydate;
    TextView mBalance;
    TextView mServiceName;
    TextView mNextServiceName;
    String username;
    String fullname;
    String alias;
    String card_no;
    String mobile;
    String status;
    String create_time;
    String expirydate;
    Double balance;
    String service_name;
    String next_service_name;
    RequestQueue mQueue;
    StringRequest request;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);
        //final TextView textView = (TextView) findViewById(R.id.id_user);
        mUsername = (TextView) findViewById(R.id.id_username);
        mFullname = (TextView) findViewById(R.id.id_fullname);
        mAlias = (TextView) findViewById(R.id.id_alias);
        mCardNo = (TextView) findViewById(R.id.id_card_no);
        mMobile = (TextView) findViewById(R.id.id_mobile);
        mStatus = (TextView) findViewById(R.id.id_status);
        mCreateTime = (TextView) findViewById(R.id.id_create_time);
        mExpirydate = (TextView) findViewById(R.id.id_expirydate);
        mBalance = (TextView) findViewById(R.id.id_balance);
        mServiceName = (TextView) findViewById(R.id.id_service_name);
        mNextServiceName = (TextView) findViewById(R.id.id_next_service_name);
        HandlerThread handlerThread = new HandlerThread("userInfo");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    gson = new GsonBuilder().serializeNulls().create();
                    String info = StreamTool.doPost(REQUEST_URL, null);
                    System.out.println(info);
                    UserInfo userInfo = gson.fromJson(info, UserInfo.class);
                    List<Results> results = userInfo.getResults();
                    //final StringBuffer sb = new StringBuffer();
                    for (Results r : results) {
                        username = r.getUsername();
                        fullname = r.getFullname();
                        alias = r.getAlias() == null ? "无" : r.getAlias();
                        card_no = r.getCertification_no();
                        mobile = r.getMobile() == null ? "无" : r.getMobile();
                        status = r.getStatus();
                        create_time = FormatUtils.parseTime(r.getCreatetime(), "yyyy-HH-dd");
                        if (r.getExpirydate() == null) {
                            /*未查询到有效期*/
                            expirydate = "无";
                        } else {
                            expirydate = FormatUtils.parseTime(r.getExpirydate(), "yyyy-MM-dd");
                        }
                        balance = r.getBalance() / 1000.00d;
                        service_name = r.getService_name();
                        next_service_name = r.getNext_service_name() == null ? "未知" : r.getNext_service_name();

                       /* sb.append("用户名：   ").append(username).append("\n")
                                .append("姓名：     ").append(fullname).append("\n")
                                .append("别名：     ").append(alias).append("\n")
                                .append("证件号：   ").append(card_no).append("\n")
                                .append("电话：     ").append(mobile).append("\n")
                                .append("账号状态：  ").append(status).append("\n")
                                .append("创建时间：  ").append(create_time).append("\n")
                                .append("有效期：    ").append(expirydate).append("\n")
                                .append("账户余额：  ").append(balance).append("\n")
                                .append("本期服务名：").append(service_name).append("\n")
                                .append("下期服务名：").append(next_service_name).append("\n");*/

                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mUsername.setText(username);
                            mFullname.setText(fullname);
                            mAlias.setText(alias);
                            mCardNo.setText(card_no);
                            mMobile.setText(mobile);
                            mStatus.setText(status);
                            mCreateTime.setText(create_time);
                            mExpirydate.setText(expirydate);
                            mBalance.setText(String.valueOf(balance));
                            mServiceName.setText(service_name);
                            mNextServiceName.setText(next_service_name);
                            //textView.setText(sb.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public String getUserInfo(String url, List<NameValuePair> params) {
       /* try {
            String result = StreamTool.doPost(url, params);
            Gson gson = new Gson();

            JSONObject jsonObject = JSONObject.fromObject(result);
            JSONObject results_msg = jsonObject.getJSONObject("userInfoResults");
            String username = results_msg.getString("username");
            String fullname = results_msg.getString("fullname");
            String service_name = results_msg.getString("service_name");
            String next_service_name = results_msg.getString("next_service_name");
            net.sf.json.JSONObject account = jsonObject.getJSONObject("account");
            double balance = account.getLong("balance") / 1000.00d;
            int pay_type = account.getInt("pay_type");
            int account_type = account.getInt("account_type");
            String payType = "";
            String accountType = "";
            switch (pay_type) {
                case 1:
                    payType = "预付费";
                    break;
                case 2:
                    payType = "后付费";
                    break;
            }
            switch (account_type) {
                case 1:
                    accountType = "支出账号";
                    break;
                case 2:
                    accountType = "收入账号";
                    break;
            }
            StringBuffer sb = new StringBuffer();
            sb.append("用户名：").append(username)
                    .append("\n姓名:").append(fullname)
                    .append("\n当前服务名称：").append(service_name)
                    .append("\n下月服务名称：").append(next_service_name)
                    .append("\n账户余额：").append(balance)
                    .append("\n付费方式：").append(payType)
                    .append("\n付费类型：").append(account_type);

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取用户信息异常";
        }*/
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
