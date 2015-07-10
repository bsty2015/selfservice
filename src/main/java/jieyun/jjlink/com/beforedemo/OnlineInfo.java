package jieyun.jjlink.com.beforedemo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import jieyun.jjlink.com.bean.online.Online;
import jieyun.jjlink.com.bean.online.Rows;
import jieyun.jjlink.com.jieyunclass.JieyunRow;
import jieyun.jjlink.com.jieyunclass.JieyunTextView;
import jieyun.jjlink.com.utils.FormatUtils;


public class OnlineInfo extends AppCompatActivity {
    private static final String REQUEST_URL = "http://192.9.2.101:8080/manage/self/online/getlist";
    private static final String LOGOUT_URL = "http://192.9.2.101:8080/manage/self/online/forcedis";
    private static final String USERANME = "用户名";
    private static final String FULLNAME = "姓名";
    private static final String SERVICE_NAME = "服务名";
    private static final String IPV4 = "用户ip地址";
    private static final String AREA_NAME = "区域名称";
    private static final String AREA_TYPE = "区域类型";
    private static final String START_TIME = "上线时间";
    TextView textView;
    TableLayout tab;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_online_info);
        textView = new TextView(getApplicationContext());
        HandlerThread handlerThread = new HandlerThread("online");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        tab = new TableLayout(getApplicationContext());
        tab.setStretchAllColumns(true);
        scrollView = new ScrollView(getApplicationContext());
        scrollView.addView(tab);
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
                    if (online == null || online.getRows() == null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "无在线记录", Toast.LENGTH_SHORT).show();
                                OnlineInfo.this.finish();
                                return;
                            }
                        });
                    } else {
                        for (Rows r : online.getRows()) {
                            createOnlineList(tab, getApplicationContext(), r, scrollView);
                            /*String username = r.getUsername();
                            String fullname = r.getFullname();
                            String serviceNmae = r.getService_name();
                            long ip = r.getUser_ipv4();
                            String area_name = r.getArea_name();
                            int area_type = r.getArea_type();
                            String startTime = FormatUtils.parseTime(r.getAcctstarttime());*/
                            /*sb.append(USERANME).append(": ").append(username).append("\n")
                                    .append(FULLNAME).append(": ").append(fullname).append("\n")
                                    .append(SERVICE_NAME).append(": ").append(serviceNmae).append("\n")
                                    .append(IPV4).append(": ").append(ip).append("\n")
                                    .append(AREA_NAME).append(": ").append(area_name).append("\n")
                                    .append(AREA_TYPE).append(": ").append(area_type).append("\n")
                                    .append(START_TIME).append(": ").append(startTime).append("\n");*/

                        }
                        final String info = sb.toString();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(info.toString());
                                setContentView(scrollView);
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


    /**
     * 动态创建在线用户的信息，可以手动下线
     * @param tab
     * @param context
     * @param r
     * @param s
     */
    private void createOnlineList(final TableLayout tab, Context context, final Rows r, ScrollView s) {
        JieyunRow row_username = new JieyunRow(context);
        JieyunRow row_fullname = new JieyunRow(context);
        JieyunRow row_servicename = new JieyunRow(context);
        JieyunRow row_ip = new JieyunRow(context);
        JieyunRow row_areaname = new JieyunRow(context);
        JieyunRow row_areatype = new JieyunRow(context);
        JieyunRow row_srcip = new JieyunRow(context);
        JieyunRow row_acctstarttime = new JieyunRow(context);
        JieyunRow row_logout = new JieyunRow(context);
        JieyunRow row_blank = new JieyunRow(context);
        JieyunTextView col_username_title = new JieyunTextView(context);
        col_username_title.setText("用户名");
        JieyunTextView col_username_text = new JieyunTextView(context);
        col_username_text.setText(r.getUsername());
        JieyunTextView col_fullname_title = new JieyunTextView(context);
        col_fullname_title.setText("姓名");
        JieyunTextView col_fullname_text = new JieyunTextView(context);
        col_fullname_text.setText(r.getFullname());
        JieyunTextView col_servicename_title = new JieyunTextView(context);
        col_servicename_title.setText("服务名");
        JieyunTextView col_servicename_text = new JieyunTextView(context);
        col_servicename_text.setText(r.getService_name());
        JieyunTextView col_ip_title = new JieyunTextView(context);
        col_ip_title.setText("用户ip地址");
        JieyunTextView col_ip_text = new JieyunTextView(context);
        col_ip_text.setText(FormatUtils.ipLongToIp(r.getUser_ipv4()));
        JieyunTextView col_areaname_title = new JieyunTextView(context);
        col_areaname_title.setText("区域名称");
        JieyunTextView col_areaname_text = new JieyunTextView(context);
        col_areaname_text.setText(r.getArea_name());
        JieyunTextView col_areatyep_title = new JieyunTextView(context);
        col_areatyep_title.setText("区域类型");
        JieyunTextView col_areatyep_text = new JieyunTextView(context);
        col_areatyep_text.setText(String.valueOf(r.getArea_type()));
        JieyunTextView col_srcip_title = new JieyunTextView(context);
        col_srcip_title.setText("客户端信息");
        JieyunTextView col_srcip_text = new JieyunTextView(context);
        col_srcip_text.setText(FormatUtils.ipLongToIp(r.getSrc_ip()));
        JieyunTextView col_acctstarttime_title = new JieyunTextView(context);
        col_acctstarttime_title.setText("上线时间");
        JieyunTextView col_acctstarttime_text = new JieyunTextView(context);
        col_acctstarttime_text.setText(FormatUtils.parseTime(r.getAcctstarttime()));
        JieyunTextView col_logout_title = new JieyunTextView(context);
        col_logout_title.setText("下线");
        JieyunTextView col_logout_text = new JieyunTextView(context);
        col_logout_text.setText("下线");
        /**
         * 点击下线 实现下线功能，切更新界面，去掉注销掉的在线信息。
         */
        col_logout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandlerThread handlerThread = new HandlerThread("logout");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            List<NameValuePair> params=new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("id",String.valueOf(r.getId())));
                            String result =StreamTool.doPost(LOGOUT_URL, params);
                            Gson gson=new Gson();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"下线成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                            tab.removeAllViewsInLayout();
                            scrollView.postInvalidate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        JieyunTextView col_blank_title = new JieyunTextView(context);
        col_blank_title.setBackgroundColor(255);
        col_blank_title.setText("");
        JieyunTextView col_blank_text = new JieyunTextView(context);
        col_blank_text.setBackgroundColor(255);
        col_blank_text.setText("");
        row_username.addView(col_username_title);
        row_username.addView(col_username_text);
        row_fullname.addView(col_fullname_title);
        row_fullname.addView(col_fullname_text);
        row_servicename.addView(col_servicename_title);
        row_servicename.addView(col_servicename_text);
        row_ip.addView(col_ip_title);
        row_ip.addView(col_ip_text);
        row_areaname.addView(col_areaname_title);
        row_areaname.addView(col_areaname_text);
        row_areatype.addView(col_areatyep_title);
        row_areatype.addView(col_areatyep_text);
        row_srcip.addView(col_srcip_title);
        row_srcip.addView(col_srcip_text);
        row_acctstarttime.addView(col_acctstarttime_title);
        row_acctstarttime.addView(col_acctstarttime_text);
        row_logout.addView(col_logout_title);
        row_logout.addView(col_logout_text);
        row_blank.addView(col_blank_title);
        row_blank.addView(col_blank_text);
        tab.addView(row_username);
        tab.addView(row_fullname);
        tab.addView(row_servicename);
        tab.addView(row_ip);
        tab.addView(row_areaname);
        tab.addView(row_areatype);
        tab.addView(row_srcip);
        tab.addView(row_acctstarttime);
        tab.addView(row_logout);
        tab.addView(row_blank);

    }
}
