package jieyun.jjlink.com.beforedemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import jieyun.jjlink.com.bean.recharge.Recharge;
import jieyun.jjlink.com.bean.recharge.Rows;
import jieyun.jjlink.com.utils.ToolUtils;


public class RechargeInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/recharge/getlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_info);
        final TextView textView = (TextView) findViewById(R.id.id_recharge);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        HandlerThread handlerThread = new HandlerThread("Recharge");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String info = StreamTool.doPost(REQUEST_URL, null);
                    System.out.println("充值信息："+info);
                    final StringBuffer sb = new StringBuffer();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Recharge recharge = gson.fromJson(info, Recharge.class);
                    List<Rows> rows = recharge.getRows();
                    for (Rows r : rows) {
                        sb.append("账号：").append(r.getAccount_no()).append("\n")
                                .append("充值金额：").append(r.getAmount() / 100.00d).append("\n")
                                .append("充值时间：").append(ToolUtils.parseTime(r.getOper_time())).append("\n")
                                .append("备注：    ").append(r.getRemark()).append("\n\n");
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
        getMenuInflater().inflate(R.menu.menu_recharge_info, menu);
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
