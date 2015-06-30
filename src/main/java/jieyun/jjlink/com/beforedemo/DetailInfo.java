package jieyun.jjlink.com.beforedemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jieyun.jjlink.com.bean.detail.Detail;
import jieyun.jjlink.com.bean.detail.Rows;
import jieyun.jjlink.com.utils.ToolUtils;

public class DetailInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/detail/getlist";
    Spinner mSpinner;
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
        setContentView(R.layout.spinner);
        textView = (TextView) findViewById(R.id.id_details);
        mSpinner = (Spinner) findViewById(R.id.id_month_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getMonth());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        mSpinner.setVisibility(View.VISIBLE);
        final TextView textView = (TextView) findViewById(R.id.id_details);

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

            HandlerThread handlerThread = new HandlerThread("detail");
            handlerThread.start();
            Handler handler = new Handler(handlerThread.getLooper());
            textView = (TextView) findViewById(R.id.id_details);
            textView.setText("");
            Integer[] month_num = getMonth_num();
            String param = String.valueOf(month_num[position]);
            final List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("month", param));
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        String request_result = StreamTool.doPost(REQUEST_URL, params);
                        Gson gson=new GsonBuilder().serializeNulls().create();
                        Detail detail=gson.fromJson(request_result,Detail.class);
                        final StringBuffer sb=new StringBuffer();
                        List<Rows> rows=detail.getRows();
                        if(rows!=null&&rows.size()>0){
                            for(Rows r:rows){
                                sb.append("用户名：").append(r.getUsername()).append("\n")
                                        .append("姓名：    ").append(r.getFullname()).append("\n")
                                        .append("服务名：  ").append(r.getService_name()).append("\n")
                                        .append("上线时间：").append(ToolUtils.parseTime(r.getAcctstarttime())).append("\n")
                                        .append("费用：    ").append(r.getAmount_ipv4()/100.00d).append("\n\n");
                            }
                        }else{
                            sb.append("未查到数据");
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
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
