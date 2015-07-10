package jieyun.jjlink.com.beforedemo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jieyun.jjlink.com.bean.detail.Detail;
import jieyun.jjlink.com.bean.detail.Rows;
import jieyun.jjlink.com.jieyunclass.JieyunRow;
import jieyun.jjlink.com.jieyunclass.JieyunTextView;
import jieyun.jjlink.com.utils.FormatUtils;

public class DetailInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/detail/getlist";
    Spinner mSpinner;
    private TableLayout tab;
    private ScrollView scrollView;
    LinearLayout linearLayout;
    TextView textView;

    /**
     * 返回当前3个月份的值
     *
     * @return
     */
    private String[] getMonth() {
        Integer[] months = getMonth_num();
        String[] month_str = new String[months.length];
        for (int i = 0; i < months.length; i++) {
            month_str[i] = months[i] + "月";
        }
        return month_str;
    }

    private Integer[] getMonth_num() {
        Calendar cal = Calendar.getInstance();
        List<Integer> months = new ArrayList<>();
        int month_curr = cal.get(Calendar.MONTH) + 1;
        months.add(month_curr);
        months.add(month_curr - 1);
        months.add(month_curr - 2);
        Integer[] m = months.toArray(new Integer[months.size()]);
        return m;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.spinner);
        //textView = (TextView) findViewById(R.id.id_details);
        //mSpinner = (Spinner) findViewById(R.id.id_month_spinner);
        //scrollView= (ScrollView) findViewById(R.id.id_scroll);
        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mSpinner = new Spinner(getApplicationContext());
        scrollView = new ScrollView(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getMonth());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        mSpinner.setVisibility(View.VISIBLE);
        linearLayout.addView(mSpinner);
        tab = new TableLayout(getApplicationContext());
        tab.setStretchAllColumns(true);
        scrollView.addView(tab);
        linearLayout.addView(scrollView);
        //final TextView textView = (TextView) findViewById(R.id.id_details);
        setContentView(linearLayout);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_info, menu);
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

    class SpinnerSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tab.removeAllViews();
                }
            });
            HandlerThread handlerThread = new HandlerThread("detail");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            //textView = (TextView) findViewById(R.id.id_details);
            // textView.setText("");
            Integer[] month_num = getMonth_num();
            String param = String.valueOf(month_num[position]);
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("month", param));
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        String request_result = StreamTool.doPost(REQUEST_URL, params);
                        Gson gson = new GsonBuilder().serializeNulls().create();
                        Detail detail = gson.fromJson(request_result, Detail.class);
                        final StringBuffer sb = new StringBuffer();
                        final List<Rows> rows = detail.getRows();
                        if (rows != null && rows.size() > 0) {

                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    for (Rows r : rows) {
                                        createDetailList(getApplicationContext(), tab, r);
                       /*         sb.append("用户名：").append(r.getUsername()).append("\n")
                                        .append("姓名：    ").append(r.getFullname()).append("\n")
                                        .append("服务名：  ").append(r.getService_name()).append("\n")
                                        .append("上线时间：").append(FormatUtils.parseTime(r.getAcctstarttime())).append("\n")
                                            .append("费用：    ").append(r.getAmount_ipv4()/1000.00d).append("\n\n");
                         */
                                    }
                                    scrollView.postInvalidate();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "未查到数据", Toast.LENGTH_SHORT).show();
                                    tab.removeAllViews();
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
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void createDetailList(Context c, TableLayout t, Rows r) {
        JieyunRow row_username = new JieyunRow(c);
        JieyunRow row_fullname = new JieyunRow(c);
        JieyunRow row_servicename = new JieyunRow(c);
        JieyunRow row_acctstarttime = new JieyunRow(c);
        JieyunRow row_amount = new JieyunRow(c);
        JieyunTextView col_username_title = new JieyunTextView(c);
        JieyunTextView col_username_text = new JieyunTextView(c);
        JieyunTextView col_fullname_title = new JieyunTextView(c);
        JieyunTextView col_fullname_text = new JieyunTextView(c);
        JieyunTextView col_servicename_title = new JieyunTextView(c);
        JieyunTextView col_servicename_text = new JieyunTextView(c);
        JieyunTextView col_acctstarttime_title = new JieyunTextView(c);
        JieyunTextView col_acctstarttime_text = new JieyunTextView(c);
        JieyunTextView col_amount_title = new JieyunTextView(c);
        JieyunTextView col_amount_text = new JieyunTextView(c);
        col_username_title.setText("用户名");
        col_username_text.setText(r.getUsername());
        col_fullname_title.setText("姓名");
        col_fullname_text.setText(r.getFullname());
        col_servicename_title.setText("服务名");
        col_servicename_text.setText(r.getService_name());
        col_acctstarttime_title.setText("上线时间");
        col_acctstarttime_text.setText(FormatUtils.parseTime(r.getAcctstarttime()));
        col_amount_title.setText("费用");
        col_amount_text.setText(String.valueOf(r.getAmount_ipv4() / 1000.00d));
        row_username.addView(col_username_title);
        row_username.addView(col_username_text);
        row_fullname.addView(col_fullname_title);
        row_fullname.addView(col_fullname_text);
        row_servicename.addView(col_servicename_title);
        row_servicename.addView(col_servicename_text);
        row_acctstarttime.addView(col_acctstarttime_title);
        row_acctstarttime.addView(col_acctstarttime_text);
        row_amount.addView(col_amount_title);
        row_amount.addView(col_amount_text);
        t.addView(row_username);
        t.addView(row_fullname);
        t.addView(row_servicename);
        t.addView(row_acctstarttime);
        t.addView(row_amount);
    }
}
