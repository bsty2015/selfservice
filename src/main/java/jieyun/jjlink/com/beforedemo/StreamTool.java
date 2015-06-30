package jieyun.jjlink.com.beforedemo;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by lu on 2015/6/15.
 */
public class StreamTool {

    private static HttpParams httpParams;
    private static DefaultHttpClient httpClient;
    private static String JSESSIONID;

    private static HttpClient getHttpClient() throws Exception{
        httpParams=new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams,20*1000);
        HttpConnectionParams.setSoTimeout(httpParams,20*1000);
        HttpConnectionParams.setSocketBufferSize(httpParams,8192);
        HttpClientParams.setRedirecting(httpParams,true);
        String userAgent="Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams,userAgent);
        httpClient=new DefaultHttpClient(httpParams);
        return httpClient;
    }

    public static String doPost(String url, List<NameValuePair> params) throws Exception{
        HttpPost httpRequest= new HttpPost(url);
        String strResult="doPostError";
        httpClient= (DefaultHttpClient) getHttpClient();
        if(params!=null&&params.size()>0){
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        }
        if(null!=JSESSIONID){
            httpRequest.setHeader("Cookie","JSESSIONID="+JSESSIONID);
        }
        HttpResponse httpResponse=httpClient.execute(httpRequest);
        if(httpResponse.getStatusLine().getStatusCode()==200){
            strResult= EntityUtils.toString(httpResponse.getEntity());
            CookieStore cookieStore=httpClient.getCookieStore();
            List<Cookie> cookies=cookieStore.getCookies();
            for(int i=0;i<cookies.size();i++){
                if("JSESSIONID".equals(cookies.get(i).getName())){
                    JSESSIONID=cookies.get(i).getValue();
                    break;
                }
            }

        }
        Log.v("strResult",strResult);
        return strResult;
    }


    /**
     * 登录自助服务平台的url
     */
    private static final String LOGIN_SELF_SERVICE_URL="http://192.9.1.12:8080/manage/self/auth/login?username=test&password=test123";

    public byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while((len=inStream.read(buffer))!=-1){
            outStream.write(buffer,0,len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 获取cookie
     * @return
     */
    public String getCookie(){
        try {
            URL url=new URL(LOGIN_SELF_SERVICE_URL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setReadTimeout(5*1000);
            con.setReadTimeout(5*1000);
            con.connect();
            //取得sessionid
            String cookieval=con.getHeaderField("set-cookie");
            String sessionid;
            if(cookieval!=null){
                sessionid=cookieval.substring(0,cookieval.indexOf(";"));
                return sessionid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送cookie
     * @param queryStr
     */
    public String postCookie(String queryStr){
        try {
            URL url=new URL(queryStr);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            String sessionId=getCookie();
            if(sessionId!=null){
                con.setRequestProperty("cookie",sessionId);
                con.setConnectTimeout(5*1000);
                con.setReadTimeout(5*1000);
                con.setRequestMethod("POST");
                con.connect();
                InputStream inStream=con.getInputStream();
                byte[] data=readInputStream(inStream);
                String json=new String(data);
                JSONArray array=new JSONArray(json);
                StringBuffer sb=new StringBuffer();
                for(int i=0;i<array.length();i++){
                    JSONObject item=array.getJSONObject(i);
                    int reply_code=item.getInt("reply_code");
                    String request_url=item.getString("request_url");
                    String reply_msg=item.getString("reply_msg");
                    sb.append("reply_code:").append(reply_code).append(" reply_msg:").append(reply_msg);
                    JSONArray jsonArray=item.getJSONArray("userInfoResults");
                    for (int j=0;j<jsonArray.length();j++){
                        JSONObject jo=jsonArray.getJSONObject(j);
                        String username=jo.getString("username");
                        String fullname=jo.getString("fullname");
                        sb.append(" username:").append(username).append(" fullname:").append(fullname);
                    }

                }
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
        return null;
    }

}
