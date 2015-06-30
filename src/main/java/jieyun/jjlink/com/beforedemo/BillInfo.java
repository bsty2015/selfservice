package jieyun.jjlink.com.beforedemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import jieyun.jjlink.com.bean.bill.Bill;
import jieyun.jjlink.com.bean.bill.Rows;
import jieyun.jjlink.com.utils.ToolUtils;


public class BillInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/bill/getlist";
    RequestQueue mQueue;
    StringRequest stringRequest;
    Gson gson;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);
        final TextView textView = (TextView) findViewById(R.id.id_bill);
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
                    if (rows != null && rows.size() > 0) {
                        for (Rows r : rows) {
                            sb.append("账号：    ").append(r.getAccount_no()).append("\n")
                                    .append("初期余额：  ").append(r.getBeginning_banlance()/100.00d).append("\n")
                                    .append("本期充值金额：").append(r.getRecharge_amount()/100.00d).append("\n")
                                    .append("本期使用金额：").append(r.getCosts_amount()/100.00d).append("\n")
                                    .append("期末余额：  ").append(r.getEnding_banlance()/100.00d).append("\n")
                                    .append("创建时间：  ").append(ToolUtils.parseTime(r.getCreatetime())).append("\n")
                                    .append("开始日期：  ").append(ToolUtils.parseTime(r.getStartdate())).append("\n")
                                    .append("结束日期：  ").append(ToolUtils.parseTime(r.getEnddate())).append("\n\n");
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(sb.toString());
                        }
                    });
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
}
