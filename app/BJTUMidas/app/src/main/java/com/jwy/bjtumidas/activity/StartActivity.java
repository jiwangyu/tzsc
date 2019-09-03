package com.jwy.bjtumidas.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.activity.user.LoginActivity;
import com.jwy.bjtumidas.utils.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.SMSSDK;

public class StartActivity extends AppCompatActivity
{
    private ImageView im_start_logo;
    private ImageView im_start_name;
    //private TextView tv_start_font_gc;// 贵财字样的textview
    //private TextView tv_start_font_tz;// 跳蚤字样的textview
    //private TextView tv_start_font_market;// 市场字样的textview
    private static final int ANIM_DURATION = 1000;
    private static final String SHAERDPREF_FILENAME = "setting";// sharedpreference中存储的文件名-----主要用于存放是否是新手导航页面
    private ImageView tViews[];
    //private TextView tv_start_version;//显示版本号的tv
    //private int current_versionCode;//当前apk的版本号
    private int versionCode_fromServeice;
    public static final int WHAT_GET_SERVER_VERSIONCODE_FINSHED = 1000;
    private static final int WHAT_FILEDOWNLOAD_FAILURE = 1001;
    private static final int WHAT_FILE_DOWNLOADING = 1002;
    private int old_current = 0;
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case WHAT_GET_SERVER_VERSIONCODE_FINSHED:
                    //必须要获取到了服务器的版本号才可以进行下面的操作
                    //checkUpdate();
                    goMain();
                    break;
                case WHAT_FILEDOWNLOAD_FAILURE:
                    goMain();
                    break;
                case WHAT_FILE_DOWNLOADING:

                    Bundle bundle = (Bundle) msg.obj;
                    int percent = bundle.getInt("percent");
                    int current = bundle.getInt("current");
                    tv_current_progress.setText("当前进度:" + percent + " %");
                    tv_download_speed.setText("速度:" + (current - old_current)
                            / 1024 + " Kb/s");
                    old_current = current;
                    break;
            }
        }
    };


    private ProgressBar pb_download;
    private TextView tv_current_progress;
    private TextView tv_download_speed;
    private ProgressBar pb_version;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_start);
        SysApplication.getInstance().addActivity(this);
        initSMSSDK();//初始化smssdk
        initView();//初始化视图
        SharedPreferences sp_setting = getSharedPreferences("setting", MODE_PRIVATE);
        //判断是否是自动更新
        if (!sp_setting.getBoolean("auto_update", true))
        {
            goMain();
        } else
        {
            goMain();
            //getUpdateVersionCodeInfo();
        }
    }

    /**
     * 初始化短信sdk
     */
    private void initSMSSDK()
    {
        SMSSDK.initSDK(this, "119e746d33b9f", "e0313795a8c5cc3334339a0a8f14b2a8");
    }

    /**
     * 检测是否有版本更新
     */
