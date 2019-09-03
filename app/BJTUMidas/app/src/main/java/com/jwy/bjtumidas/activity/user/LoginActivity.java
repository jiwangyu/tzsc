package com.jwy.bjtumidas.activity.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.activity.MainActivity;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.customize.TopBackAndTitleView;
import com.jwy.bjtumidas.utils.HttpStreamUtils;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.SysApplication;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class LoginActivity extends AppCompatActivity
{
    private static final int TYPE_REGISTER = 1;
    private static final int TYPE_UPDATE_PWD = 2;
    private static final int WHAT_GET_LOGIN_INFO_FINSHED = 1000;
    private static final int WHAT_LOGIN_NETWORK_EXCEPTION = 1003;
    private static final int WHAT_LOGINING = 1001;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case WHAT_GET_LOGIN_INFO_FINSHED:
                    closeLoginingDialog();//获取登陆结果之后,就隐藏对话框
                    String info = null;
                    try
                    {
                        info = HttpStreamUtils.getLoginInfo();
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                        ToastUtils.showInfo(LoginActivity.this, "登陆失败");
                    }
                    String[] infos = info.split(",--,");
                    String nickName = infos[1];
//                    Log.e("mylog", "昵称到底是什么--------" + nickName + "还有结果是什么" + infos[0]);
                    if ("successful".equals(infos[0]))
                    {
                        ToastUtils.showInfo(LoginActivity.this, "登陆成功");
                        saveUserInfo(username, md5_pwd, nickName);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));//登录成功,跳转到主页面
                        finish();
                    } else if ("failed".equals(infos[0]))
                    {
                        ToastUtils.showInfo(LoginActivity.this, "用户名或密码错误");
                    }
                    break;
                case WHAT_LOGIN_NETWORK_EXCEPTION:
                    closeLoginingDialog();//网络连接出问题了,就隐藏对话框
                    ToastUtils.showInfo(LoginActivity.this, "网络连接出错");
                    break;
                case WHAT_LOGINING:
                    showLoginingDialog();
                    break;
            }
        }

    };

    private EditText et_pwd;
    private EditText et_userName;
    private String username;
    private String md5_pwd;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        SharedPreferences login_sp = getSharedPreferences("login", MODE_PRIVATE);
        String username = login_sp.getString("username", "");
        String pwd = login_sp.getString("pwd", "");
        if ((!TextUtils.isEmpty(username) && (!TextUtils.isEmpty(pwd))) && (username.length() == 11 || username.length() == 8) && pwd.length() == 32) {
            startActivity(new Intent(this, MainActivity.class));
//            Log.e("mylog", "已经登录过了,就不用登录了");
        } else {
            initView();
        }
    }

    /**
     * 初始化布局
     */
    private void initView()
    {
        setContentView(R.layout.activity_login);
        et_userName = (EditText) findViewById(R.id.et_login_username);
        et_userName.setText(getSharedPreferences("login", MODE_PRIVATE).getString("username", ""));
        et_pwd = (EditText) findViewById(R.id.et_login_pwd);
        TopBackAndTitleView tbatv_login_topbar = (TopBackAndTitleView) findViewById(R.id.tbatv_login_topbar);
        tbatv_login_topbar.setText("用户登录");
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setTitle("用户登陆");
        pDialog.setMessage("正在登陆");
    }

    /**
     * 保存用户信息
     */
    private void saveUserInfo(String username, String md5_pwd, String nickName)
    {
        SharedPreferences sp_login = getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp_login.edit();
        editor.putString("username", username);
        editor.putString("nickname", nickName);
        editor.putString("pwd", md5_pwd);
        editor.commit();
    }

    /**
     * 返回按钮
     *
     * @param view
     */
    public void onBack(View view)
    {
        finish();
    }

    /**
     * 登录按钮
     *
     * @param view
     */
    public void onLogin(View view)
    {

        username = et_userName.getText().toString().trim();
//        Log.e("mylog", "用户名长度为:" + username.length());
        if (TextUtils.isEmpty(username))
        {
            et_userName.requestFocus();
            et_userName.setCursorVisible(true);
            ToastUtils.showInfo(this, "用户名不能为空哦");
            return;
        } else if (username.length() < 8)
        {
            et_userName.requestFocus();
            et_userName.setCursorVisible(true);
            ToastUtils.showInfo(this, "请输入正确的 学号/手机");
            return;
        }
        String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(pwd))
        {
            ToastUtils.showInfo(this, "密码不能为空");
            et_pwd.requestFocus();
            et_pwd.setCursorVisible(true);
            return;
        } else if (pwd.length() < 6)
        {
            et_pwd.requestFocus();
            et_pwd.setCursorVisible(true);
            ToastUtils.showInfo(this, "密码的长度必须大于等于6位");
            return;
        }
        handler.sendEmptyMessage(WHAT_LOGINING);
        //对密码进行5次MD5加密
        md5_pwd = MD5Encoder.fiveMD5Encode(pwd);
        //在这里发送用户信息请求
//        Log.e("mylog", "登录了");
//        ToastUtils.showInfo(this, "登录了,进行登录验证");
        new Thread()
        {
            @Override
            public void run()
            {
                try
                {

                    HttpStreamUtils.sendHttpRequest(handler, WHAT_GET_LOGIN_INFO_FINSHED,
                            getResources().getString(R.string.url_login) + "?username=" +
                                    username + "&pwd=" + md5_pwd);

                } catch (IOException e)
                {
                    e.printStackTrace();
                    handler.sendEmptyMessage(WHAT_LOGIN_NETWORK_EXCEPTION);
                }
            }
        }.start();
    }

    /**
     * 显示正在登陆中
     */
    private void showLoginingDialog() {
        pDialog.show();
    }

    /**
     * 显示正在登陆中
     */
    private void closeLoginingDialog() {
        pDialog.dismiss();
    }

    /**
     * 注册按钮
     *
     * @param view
     */
    public void onRegister(View view) {
        //先开启短信验证,通过了才能注册
        startSMSCheck(TYPE_REGISTER);
//        registerUser("15519099928", TYPE_REGISTER);
    }

    /**
     * 找回密码按钮
     *
     * @param view
     */
    public void onGetPwd(View view) {
        //先开启短信验证,通过了才能修改密码
        startSMSCheck(TYPE_UPDATE_PWD);
//        registerUser("15519099928",TYPE_UPDATE_PWD);
    }

    /**
     * 开启短信验证
     *
     * @param type
     */
    private void startSMSCheck(final int type) {
        //打开注册页面
        RegisterPage registerPage = new RegisterPage();

        registerPage.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                // 解析注册结果
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //依然走短信验证
                    @SuppressWarnings("unchecked")
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");
                    // 提交用户信息
                    registerUser(phone, type);
                }
            }
        });
        registerPage.show(this);
    }

    /**
     * 验证成功之后,跳转装注册用户(完善信息页面)
     *
     * @param phone
     */
    private void registerUser(String phone, int type) {
        Intent it = null;
        if (type == TYPE_REGISTER) {
            it = new Intent(this, RegisterActivity.class);
        } else if (type == TYPE_UPDATE_PWD) {
            it = new Intent(this, UpdatePwdActivity.class);
        }
        it.putExtra("phone", phone);
        startActivity(it);
        finish();
    }

    private long exitTime = 0;

    /**
     * 按两次返回键退出系统
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showInfo(this, "再按一次退出跳蚤市场");
                exitTime = System.currentTimeMillis();
            } else {
                SysApplication.getInstance().exit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
