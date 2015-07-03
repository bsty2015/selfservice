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
import jieyun.jjlink.com.utils.ToolUtils;


public class RechargeInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/recharge/getlist";
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
                    scrollView=new ScrollView(getApplicationContext());
                    scrollView.addView(tab);
                    for (Rows r : rows) {

                        createTabLayout(getApplicationContext(), tab, r);

                      /*  sb.append("账号：").append(r.getAccount_no()).append("\n")
                                .append("充值金额：").append(r.getAmount() / 100.00d).append("\n")
                                .append("充值时间：").append(ToolUtils.parseTime(r.getOper_time())).append("\n")
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
        TableRow row_1 = new JieyunRow(context);
        TableRow row_2 = new JieyunRow(context);
        TableRow row_3 = new JieyunRow(context);
        TableRow row_4 = new JieyunRow(context);
        TableRow row_blank = new JieyunRow(context);
        TextView col_1_1 = new JieyunTextView(context);
        TextView col_1_2 = new JieyunTextView(context);
        TextView col_2_1 = new JieyunTextView(context);
        TextView col_2_2 = new JieyunTextView(context);
        TextView col_3_1 = new JieyunTextView(context);
        TextView col_3_2 = new JieyunTextView(context);
        TextView col_4_1 = new JieyunTextView(context);
        TextView col_4_2 = new JieyunTextView(context);
        TextView col_blank_1= new JieyunTextView(context);
        TextView col_blank_2= new JieyunTextView(context);
        col_1_1.setText("账号");
        col_1_2.setText(r.getAccount_no());
        col_2_1.setText("充值金额");
        col_2_2.setText(String.valueOf(r.getAmount() / 100.00d));
        col_3_1.setText("充值时间");
        col_3_2.setText(ToolUtils.parseTime(r.getOper_time()));
        col_4_1.setText("备注");
        col_4_2.setText(r.getRemark());
        col_blank_1.setText("");
        col_blank_1.setBackgroundColor(255);
        col_blank_2.setText("");
        row_1.addView(col_1_1);
        row_1.addView(col_1_2);
        row_2.addView(col_2_1);
        row_2.addView(col_2_2);
        row_3.addView(col_3_1);
        row_3.addView(col_3_2);
        row_4.addView(col_4_1);
        row_4.addView(col_4_2);
        row_blank.addView(col_blank_1);
        row_blank.addView(col_blank_2);
        tab.addView(row_1);
        tab.addView(row_2);
        tab.addView(row_3);
        tab.addView(row_4);
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