//    private void checkUpdate()
//    {
//        if (current_versionCode != versionCode_fromServeice)
//        {
//            //有更新,提示用户更新应用
////            Log.e("mylog", "有更新哦");
//            remindUserUpdateApp();
//        } else
//        {
//            //没有更新跳转到主页
//            goMain();
//        }
//    }

    /**
     * 跳转至主页面
     */
    private void goMain()
    {
        //pb_version.setVisibility(View.INVISIBLE);
        initAnim();
        new Timer().schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                // 先读取出sharedPreferences中的isFirstStart的 值
                SharedPreferences sharedPreferences = getSharedPreferences(
                        SHAERDPREF_FILENAME, MODE_PRIVATE);
                boolean isFirstStart = sharedPreferences.getBoolean(
                        "isFirstStart", true);
//                Log.e("mylog", "isFirstStart=" + isFirstStart);
                Intent mIntent = null;
                if (isFirstStart)
                {
                    // 进入到导航页面，并在导航页的最后一页中单击体验的时候，将isFirstStart设置为false
//                    mIntent = new Intent(StartActivity.this,
//                            GuideActivity.class);
//                    startActivity(mIntent);
//                    finish();// 跳转之后就销毁这个activity
                    new Timer().schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            Intent it = new Intent(StartActivity.this, LoginActivity.class);
                            startActivity(it);
                            finish();// 跳转之后就销毁这个activity
                        }
                    }, 1000);
                } else
                {
                    // 不是软件的第一次启动/软件更新后的第一次启动，直接进入到主页面------登录
                    // 为了提高用户体验,跳转之前先等待2s
                    new Timer().schedule(new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            Intent it = new Intent(StartActivity.this, LoginActivity.class);
                            startActivity(it);
                            finish();// 跳转之后就销毁这个activity
                        }
                    }, 1000);
                }
            }
        }, 2000);

    }

    /**
     * 提醒用户更新应用
     */
    private void remindUserUpdateApp()
    {
        //1弹出对话框
        //showIsDownloadDialog();
//        showUpdataInfo();
    }

    private void showUpdataInfo()
    {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("确定要更新?");
//        builder.setMessage("有更新哦");
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
//        {
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                downloadApk();
//                showDownloadProgress();
//            }
//
//        });
//        builder.setCancelable(false);
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
//        {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                goMain();
//            }
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//        alertDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
//        {
//
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode,
//                                 KeyEvent event)
//            {
//                if (keyCode == KeyEvent.KEYCODE_BACK)
//                {
//                    goMain();
//                }
//                return true;
//            }
//        });
    }

    /**
     * 显示是否下载对话框
     */
    private void showIsDownloadDialog()
    {
//        final Dialog updateInfo_dialog = new Dialog(this);
//        updateInfo_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//不能让它有标题栏哦
//        View view = View.inflate(this, R.layout.dialog_isdownload, null);
//        updateInfo_dialog.setContentView(view);
//        //确定的时候需要跳转到下载页面
//        Button btn_ok = (Button) view.findViewById(R.id.btn_isdownload_ok);
//        btn_ok.setOnClickListener(new android.view.View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                downloadApk();
//                showDownloadProgress();
//                updateInfo_dialog.dismiss();
//            }
//
//        });
//        //取消按钮
//        Button btn_cancle = (Button) view
//                .findViewById(R.id.btn_isdownload_cancle);
//        btn_cancle.setOnClickListener(new android.view.View.OnClickListener()
//        {
//
//            @Override
//            public void onClick(View arg0)
//            {
//                updateInfo_dialog.dismiss();
//                goMain();
//            }
//        });
//        updateInfo_dialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent);
//        //设置点击边框之外不可以消失
//        updateInfo_dialog.setCancelable(false);
//        updateInfo_dialog.setCanceledOnTouchOutside(false);
//        //监听用户点击返回键
//        updateInfo_dialog.setOnKeyListener(new DialogInterface.OnKeyListener()
//        {
//
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode,
//                                 KeyEvent event)
//            {
//                if (keyCode == KeyEvent.KEYCODE_BACK)
//                {
//                    updateInfo_dialog.dismiss();
//                    goMain();
//                }
//                return true;
//            }
//        });
//        updateInfo_dialog.show();
    }

    /**
     * 显示当前下载进度
     */
    private void showDownloadProgress()
    {
//        Dialog dialog = new Dialog(this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//不能让它有标题栏哦
//        View dpView = View.inflate(this, R.layout.dialog_download_progress,
//                null);
//        dialog.setContentView(dpView);
//        pb_download = (ProgressBar) dpView.findViewById(R.id.progressBar);
//        tv_current_progress = (TextView) dpView
//                .findViewById(R.id.tv_current_progress);
//        tv_download_speed = (TextView) dpView
//                .findViewById(R.id.tv_download_speed);
//        dialog.getWindow().setBackgroundDrawableResource(
//                android.R.color.transparent);
//        dialog.setCancelable(false);
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.show();
    }

    /**
     * 下载apk文件
     */
    private void downloadApk()
    {
//        HttpUtils mHttpUtils = new HttpUtils();
//        if (Environment.getExternalStorageState().equals(
//                Environment.MEDIA_MOUNTED))
//        {
//            File file = new File(Environment.getExternalStorageDirectory()
//                    + "/tzsc.apk");
//            mHttpUtils.download(getResources().getString(R.string.url_apk_download),
//                    file.getAbsolutePath(), new RequestCallBack<File>()
//                    {
//                        @Override
//                        public void onStart()
//                        {
//                            super.onStart();
//                        }
//
//                        @Override
//                        public void onFailure(HttpException arg0, String arg1)
//                        {
//                            handler.sendEmptyMessage(WHAT_FILEDOWNLOAD_FAILURE);
//                        }
//
//                        @Override
//                        public void onSuccess(ResponseInfo<File> reInfo)
//                        {
//                            installApk(reInfo.result);
//                        }
//
//                        @Override
//                        public void onLoading(long total, long current,
//                                              boolean isUploading)
//                        {
//                            super.onLoading(total, current, isUploading);
//                            // if (isUploading) {
//                            pb_download.setMax((int) total);
//                            pb_download.setProgress((int) current);
//                            Message msg = handler.obtainMessage();
//                            msg.what = WHAT_FILE_DOWNLOADING;
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("current", (int) current);
//                            bundle.putInt("percent",
//                                    (int) (((float) current / total) * 100));
//                            msg.obj = bundle;
//                            handler.sendMessage(msg);
//                            // }
//                        }
//                    });
//            // }
//        }
    }

    /**
     * 安装文件
     *
     * @param file
     */
    private void installApk(File file)
    {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.setDataAndType(Uri.fromFile(file),
//                "application/vnd.android.package-archive");
//        startActivityForResult(intent, 0);
    }


    /**
     * 获取服务器中的版本号信息
     */
    private void getUpdateVersionCodeInfo()
    {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(getResources().getString(R.string.url_version),
//                null, new MyJsonObjectListener(), new MyErrorListener());
//        queue.add(jsonObjectRequest);
//        queue.start();
    }

    /**
     * Json请求监听器
     */
    class MyJsonObjectListener implements Response.Listener<JSONObject>
    {
        /**
         * 成功的时候会调用这个方法,此时就可以解析数据了
         */
        @Override
        public void onResponse(JSONObject jsonObject)
        {
            //数据格式是:{"response": "version", "url": "http://115.29.148.100:8080/gctzsc/gctzsc.apk","versionCode": 2}
            //此处的jsonObject就是解析出来的json对象了
            try
            {
                versionCode_fromServeice = jsonObject.getInt("versionCode");
//                Log.e("mylog", "请求到数据了 " + versionCode_fromServeice);
                handler.sendEmptyMessage(WHAT_GET_SERVER_VERSIONCODE_FINSHED);
            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Json请求失败处理器
     */
    class MyErrorListener implements Response.ErrorListener
    {

        @Override
        public void onErrorResponse(VolleyError volleyError)
        {
            Toast.makeText(StartActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
//            Log.e("mylog", "网络请求失败了");
            goMain();
        }
    }

    /**
     * 初始化动画
     */
    private void initAnim()
    {
        im_start_logo.setAlpha(1.0f);
        im_start_name.setAlpha(1.0f);
        //tv_start_font_market.setAlpha(1.0f);
        AlphaAnimation anim = null;
        for (int i = 0; i < tViews.length; i++)
        {
            anim = new AlphaAnimation(0f, 1.0f);
            anim.setStartOffset(i * 1500);// 等待1.5s之后0 1500 3000
            anim.setDuration(ANIM_DURATION);// 执行1秒
            tViews[i].startAnimation(anim);
        }
        // 设置动画监听事件
        if (anim != null)
        {
            anim.setAnimationListener(new Animation.AnimationListener()
            {
                // 动画开始的时候回调这个方法
                @Override
                public void onAnimationStart(Animation animation)
                {

                }

                @Override
                public void onAnimationRepeat(Animation animation)
                {

                }

                // 动画结束的回调这个方法
                @Override
                public void onAnimationEnd(Animation animation)
                {
//                    goMain();
                }
            });
        }
    }

    /**
     * 初始化视图
     */
    private void initView()
    {
        setContentView(R.layout.activity_start);
        im_start_logo = (ImageView) findViewById(R.id.im_start_logo);
        im_start_logo.setAlpha(0f);
        im_start_name = (ImageView) findViewById(R.id.im_start_name);
        im_start_name.setAlpha(0f);
        //pb_version = (ProgressBar) findViewById(R.id.pb_version);
        //tv_current_progress = (TextView) findViewById(R.id.tv_current_progress);
        //tv_download_speed = (TextView) findViewById(R.id.tv_download_speed);
        //tv_start_font_gc = (TextView) findViewById(R.id.tv_start_font_gc);
        //tv_start_font_gc.setAlpha(0);
        //tv_start_font_tz = (TextView) findViewById(R.id.tv_start_font_tz);
        //tv_start_font_tz.setAlpha(0);
        //tv_start_font_market = (TextView) findViewById(R.id.tv_start_font_market);
        //tv_start_font_market.setAlpha(0);
        //tViews = new TextView[]{tv_start_font_gc, tv_start_font_tz,tv_start_font_market};
        tViews = new ImageView[]{im_start_logo, im_start_name};
        //tv_start_version = (TextView) findViewById(R.id.tv_start_versionCode);
        //getCurrApkVersion();
    }

    /**
     * 获取当前应用的版本
     */
//    private void getCurrApkVersion()
//    {
//        PackageManager packageManager = getPackageManager();
//        try
//        {
//            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
//            current_versionCode = packageInfo.versionCode;
//            tv_start_version.setText("V." + current_versionCode);
//        } catch (PackageManager.NameNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//    }
}
