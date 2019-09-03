package com.jwy.bjtumidas.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.activity.user.ManageActivity;
import com.jwy.bjtumidas.activity.user.SettingActivity;
import com.jwy.bjtumidas.activity.user.UserInfoActivity;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.utils.HttpStreamUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment
{
    private static final int WHAT_SEND_COMMENT_FAILED = 209;
    public static final int WHAT_SEND_COMMENT = 208;
    private View view_container;
    private LinearLayout ll_settings;
    private LinearLayout ll_userInfo;
    private LinearLayout ll_manager_goods;
    private LinearLayout ll_manager_comment;
    private static final int WHAT_UPREATE_NICKNAME = 1000;
    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_UPREATE_NICKNAME) {
                tv_nickName.setText((String) msg.obj);
            }
        }
    };
    private static TextView tv_nickName;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (view_container == null) {
            view_container = inflater.inflate(R.layout.fragment_user, null);
        }
        ViewGroup parent = (ViewGroup) view_container.getParent();
        if (parent != null) {
            parent.removeAllViewsInLayout();
        }
        return view_container;
        // Inflate the layout for this fragment
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        tv_nickName = (TextView) view_container.findViewById(R.id.tv_mine_nickname);
        tv_nickName.setText(getActivity().getSharedPreferences("login", MODE_PRIVATE).getString("nickname", "nickname1"));
        TextView tv_title = (TextView) view_container.findViewById(R.id.tv_classify_custom_actionbar_title);
        tv_title.setText("个人中心");
//        btn_login = (Button) view_container.findViewById(R.id.btn_mine_login);
        ll_userInfo = (LinearLayout) view_container.findViewById(R.id.ll_mine_userinfo);
        ll_settings = (LinearLayout) view_container.findViewById(R.id.ll_mine_settings);
        ll_manager_goods = (LinearLayout) view_container.findViewById(R.id.ll_mine_manager_goods);
        ll_manager_comment = (LinearLayout) view_container.findViewById(R.id.ll_mine_comment);
        setListener();
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        ll_manager_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ManageActivity.class));
            }
        });
        ll_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        ll_userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
            }
        });
        ll_manager_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onComment();
            }
        });
    }

    public void onComment() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("反馈");
        final EditText et_comment = new EditText(getActivity());
        et_comment.setBackgroundResource(R.drawable.shape_input);
        et_comment.setLines(5);
        et_comment.setGravity(Gravity.TOP);
        et_comment.setBackgroundResource(R.mipmap.et_comment_bg);
        builder.setView(et_comment);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String content = et_comment.getText().toString().trim();
                try {
                    content = URLEncoder.encode(content, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String url = getResources().getString(R.string.url_send_comment) + "?username=" +
                        getActivity().getSharedPreferences("login", MODE_PRIVATE).getString("username", "") +
                        "&content=" + content + "&gid=" + 1;
                Log.e("mylog", "评论的请求地址为：" + url);
                Log.e("mylog", "content：" +content);
                Log.e("mylog", "content：" +et_comment.getText());
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            HttpStreamUtils.sendHttpRequest(handler, WHAT_SEND_COMMENT, url);
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(WHAT_SEND_COMMENT_FAILED);
                        }
                    }
                }.start();


                ToastUtils.showInfo(getActivity(), "留言成功");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showInfo(getActivity(), "取消留言");
            }
        });
        AlertDialog commentDialog = builder.create();
        commentDialog.show();
    }

}
