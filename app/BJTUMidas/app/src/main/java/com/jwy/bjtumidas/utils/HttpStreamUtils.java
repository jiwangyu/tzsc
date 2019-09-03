package com.jwy.bjtumidas.utils;


import android.os.Handler;
import android.util.Log;

import com.jwy.bjtumidas.db.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Http请求,处理流
 */
public class HttpStreamUtils {

    private static HttpURLConnection conn;
    private static String json_str = "";//用户存放获取到的json信息

    /**
     * 发送http请求
     *
     * @param path
     * @throws IOException
     */
    public static void sendHttpRequest(Handler handler, int what, String path) throws IOException {
        //1,先获取url地址
        URL url = new URL(path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        setTimeout();
        publicMethod(handler, what);
    }

    /**
     * 获取用户信息
     *
     * @return
     * @throws JSONException
     */
    public static boolean isUpdateNickNameInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject(json_str);
        if ("successful".equals(jsonObject.getString("response"))) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户信息
     *
     * @return
     * @throws JSONException
     */
    public static Users getUserInfo() throws JSONException {
        JSONObject jsonObject = new JSONObject(json_str);
//        {"response":"successful","userinfo":{"uId":12,"uNo":"11111111111112","uNickName":"???"
// ,"uPhone":"15519099928","uPwd":"1f57e191e579b92a558b5e68248fcf56","isMan":true,"uTime":1461244998039}}
        if ("successful".equals(jsonObject.getString("response"))) {
            JSONObject userinfo = jsonObject.getJSONObject("userinfo");
            Users users = new Users();
            users.setuId(userinfo.getInt("uId"));
            users.setMan(userinfo.getBoolean("isMan"));
            users.setuNickName(userinfo.getString("uNickName"));
            users.setuPhone(userinfo.getString("uPhone"));
            users.setuPwd(userinfo.getString("uPwd"));
            users.setuTime(userinfo.getLong("uTime"));
            users.setuNo(userinfo.getString("uNo"));
            return users;
        } else {
            return null;
        }
    }

    /**
     * 使用post方式提交数据
     *
     * @param handler
     * @param what
     * @param path
     * @throws IOException
     */
    public static void sendHttpRequestByPost(Handler handler, int what, String path, Map<String, Object> param) throws IOException {
        //1,先获取url地址
        URL url = new URL(path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        setTimeout();//设置超时时间
        String data = ObjectUtils.hashMapToUrl(param);
        Log.e("mylog", "请求数据为:" + path + "?" + data);

        //设置响应头内容
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", data.length() + "");
        conn.setDoOutput(true);//开启输出流
        conn.getOutputStream().write(data.getBytes());//发送请求内容
        publicMethod(handler, what);
    }

    /**
     * 设置超时时间
     */
    private static void setTimeout() {
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
    }

    /**
     * 公共的方法
     *
     * @param handler
     * @param what
     * @throws IOException
     */
    private static void publicMethod(Handler handler, int what) throws IOException {
        if (conn.getResponseCode() == 200) {
            InputStream is = conn.getInputStream();
            byte[] buf = new byte[1024];
            int len = 0;
            json_str = "";
            while ((len = is.read(buf)) != -1) {
                json_str += new String(buf, 0, len);
            }
            is.close();
        }
        handler.sendEmptyMessage(what);
        close();//释放资源
    }

    /**
     * 获取注册信息
     *
     * @return
     * @throws JSONException
     */
    public static String getRegisterInfo() throws JSONException {
        JSONObject jObject = new JSONObject(json_str);
        return jObject.getString("register");
    }

    /**
     * 获取发布结果
     *
     * @return
     * @throws JSONException
     */
    public static String getPublishInfo() throws JSONException {
        JSONObject jObject = new JSONObject(json_str);
        return jObject.getString("publish") + "=-=" + jObject.optInt("gId");
    }

    /**
     * 获取修改密码信息
     *
     * @return
     * @throws JSONException
     */
    public static String getUpdatePwdInfo() throws JSONException {
        JSONObject jObject = new JSONObject(json_str);
        return jObject.getString("update_pwd");
    }

    /**
     * 获取登陆信息
     *
     * @return
     * @throws JSONException
     */
    public static String getLoginInfo() throws JSONException {
        JSONObject jObject = new JSONObject(json_str);
        Log.e("mylog", "获取的json文件为" + json_str.toString());
        return jObject.getString("login") + ",--," + jObject.getString("nickname");
    }

    /**
     * 获取
     *
     * @return
     */
    public static List<String> getAdvImgUrlToList() throws JSONException {
        JSONObject json = new JSONObject(json_str);
        JSONArray urls = json.getJSONArray("home_banner");
        List<String> urls_list = new ArrayList<String>();
        for (int i = 0; i < urls.length(); i++) {
            JSONObject urlJsonObject = urls.getJSONObject(i);
            urls_list.add(urlJsonObject.getString("pic"));
        }
        return urls_list;
    }

    /**
     * 获取求购信息
     *
     * @return
     */
    public static String getRequestInfo() throws JSONException {
        JSONObject jObject = new JSONObject(json_str);
        return jObject.getString("publishreq");
    }

    /**
     * 释放资源
     */
    private static void close() {
    }
}
