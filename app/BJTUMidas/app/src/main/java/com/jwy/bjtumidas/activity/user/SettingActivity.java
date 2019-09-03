package com.jwy.bjtumidas.activity.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.customize.SettingItem;
import com.jwy.bjtumidas.utils.SysApplication;

public class SettingActivity extends AppCompatActivity
{

    private SharedPreferences sp_setting;
    private SettingItem si_update;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_setting);
        SysApplication.getInstance().addActivity(this);
        sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        setContentView(R.layout.activity_setting);
        si_update = (SettingItem) findViewById(R.id.si_setting_autoupdate);
        si_update.setChecked(sp_setting.getBoolean("auto_update", true));
        setListener();
    }

    /**
     * 设置监听器
     */
    private void setListener() {

        si_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (si_update.getChecked()) {//先获取选中状体,选中就改变为不选中,不选中就改变为选中
                    si_update.setChecked(false);
                    sp_setting.edit().putBoolean("auto_update", false).commit();
                } else {
                    si_update.setChecked(true);
                    sp_setting.edit().putBoolean("auto_update", true).commit();
                }
            }
        });
    }
}
