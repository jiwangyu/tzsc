package com.jwy.bjtumidas.activity.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.utils.HttpStreamUtils;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.SysApplication;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity
{
    private String phone;
    private Intent mIntent;
    private EditText et_repwd;
    private EditText et_pwd;
    private EditText et_sNo;
    private RadioGroup rg_sex;
    private EditText et_nickName;
    private ProgressDialog pDialog;
    private static final int WHAT_GET_REGISTER_FINSHED = 1000;
    private static final int WHAT_CONNECTION_FAILED = 1001;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_GET_REGISTER_FINSHED:
                    closeRegistingDialog();
                    try {
                        String info = HttpStreamUtils.getRegisterInfo();
//                        Log.e("mylog", "信息为" + info);
                        if ("successful".equals(info)) {
                            ToastUtils.showInfo(RegisterActivity.this, "注册成功");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else if ("existed".equals(info)) {
                            ToastUtils.showInfo(RegisterActivity.this, "该手机号或学号已注册");
                        } else if ("failed".equals(info)) {
                            ToastUtils.showInfo(RegisterActivity.this, "注册失败");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        ToastUtils.showInfo(RegisterActivity.this, "请求失败");
                    }
                    break;
                case WHAT_CONNECTION_FAILED:
                    closeRegistingDialog();
                    ToastUtils.showInfo(RegisterActivity.this, "网络连接失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SysApplication.getInstance().addActivity(this);
        mIntent = getIntent();
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(R.layout.activity_register);
        //昵称
        et_nickName = (EditText) findViewById(R.id.et_register_nickname);
        phone = mIntent.getStringExtra("phone");
        et_nickName.setText(phone);//号码回显

        et_sNo = (EditText) findViewById(R.id.et_register_sno);//学号

        et_repwd = (EditText) findViewById(R.id.et_register_repwd);//再次确认密码

        et_pwd = (EditText) findViewById(R.id.et_register_pwd);//密码
        rg_sex = (RadioGroup) findViewById(R.id.rg_register_sex);
        rg_sex.check(R.id.rb_register_man);//默认选中第一个

        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setTitle("用户注册");
        pDialog.setMessage("正在注册");

    }

    /**
     * 显示正在注册中
     */
    private void showRegistingDialog() {
        pDialog.show();
    }

    /**
     * 显示正在登陆中
     */
    private void closeRegistingDialog() {
        pDialog.dismiss();
    }

    /**
     * 返回按钮
     *
     * @param view
     */
    public void onBack(View view) {

        finish();
    }

    /**
     * 注册按钮
     *
     * @param view
     */
    public void onRegister(View view) {
        String nickName = et_nickName.getText().toString().trim();//昵称
        if (TextUtils.isEmpty(nickName)) {
            ToastUtils.showInfo(this, "昵称不能为空");
            et_nickName.requestFocus();
            et_nickName.setCursorVisible(true);
            return;
        }

        String pwd = et_pwd.getText().toString().trim();//密码
        String re_pwd = et_repwd.getText().toString().trim();//错误密码
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

        String sNo = et_sNo.getText().toString().trim();//学号
        if (sNo.length() != 8) {
            et_sNo.requestFocus();
            et_sNo.setCursorVisible(true);
            ToastUtils.showInfo(this, "学号输入有误(请查看是否有8位)");
            return;
        }
        boolean isMan = true;
        if (R.id.rb_register_man != rg_sex.getCheckedRadioButtonId()) {
            isMan = false;
        }
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("sNo", sNo);
        params.put("nickName", nickName);
        params.put("pwd", MD5Encoder.fiveMD5Encode(pwd));
        params.put("sex", isMan + "");
        params.put("phone", phone);
//        Log.e("mylog", nickName + " - " + pwd + " - " + sNo + " - " + (isMan ? "男" : "女"));
        showRegistingDialog();
        new Thread() {
            @Override
            public void run() {

                try {
                    HttpStreamUtils.sendHttpRequestByPost(handler, WHAT_GET_REGISTER_FINSHED,
                            getResources().getString(R.string.url_register), params);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(WHAT_CONNECTION_FAILED);
                }
            }
        }.start();
    }

    /**
     * 立即登陆按钮
     *
     * @param view
     */
    public void onLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
