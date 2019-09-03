package com.jwy.bjtumidas.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.utils.HttpStreamUtils;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.ProgressDialogUtils;
import com.jwy.bjtumidas.utils.SysApplication;

import org.json.JSONException;

import java.io.IOException;

public class UpdatePwdActivity extends AppCompatActivity
{
    private EditText et_userName;
    private Intent mIntent;
    private EditText et_pwd;
    private EditText et_repwd;
    private static final int WHAT_GET_UPDATE_PWD_FINISHED = 1000;
    private static final int WHAT_CONNECTION_FAILED = 1001;
    private ProgressDialogUtils progressDialog;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_GET_UPDATE_PWD_FINISHED:
                    try {
                        progressDialog.closeProgressDialog();
                        String info = HttpStreamUtils.getUpdatePwdInfo();
                        if ("successful".equals(info)) {
                            ToastUtils.showInfo(UpdatePwdActivity.this, "修改成功");
//                            Log.e("mylog", "修改密码成功了,跳转到登录页面");
                        } else {
                            ToastUtils.showInfo(UpdatePwdActivity.this, "发生未知错误");
                        }
                        startActivity(new Intent(UpdatePwdActivity.this, LoginActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.showInfo(UpdatePwdActivity.this, "请求失败");
                    }
                    break;
                case WHAT_CONNECTION_FAILED:
                    progressDialog.closeProgressDialog();
                    ToastUtils.showInfo(UpdatePwdActivity.this, "网络链接失败");
                    break;
            }
        }
    };
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SysApplication.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        mIntent = getIntent();
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(R.layout.activity_update_pwd);
        progressDialog = new ProgressDialogUtils(this, "更改密码", "更改密码中");
        et_userName = (EditText) findViewById(R.id.et_update_username);
        phone = mIntent.getStringExtra("phone");
        et_userName.setText(phone);

        et_pwd = (EditText) findViewById(R.id.et_update_pwd);
        et_repwd = (EditText) findViewById(R.id.et_update_repwd);

    }


    /**
     * 更改密码
     *
     * @param view
     */
    public void onUpdate(View view) {
        String pwd = et_pwd.getText().toString().trim();//密码
        String re_pwd = et_repwd.getText().toString().trim();//确认密码
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(re_pwd)) {
            ToastUtils.showInfo(this, "密码不能为空");
            et_pwd.requestFocus();
            et_pwd.setCursorVisible(true);
            return;
        } else if (pwd.length() < 6 || re_pwd.length() < 6) {
            et_pwd.requestFocus();
            et_pwd.setCursorVisible(true);
            ToastUtils.showInfo(this, "密码的长度必须大于等于6位,小于等于20位");
            return;
        } else if (!pwd.equals(re_pwd)) {
            et_repwd.requestFocus();
            et_repwd.setCursorVisible(true);
            ToastUtils.showInfo(this, "两次输入的密码不一致,请重新输入");
            return;
        }
        final String md5_pwd = MD5Encoder.fiveMD5Encode(pwd);
        progressDialog.showProgressDialog();
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpStreamUtils.sendHttpRequest(handler, WHAT_GET_UPDATE_PWD_FINISHED,
                            getResources().getString(R.string.url_updatepwd) + "?username=" + phone + "&pwd=" + md5_pwd);
//                    Log.e("mylog", "请求参数为" + getResources().getString(R.string.url_updatepwd) + "?username=" + phone + "&pwd=" + md5_pwd);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(WHAT_CONNECTION_FAILED);

                }
            }
        }.start();

    }

    /**
     * 更改密码
     *
     * @param view
     */
    public void onCancle(View view) {

        finish();
    }

    /**
     * 返回键
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }
}
