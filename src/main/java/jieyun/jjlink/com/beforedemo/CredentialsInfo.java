package jieyun.jjlink.com.beforedemo;

import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import jieyun.jjlink.com.bean.credentials.Credentials;
import jieyun.jjlink.com.bean.credentials.Rows;
import jieyun.jjlink.com.utils.ToolUtils;

public class CredentialsInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/authlog/getlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credentials_info);
        final TextView textView = (TextView) findViewById(R.id.id_credentials_info);
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
                    final StringBuffer sb = new StringBuffer();
                    for (Rows r : rowses) {
                        sb.append("用户名: ").append(r.getUsername()).append("\n")
                                .append("区域名: ").append(r.getArea_name()).append("\n")
                                .append("区域类型：").append(r.getArea_type()).append("\n")
                                .append("认证时间：").append(ToolUtils.parseTime(r.getDatetime())).append("\n")
                                .append("认证结果：").append(r.getReply_msg()).append("\n");
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
}
