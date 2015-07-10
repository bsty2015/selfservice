package jieyun.jjlink.com.beforedemo;

import android.content.Context;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import jieyun.jjlink.com.bean.credentials.Credentials;
import jieyun.jjlink.com.bean.credentials.Rows;
import jieyun.jjlink.com.jieyunclass.JieyunRow;
import jieyun.jjlink.com.jieyunclass.JieyunTextView;
import jieyun.jjlink.com.utils.FormatUtils;

public class CredentialsInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/authlog/getlist";
    private TableLayout tab;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_credentials_info);
        tab = new TableLayout(getApplicationContext());
        tab.setStretchAllColumns(true);
        scrollView = new ScrollView(getApplicationContext());
        scrollView.addView(tab);
        //final TextView textView = (TextView) findViewById(R.id.id_credentials_info);
        HandlerThread handlerThread = new HandlerThread("credentials");
        handlerThread.start();
        android.os.Handler handler = new android.os.Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String info = StreamTool.doPost(REQUEST_URL, null);
                    Gson gson = new Gson();
                    Credentials credentials = gson.fromJson(info, Credentials.class);
                    List<Rows> rowses = credentials.getRows();
                    if (credentials == null || rowses == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "无认证信息", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });
                    } else {
                        for (Rows r : rowses) {
                            createCredentialsList(getApplicationContext(), r, tab);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(sb.toString());
                                setContentView(scrollView);
                            }
                        });
                    }
                    // final StringBuffer sb = new StringBuffer();
                    /*for (Rows r : rowses) {
                        sb.append("用户名: ").append(r.getUsername()).append("\n")
                                .append("区域名: ").append(r.getArea_name()).append("\n")
                                .append("区域类型：").append(r.getArea_type()).append("\n")
                                .append("认证时间：").append(FormatUtils.parseTime(r.getDatetime())).append("\n")
                                .append("认证结果：").append(r.getReply_msg()).append("\n");

                    }*/

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("网络访问异常");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_credentials_info, menu);
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

    private void createCredentialsList(Context context, Rows rows, TableLayout tab) {
        JieyunRow row_username = new JieyunRow(context);
        JieyunRow row_areaname = new JieyunRow(context);
        JieyunRow row_areatype = new JieyunRow(context);
        JieyunRow row_datetime = new JieyunRow(context);
        JieyunRow row_replymsg = new JieyunRow(context);
        JieyunTextView col_username_title = new JieyunTextView(context);
        JieyunTextView col_username_text = new JieyunTextView(context);
        JieyunTextView col_areaname_title = new JieyunTextView(context);
        JieyunTextView col_areaname_text = new JieyunTextView(context);
        JieyunTextView col_areatype_title = new JieyunTextView(context);
        JieyunTextView col_areatype_text = new JieyunTextView(context);
        JieyunTextView col_datetime_title = new JieyunTextView(context);
        JieyunTextView col_datetime_text = new JieyunTextView(context);
        JieyunTextView col_replymsg_title = new JieyunTextView(context);
        JieyunTextView col_replymsg_text = new JieyunTextView(context);
        col_username_title.setText("用户名");
        col_username_text.setText(rows.getUsername());
        col_areaname_title.setText("区域名");
        col_areaname_text.setText(rows.getArea_name());
        col_areatype_title.setText("区域类型");
        col_areatype_text.setText(rows.getArea_type());
        col_datetime_title.setText("认证时间");
        col_datetime_text.setText(FormatUtils.parseTime(rows.getDatetime()));
        col_replymsg_title.setText("认证结果");
        col_replymsg_text.setText(rows.getReply_msg());
        row_username.addView(col_username_title);
        row_username.addView(col_username_text);
        row_areaname.addView(col_areaname_title);
        row_areaname.addView(col_areaname_text);
        row_areatype.addView(col_areatype_title);
        row_areatype.addView(col_areatype_text);
        row_datetime.addView(col_datetime_title);
        row_datetime.addView(col_datetime_text);
        row_replymsg.addView(col_replymsg_title);
        row_replymsg.addView(col_replymsg_text);
        tab.addView(row_username);
        tab.addView(row_areaname);
        tab.addView(row_areatype);
        tab.addView(row_datetime);
        tab.addView(row_replymsg);
    }
}
