package jieyun.jjlink.com.beforedemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import jieyun.jjlink.com.bean.recharge.Recharge;
import jieyun.jjlink.com.bean.recharge.Rows;
import jieyun.jjlink.com.jieyunclass.JieyunRow;
import jieyun.jjlink.com.jieyunclass.JieyunTextView;
import jieyun.jjlink.com.utils.FormatUtils;


public class RechargeInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/recharge/getlist";
    ScrollView scrollView;
    LinearLayout linearLayout;
    TableLayout tab;
    TableRow row;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_recharge_info);
        //       final TextView textView = (TextView) findViewById(R.id.id_recharge);
//        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        HandlerThread handlerThread = new HandlerThread("Recharge");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String info = StreamTool.doPost(REQUEST_URL, null);
                    System.out.println("充值信息：" + info);
                    final StringBuffer sb = new StringBuffer();
                    Gson gson = new GsonBuilder().serializeNulls().create();
                    Recharge recharge = gson.fromJson(info, Recharge.class);
                    List<Rows> rows = recharge.getRows();
                    tab = new TableLayout(getApplicationContext());
                    tab.setStretchAllColumns(true);
                    scrollView = new ScrollView(getApplicationContext());
                    scrollView.addView(tab);
                    for (Rows r : rows) {

                        createTabLayout(getApplicationContext(), tab, r);

                      /*  sb.append("账号：").append(r.getAccount_no()).append("\n")
                                .append("充值金额：").append(r.getAmount() / 100.00d).append("\n")
                                .append("充值时间：").append(FormatUtils.parseTime(r.getOper_time())).append("\n")
                                .append("备注：    ").append(r.getRemark()).append("\n\n");*/
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setContentView(scrollView);
//                            textView.setText(sb.toString());
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

    /**
     * 动态生成表格显示充值信息
     *
     * @param context
     * @param tab
     * @param r
     */
    private void createTabLayout(Context context, TableLayout tab, Rows r) {
        TableRow row_accountno = new JieyunRow(context);
        TableRow row_amount = new JieyunRow(context);
        TableRow row_opertime = new JieyunRow(context);
        TableRow row_mark = new JieyunRow(context);
        TableRow row_blank = new JieyunRow(context);
        TextView row_account_title = new JieyunTextView(context);
        TextView row_account_text = new JieyunTextView(context);
        TextView row_amount_title = new JieyunTextView(context);
        TextView row_amount_text = new JieyunTextView(context);
        TextView row_opertime_title = new JieyunTextView(context);
        TextView row_opertime_text = new JieyunTextView(context);
        TextView row_mark_title = new JieyunTextView(context);
        TextView row_mark_text = new JieyunTextView(context);
        TextView row_blank_title = new JieyunTextView(context);
        TextView row_blank_text = new JieyunTextView(context);
        row_account_title.setText("账号");
        row_account_text.setText(r.getAccount_no());
        row_amount_title.setText("充值金额");
        row_amount_text.setText(String.valueOf(r.getAmount() / 1000.00d));
        row_opertime_title.setText("充值时间");
        row_opertime_text.setText(FormatUtils.parseTime(r.getOper_time()));
        row_mark_title.setText("备注");
        row_mark_text.setText(r.getRemark());
        row_blank_title.setText("");
        row_blank_title.setBackgroundColor(255);
        row_blank_text.setBackgroundColor(255);
        row_blank_text.setText("");
        row_accountno.addView(row_account_title);
        row_accountno.addView(row_account_text);
        row_amount.addView(row_amount_title);
        row_amount.addView(row_amount_text);
        row_opertime.addView(row_opertime_title);
        row_opertime.addView(row_opertime_text);
        row_mark.addView(row_mark_title);
        row_mark.addView(row_mark_text);
        row_blank.addView(row_blank_title);
        row_blank.addView(row_blank_text);
        tab.addView(row_accountno);
        tab.addView(row_amount);
        tab.addView(row_opertime);
        tab.addView(row_mark);
        tab.addView(row_blank);
    }

    class JieyunTab extends TableLayout {
        public JieyunTab(Context context) {
            super(context);
        }

        public JieyunTab(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

    }

}
