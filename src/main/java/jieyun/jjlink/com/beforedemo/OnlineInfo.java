package jieyun.jjlink.com.beforedemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jieyun.jjlink.com.bean.online.Online;
import jieyun.jjlink.com.bean.online.Rows;
import jieyun.jjlink.com.utils.ToolUtils;


public class OnlineInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.1.8:8080/manage/self/online/getlist";
    private static final String USERANME = "用户名";
    private static final String FULLNAME = "姓名";
    private static final String SERVICE_NAME = "服务名";
    private static final String IPV4 = "用户ip地址";
    private static final String AREA_NAME = "区域名称";
    private static final String AREA_TYPE = "区域类型";
    private static final String START_TIME = "上线时间";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_info);
        final TextView textView = (TextView) findViewById(R.id.id_online);
        HandlerThread handlerThread = new HandlerThread("online");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = StreamTool.doPost(REQUEST_URL, null);
                    System.out.println("查询在线信息：" + str);
                    Gson gson = new Gson();
                    //Type type= new TypeToken<Online>(){}.getType();
                    Online online = gson.fromJson(str, Online.class);
                    System.out.println(online.toString());
                    StringBuffer sb = new StringBuffer();
                    for (Rows r : online.getRows()) {
                        String username = r.getUsername();
                        String fullname = r.getFullname();
                        String serviceNmae = r.getService_name();
                        long ip = r.getUser_ipv4();
                        String area_name = r.getArea_name();
                        int area_type = r.getArea_type();
                        String startTime = ToolUtils.parseTime(r.getAcctstarttime());
                        sb.append(USERANME).append(": ").append(username).append("\n")
                                .append(FULLNAME).append(": ").append(fullname).append("\n")
                                .append(SERVICE_NAME).append(": ").append(serviceNmae).append("\n")
                                .append(IPV4).append(": ").append(ip).append("\n")
                                .append(AREA_NAME).append(": ").append(area_name).append("\n")
                                .append(AREA_TYPE).append(": ").append(area_type).append("\n")
                                .append(START_TIME).append(": ").append(startTime).append("\n");
                    }
                    final String info = sb.toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(info.toString());
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
        getMenuInflater().inflate(R.menu.menu_online_info, menu);
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
