package jieyun.jjlink.com.beforedemo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import jieyun.jjlink.com.bean.bill.Bill;
import jieyun.jjlink.com.bean.bill.Rows;
import jieyun.jjlink.com.jieyunclass.JieyunRow;
import jieyun.jjlink.com.jieyunclass.JieyunTextView;
import jieyun.jjlink.com.utils.FormatUtils;


public class BillInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/bill/getlist";
    RequestQueue mQueue;
    StringRequest stringRequest;
    TableLayout tableLayout;
    ScrollView scrollView;
    Gson gson;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_bill_info);
        //final JieyunTextView JieyunTextView = (JieyunTextView) findViewById(R.id.id_bill);
        HandlerThread handlerThread = new HandlerThread("bill");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = StreamTool.doPost(REQUEST_URL, null);
                    gson = new GsonBuilder().serializeNulls().create();
                    Bill bill = gson.fromJson(str, Bill.class);
                    List<Rows> rows = bill.getRows();
                    final StringBuffer sb = new StringBuffer();
                    tableLayout = new TableLayout(getApplicationContext());
                    tableLayout.setStretchAllColumns(true);
                    scrollView = new ScrollView(getApplicationContext());
                    scrollView.addView(tableLayout);
                    if (rows != null && rows.size() > 0) {
                        for (Rows r : rows) {
                            createBillList(tableLayout, getApplicationContext(), r);
                            /*sb.append("账号：    ").append(r.getAccount_no()).append("\n")
                                    .append("初期余额：   ").append(r.getBeginning_balance() / 1000.00d).append("\n")
                                    .append("本期充值金额：").append(r.getRecharge_amount() / 1000.00d).append("\n")
                                    .append("本期使用金额：").append(r.getCosts_amount() / 1000.00d).append("\n")
                                    .append("期末余额：   ").append(r.getEnding_balance() / 1000.00d).append("\n")
                                    .append("创建时间：   ").append(FormatUtils.parseTime(r.getCreatetime())).append("\n")
                                    .append("开始日期：   ").append(FormatUtils.parseTime(r.getStartdate())).append("\n")
                                    .append("结束日期：   ").append(FormatUtils.parseTime(r.getEnddate())).append("\n\n");
  */
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // JieyunTextView.setText(sb.toString());
                                setContentView(scrollView);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "无账单信息", Toast.LENGTH_SHORT).show();
                                BillInfo.this.finish();
                                return;
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill_info, menu);
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

    private void createBillList(TableLayout tab, Context context, Rows r) {
        //TableLayout tab = new TableLayout(getApplicationContext());
        JieyunRow row_account = new JieyunRow(context);
        JieyunRow row_begin_balance = new JieyunRow(context);
        JieyunRow row_recharge_amount = new JieyunRow(context);
        JieyunRow row_cost_amount = new JieyunRow(context);
        JieyunRow row_ending_balance = new JieyunRow(context);
        JieyunRow row_create_time = new JieyunRow(context);
        JieyunRow row_start_time = new JieyunRow(context);
        JieyunRow row_end_date = new JieyunRow(context);
        JieyunTextView row_account_title = new JieyunTextView(context);
        JieyunTextView row_account_text = new JieyunTextView(context);
        JieyunTextView row_beginbalance_title = new JieyunTextView(context);
        JieyunTextView row_beginbalance_text = new JieyunTextView(context);
        JieyunTextView row_rechargeamount_title = new JieyunTextView(context);
        JieyunTextView row_rechargeamount_text = new JieyunTextView(context);
        JieyunTextView row_costamount_title = new JieyunTextView(context);
        JieyunTextView row_costamount_text = new JieyunTextView(context);
        JieyunTextView row_endbalance_title = new JieyunTextView(context);
        JieyunTextView row_endbalance_text = new JieyunTextView(context);
        JieyunTextView row_createtime_title = new JieyunTextView(context);
        JieyunTextView row_createtime_text = new JieyunTextView(context);
        JieyunTextView row_startdate_title = new JieyunTextView(context);
        JieyunTextView row_startdate_text = new JieyunTextView(context);
        JieyunTextView row_enddate_title = new JieyunTextView(context);
        JieyunTextView row_enddate_text = new JieyunTextView(context);
        row_account_title.setText("账号");
        row_account_text.setText(r.getAccount_no());
        row_beginbalance_title.setText("初期余额");
        row_beginbalance_text.setText(String.valueOf(r.getBeginning_balance() / 1000.00d));
        row_rechargeamount_title.setText("本期充值金额");
        row_rechargeamount_text.setText(String.valueOf(r.getRecharge_amount() / 1000.00d));
        row_costamount_title.setText("本期使用金额");
        row_costamount_text.setText(String.valueOf(r.getCosts_amount() / 1000.00d));
        row_endbalance_title.setText("期末余额");
        row_endbalance_text.setText(String.valueOf(r.getEnding_balance() / 1000.00d));
        row_createtime_title.setText("创建时间");
        row_createtime_text.setText(FormatUtils.parseTime(r.getCreatetime()));
        row_startdate_title.setText("开始时间");
        row_startdate_text.setText(FormatUtils.parseTime(r.getStartdate()));
        row_enddate_title.setText("结束时间");
        row_enddate_text.setText(FormatUtils.parseTime(r.getEnddate()));
        row_account.addView(row_account_title);
        row_account.addView(row_account_text);
        row_begin_balance.addView(row_beginbalance_title);
        row_begin_balance.addView(row_beginbalance_text);
        row_recharge_amount.addView(row_rechargeamount_title);
        row_recharge_amount.addView(row_rechargeamount_text);
        row_cost_amount.addView(row_costamount_title);
        row_cost_amount.addView(row_costamount_text);
        row_ending_balance.addView(row_endbalance_title);
        row_ending_balance.addView(row_endbalance_text);
        row_create_time.addView(row_createtime_title);
        row_create_time.addView(row_createtime_text);
        row_start_time.addView(row_startdate_title);
        row_start_time.addView(row_startdate_text);
        row_end_date.addView(row_enddate_title);
        row_end_date.addView(row_enddate_text);
        tab.addView(row_account);
        tab.addView(row_begin_balance);
        tab.addView(row_recharge_amount);
        tab.addView(row_cost_amount);
        tab.addView(row_ending_balance);
        tab.addView(row_create_time);
        tab.addView(row_start_time);
        tab.addView(row_end_date);

    }
}
