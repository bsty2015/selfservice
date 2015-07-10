package jieyun.jjlink.com.beforedemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String LOGIN_SELF_SERVICE_URL = "http://192.9.2.101:8080/manage/self/auth/login";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.user_info);
                break;
            case 2:
                mTitle = getString(R.string.self_service);
                break;
         /*   case 3:
                mTitle = getString(R.string.credentials);
                break;
            case 4:
                mTitle = getString(R.string.details_info);
                break;
            case 5:
                mTitle = getString(R.string.billing_info);
                break;
            case 6:
                mTitle =getString(R.string.Recharge);
                break;*/
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
                final TextView textView = (TextView) rootView.findViewById(R.id.section_label);

                HandlerThread handlerThread = new HandlerThread("login_self_service");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String textContext;
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            //String username = (String) getSpInfo("userInfo", "uname");
                            //String passwd = (String) getSpInfo("userInfo", "pwd");
                            params.add(new BasicNameValuePair("username", "admin"));
                            params.add(new BasicNameValuePair("password", "admin"));
                            textContext = StreamTool.doPost(LOGIN_SELF_SERVICE_URL, params);

                            String loginInfo = showLoginInfo(textContext);
                            
                            //textView.setText(loginInfo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                return rootView;
            } else {
                //各个查询菜单
                View rootView = inflater.inflate(R.layout.fragment_main_other, container, false);
                TextView tv_user = (TextView) rootView.findViewById(R.id.id_user_info);
                setOnclickMethod(tv_user.getId(), UserInfoActivity.class, rootView);
                TextView tv_online = (TextView) rootView.findViewById(R.id.id_online_info);
                setOnclickMethod(tv_online.getId(), OnlineInfo.class, rootView);
                TextView tv_credentials = (TextView) rootView.findViewById(R.id.id_credentials);
                setOnclickMethod(tv_credentials.getId(), CredentialsInfo.class, rootView);
                TextView tv_details = (TextView) rootView.findViewById(R.id.id_details_info);
                setOnclickMethod(tv_details.getId(), DetailInfo.class, rootView);
                TextView tv_bill = (TextView) rootView.findViewById(R.id.id_bill_info);
                setOnclickMethod(tv_bill.getId(), BillInfo.class, rootView);
                TextView tv_recharge = (TextView) rootView.findViewById(R.id.id_recharge_info);
                setOnclickMethod(tv_recharge.getId(), RechargeInfo.class, rootView);
                return rootView;
            }
        }

        /**
         * 获取app自身储存的登录所需的信息，如用户名密码
         *
         * @param spname
         * @param spStr
         * @return
         */
        private Object getSpInfo(String spname, String spStr) {
            SharedPreferences sp = getActivity().getSharedPreferences(spname, Context.MODE_WORLD_READABLE);
            return sp.getString(spStr, null);
        }

        /**
         * 显示登录信息
         *
         * @param jsonStr
         * @return
         */
        private String showLoginInfo(String jsonStr) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String result_msg = jsonObject.getString("reply_msg");
                JSONObject results = (JSONObject) jsonObject.get("results");
                String username = results.getString("username");
                String loginTime = parseTime((Integer) results.get("logintime"));
                String fullname = results.getString("fullname");
                StringBuffer sb = new StringBuffer();
                sb.append("登录结果：").append(result_msg).append("\n用户名：").append(username).append("\n全名：").append(fullname).append("\n登录时间：").append(loginTime);
                return sb.toString();
            } catch (JSONException e) {
                e.printStackTrace();
                return "登录返回结果异常";
            }

        }


        private void setOnclickMethod(int id, final Class clazz, View rootView) {
            TextView textView = (TextView) rootView.findViewById(id);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().startActivity(new Intent(getActivity(), clazz));
                }
            });
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    private static String parseTime(int time) {
        System.setProperty("user.timezone", "Asia/Shanghai");
        TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
        TimeZone.setDefault(tz);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Time(time * 1000l);
        return sdf.format(date);
    }

}
