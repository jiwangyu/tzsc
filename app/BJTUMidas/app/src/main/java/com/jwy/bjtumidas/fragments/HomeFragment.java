package com.jwy.bjtumidas.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jwy.bjtumidas.R;
import com.jwy.bjtumidas.activity.goods.GoodsDetailActivity;
import com.jwy.bjtumidas.activity.goods.GoodsListActivity;
import com.jwy.bjtumidas.customize.ToastUtils;
import com.jwy.bjtumidas.db.Goods;
import com.jwy.bjtumidas.db.PaiGoods;
import com.jwy.bjtumidas.db.UrgentGoods;
import com.jwy.bjtumidas.utils.ClassifyArrays;
import com.jwy.bjtumidas.utils.DateUtils;
import com.jwy.bjtumidas.utils.GoodsInfoQueryHttpUtils;
import com.jwy.bjtumidas.utils.HttpStreamUtils;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
{

    private static final int WHAT_REFRESH_FINISHED = 204;
    private static int countUpdate = 0;
    private View view_container;// 当前的页面
    private List<String> advImgUrl_list = null;
    private static final int UPDATEING = 10001;
    public static final int WHAT_GET_DATA_FINISHED = 200;
    public static final int WHAT_GET_DATA_FAILED = 201;
    public static final int WHAT_GET_URGENT_FINSHED = 202;
    public static final int WHAT_DATA_PARSE_FAILED = 203;
    private static final int WHAT_GETADVIMG_FINISHED = 1000;
    public static final int WHAT_GET_PAI_FINISHED = 205;
    private static int currentPostion = 0;
    private boolean isFirstTimer = true;
    private boolean isFirstStart = true;
    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case WHAT_GETADVIMG_FINISHED:
                    try {
                        countUpdate++;
                        advImgUrl_list = HttpStreamUtils.getAdvImgUrlToList();
                        //setViewPagerAapter();
                        if (isFirstTimer) {
                            setSlidTimer();//数据加载完毕才设置定时器
                            isFirstTimer = false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case UPDATEING:
                    //vp_main_header_adv.setCurrentItem((++currentPostion));
                    updatePointBg(currentPostion % advImgUrl_list.size());
                    break;
                case WHAT_GET_DATA_FAILED:
                    ToastUtils.showInfo(getActivity(), "网络有点问题,请重试或检查网络连接");
                    break;
                case WHAT_GET_DATA_FINISHED://获取最新发布的数据
                    countUpdate++;
                    newGoodsList = (List<Goods>) msg.obj;
                    showNewGoodsDataToView();
                    break;
                case WHAT_GET_URGENT_FINSHED://获取紧急出售的数据
                    countUpdate++;
                    urgentGoodsList = (ArrayList<UrgentGoods>) msg.obj;
                    showUrgentGoodsDataToView();
                    break;
                case WHAT_DATA_PARSE_FAILED://json解析出错了
                    ToastUtils.showInfo(getActivity(), "数据解析出错了");
                    break;
                case WHAT_REFRESH_FINISHED:
                    animation.cancel();
                    ToastUtils.showInfo(getActivity(), "刷新完毕");
                    break;
                case WHAT_GET_PAI_FINISHED://获取紧急出售的数据
                    countUpdate++;
                    paiGoodsList = (ArrayList<PaiGoods>) msg.obj;
                    showNewGoodsDataToView();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    //private ViewPager vp_main_header_adv;//头部的图片轮播
    private LinearLayout ll_home_point_container;
    private GridView gv_home_menu;
    private int menu_imgs[] = {
            R.mipmap.all_selected, R.mipmap.new1, R.mipmap.popular,  R.mipmap.urgentgoods
    };
    private int goods_imgs[] = {R.mipmap.gi1, R.mipmap.gi2, R.mipmap.gi3, R.mipmap.gi4};
    private String menu_title[] = {"全部", "最新发布", "热门",  "急售"};
    private ListView lv_home_pai;
    private GridView gv_home_goods_new;
    private ListView lv_home_urgent;
    private ImageView iv_search;
    private ImageView iv_newgoods;
    private List<Goods> newGoodsList;
    private ImageView iv_fresh;
    private ArrayList<UrgentGoods> urgentGoodsList;
    private RotateAnimation animation;
    private ArrayList<PaiGoods> paiGoodsList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        if (view_container == null) {
            view_container = inflater.inflate(R.layout.fragment_home, container, false);
        }
        ViewGroup parent = (ViewGroup) view_container.getParent();
        if (parent != null) {
            parent.removeAllViewsInLayout();
        }
        return view_container;
    }

    /**
     * 获取最新发布的商品信息
     */
    private void getNewGoodsInfo() {
        //开启线程读取数据
        new Thread() {
            @Override
            public void run() {
                GoodsInfoQueryHttpUtils.getNewGoodsInfoToList(getActivity(), getActivity().getResources().getString(R.string.url_query_goods) + "?type=new", handler);
            }
        }.start();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
//        initListener();
        //只执行一次就可以了,要更新就通过刷新按钮来更新数据哦
        if (isFirstStart) {
            initData();
            isFirstStart = false;
        }
    }

    /**
     * 显示紧急出售商品的数据到主页面
     */
    private void showUrgentGoodsDataToView() {
        for (UrgentGoods g : urgentGoodsList) {
//            Log.e("mylog", "获取到的数据为：" + g.toString());
        }
        setUrgentGoodsListViewAdapter();
    }

    /**
     * 显示拍卖商品到主页
     */
//    private void showPaiGoodsDataToView() {
//        for (PaiGoods g : paiGoodsList) {
////            Log.e("mylog", "获取到的拍卖的数据------------------：" + g.toString());
//        }
//        setPaiGoodsListAdapater();
//    }

    /**
     * 显示数据到界面上
     */
    private void showNewGoodsDataToView() {
        for (Goods g : newGoodsList) {
//            Log.e("mylog", "获取到的数据为：" + g.toString());
//            Log.e("mylog", "有多少个？" + newGoodsList.size() + "\n实际有：" + (newGoodsList.size() > 5 ? 5 : newGoodsList.size()));

        }
        setNewGoodsListAdapter();
    }

    /**
     * 设置图片轮播
     */
    public void setSlidTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(UPDATEING);
            }
        }, 100, 3500);
    }

    /**
     * 根据位置更新点的背景显示
     *
     * @param position 当前正在显示的位置
     */
    public void updatePointBg(int position) {
        // 首先要先将所有的点都设置为不选中状态
        for (int i = 0; i < advImgUrl_list.size(); i++) {
            ll_home_point_container.getChildAt(i).setBackgroundResource(R.drawable.shape_home_point_unselected);// 通过线性布局获取所有相应对应的子控件，然后将这些子控件的背景设置为不选中状态
        }
        // 最后将传递过来的位置设置为选中状态
        ll_home_point_container.getChildAt(position).setBackgroundResource(R.drawable.shape_home_point_selected);
    }

    /**
     * 初始化数据
     */
    private void initData() {
//        getImgUrlFromServies();
        updateData();
    }

    /**
     * 获取服务器的图片地址
     */
    private void getImgUrlFromServies() {
        new Thread() {
            @Override
            public void run() {
                try {
                    HttpStreamUtils.sendHttpRequest(handler,
                            WHAT_GETADVIMG_FINISHED,
                            getResources().getString(R.string.url_advImg));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        if (ll_home_point_container == null) {
           // vp_main_header_adv = (ViewPager) view_container.findViewById(R.id.vp_home_header_adv);
            //ll_home_point_container = (LinearLayout) view_container.findViewById(R.id.ll_home_point_parent);
            gv_home_menu = (GridView) view_container.findViewById(R.id.gv_home_menu);
            setMenuGridViewAdapter();
            lv_home_pai = (ListView) view_container.findViewById(R.id.lv_home_pai);
            lv_home_pai.addHeaderView(View.inflate(getActivity(), R.layout.listview_header_pai, null));
            gv_home_goods_new = (GridView) view_container.findViewById(R.id.gv_home_goods_new);
//            setNewGoodsListAdapter();
            lv_home_urgent = (ListView) view_container.findViewById(R.id.lv_home_urgent);
            //lv_home_urgent.addHeaderView(View.inflate(getActivity(), R.layout.listview_item_header_urgent, null));

            lv_home_urgent.addFooterView(View.inflate(getActivity(), R.layout.listview_item_footer_urgent, null));

            iv_search = (ImageView) view_container.findViewById(R.id.iv_home_search);
            iv_newgoods = (ImageView) view_container.findViewById(R.id.iv_newmore);
            iv_fresh = (ImageView) view_container.findViewById(R.id.iv_home_fresh);
            setListener();
        }
    }

//    private void setPaiGoodsListAdapater() {
//        lv_home_pai.setAdapter(new MyPaiListViewAdapter());
//    }

    /**
     * 设置监听器
     */
    private void setListener() {
        gv_home_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getActivity(), GoodsListActivity.class);
                it.putExtra("title", i);
                it.putExtra("type", "other");
                startActivity(it);
            }
        });
        lv_home_pai.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int gId = 0;
                if (i > 0) {
                    gId = paiGoodsList.get(i - 1).getgId();//因为已经添加头了,所以要减少1哦
                }
//                ToastUtils.showInfo(getActivity(), "点击了第" + i + "这个商品的编号为:" + gId);
                gotoPagerHeader(gId, i, "new");
            }
        });
        lv_home_urgent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int gId = 0;
                if (i > 0) {
                    gId = urgentGoodsList.get(i - 1).getgId();
                }
//                ToastUtils.showInfo(getActivity(), "点击了第" + i + "这个商品的编号为:" + gId);
                gotoPagerHeader(gId, i, "urgent");
            }
        });
        gv_home_goods_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int gId = newGoodsList.get(i).getgId();
                gotoPager(gId, "new");
            }
        });
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), GoodsListActivity.class);
                it.putExtra("type", "search");
                startActivity(it);
            }
        });
        iv_newgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), GoodsListActivity.class);
                it.putExtra("title", "最新发布");
                it.putExtra("type", "other");
                startActivity(it);
            }
        });
        animation = new RotateAnimation(0, 360f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(25);
        iv_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
                ToastUtils.showInfo(getActivity(), "正在更新数据......");
                iv_fresh.startAnimation(animation);
            }
        });

    }

    /**
     * 更新数据
     */
    private void updateData() {
        //getImgUrlFromServies();//获取轮播图片
        getNewGoodsInfo();//获取最新发布
        getUrgentGoodsInfo();//获取紧急出售的商品
        //getPaiGoodsInfo();//获取拍卖的商品
        checkedUpdated();
    }

    /**
     * 检测是否更新完毕
     */
    private void checkedUpdated() {
        new Thread() {
            @Override
            public void run() {
                try {
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (countUpdate >= 4) {
                    handler.sendEmptyMessage(WHAT_REFRESH_FINISHED);
                    countUpdate = 0;
                }
            }
        }.start();
    }

//    /**
//     * 获取拍卖商品
//     */
//    private void getPaiGoodsInfo() {
//        new Thread() {
//            @Override
//            public void run() {
//                GoodsInfoQueryHttpUtils.getPaiGoodsInfoToList(getActivity(),
//                        getActivity().getResources().getString(R.string.url_query_goods) + "?type=pai", handler);
////                Log.e("mylog", "拍卖请求地址：" + getActivity().getResources().getString(R.string.url_query_goods) + "?type=pai");
//            }
//        }.start();
//    }

    /**
     * 获取紧急出售的商品
     */
    private void getUrgentGoodsInfo() {
        new Thread() {
            @Override
            public void run() {
                GoodsInfoQueryHttpUtils.getUrgentGoodsInfoToList(getActivity(),
                        getActivity().getResources().getString(R.string.url_query_goods) + "?type=urgent", handler);
            }
        }.start();
    }

    /**
     * 跳转到相应的页面
     *
     * @param i
     */
    private void gotoPagerHeader(int gId, int i, String type) {
        Intent it = null;
        if (i == 0 || i == lv_home_pai.getCount()) {
            it = new Intent(getActivity(), GoodsListActivity.class);
            it.putExtra("title", 4);
            it.putExtra("type", "other");
        } else {
            it = new Intent(getActivity(), GoodsDetailActivity.class);
            ToastUtils.showInfo(getActivity(), "点击商品的编号:" + gId);
            it.putExtra("gId", gId);
            it.putExtra("type", type);
        }
        startActivity(it);
    }

    /**
     * 跳转到相应的页面
     *
     * @param gId
     */
    private void gotoPager(int gId, String type) {
        Intent it = new Intent(getActivity(), GoodsDetailActivity.class);
        it.putExtra("gId", gId);
//        ToastUtils.showInfo(getActivity(), "点击商品的编号:" + gId);
        it.putExtra("type", type);
        startActivity(it);
    }

    /**
     * 设置紧急出售商品的列表适配器
     */
    private void setUrgentGoodsListViewAdapter() {
        lv_home_urgent.setAdapter(new MyUrgentGoodsAdapter());
    }

    class MyUrgentGoodsAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return urgentGoodsList.size() >= 3 ? 3 : urgentGoodsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            UrgentGoods goods = urgentGoodsList.get(i);
            View item_view = View.inflate(getActivity(), R.layout.listview_item_urgent, null);
            TextView tv_classify = (TextView) item_view.findViewById(R.id.tv_home_urgent_classify);
            tv_classify.setText("【" + ClassifyArrays.titls[goods.getgClassify()] + "】");
            TextView tv_title = (TextView) item_view.findViewById(R.id.tv_home_urgent_title);
            tv_title.setText(goods.getgTitle());
            return item_view;
        }
    }

    /**
     * 设置最新商品列表的适配器
     */
    private void setNewGoodsListAdapter() {
//        Log.e("mylog", "有多少个？" + newGoodsList.size() + "\n实际有：" + (newGoodsList.size() > 5 ? 5 : newGoodsList.size()));
        gv_home_goods_new.setAdapter(new MyNewGoodsListAdapter());
    }

    class MyNewGoodsListAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return newGoodsList.size() >= 5 ? 5 : newGoodsList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View item_view = View.inflate(getActivity(), R.layout.gridview_item_main_showgoods, null);
//            Log.e("mylog", "有多少个？" + newGoodsList.size() + "\n实际有：" + (newGoodsList.size() > 5 ? 5 : newGoodsList.size()));
            Goods goods = newGoodsList.get(i);
            ImageView iv_pic = (ImageView) item_view.findViewById(R.id.iv_home_gridview_gimg);
            if (goods.getgImgUrls().size() > 0) {
                new BitmapUtils(getActivity()).display(iv_pic, goods.getgImgUrls().get(0));
            }
            TextView tv_oldPrice = (TextView) item_view.findViewById(R.id.tv_home_gridview_newgoods_oldprice);
            tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_oldPrice.setText("￥" + goods.getgOldPrice() + ".00");
            if (goods.getgOldPrice() == 0) {
                tv_oldPrice.setVisibility(View.INVISIBLE);
            }

            TextView tv_nickName = (TextView) item_view.findViewById(R.id.tv_home_gridview_nickname);
            tv_nickName.setText(goods.getuNickName());

            TextView tv_gTime = (TextView) item_view.findViewById(R.id.tv_home_gridview_gtime);
            try {
                String time = DateUtils.getTimeAfterFormat(goods.getgTime());
                tv_gTime.setText(time + "");
            } catch (ParseException e) {
                e.printStackTrace();
                ToastUtils.showInfo(getActivity(), "日期解析出问题了");
            }

            TextView tv_gPrice = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_maxprice);
            if (goods.getgPrice() == 0) {
                tv_gPrice.setText("免费");
                tv_gPrice.setTextColor(Color.RED);
                tv_oldPrice.setVisibility(View.GONE);
            } else {
                tv_gPrice.setText("￥" + goods.getgPrice() + ".00");
            }

            TextView tv_gBrowCount = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_address);
            tv_gBrowCount.setText("浏览量：" + goods.getgBrowCount());

            TextView tv_gPinkage = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gpinkage);
            tv_gPinkage.setText(goods.isPinkage() ? "包邮" : "自取");
            TextView tv_gClassify = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gclassify);
            tv_gClassify.setText(ClassifyArrays.titls[goods.getgClassify()] + "");

            TextView tv_gTitle = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gtitle);
            tv_gTitle.setText(goods.getgTitle());


            return item_view;
        }
    }

//    class MyPaiListViewAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return paiGoodsList.size() > 3 ? 3 : paiGoodsList.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View item_view = null;
//            if (view == null) {
//                item_view = View.inflate(getActivity(), R.layout.listview_item_home_goodsinfo, null);
//            } else {
//                item_view = view;
//            }
//            TextView tv_oldPrice = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_oldprice);
//
//            final PaiGoods goods = paiGoodsList.get(i);
//            LinearLayout gl_home_goodsimg = (LinearLayout) item_view.findViewById(R.id.gl_home_goodsimg);
//            gl_home_goodsimg.removeAllViews();
//            List<String> url = goods.getgImgUrls();
//            if (url.size() > 0) {
//                for (int j = 0; j < (url.size() >= 3 ? 3 : url.size()); j++) {
//                    ImageView imageView = new ImageView(getActivity());
////                    Log.e("mylog", "显示了" + j + "  长度为:" + (url.size() >= 3 ? 3 : url.size()));
//                    WindowManager windowManager = getActivity().getWindowManager();
//                    int width = windowManager.getDefaultDisplay().getWidth();
//                    int height = windowManager.getDefaultDisplay().getHeight();
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / 3, height / 4);
//                    layoutParams.setMargins(2, 0, 2, 0);
//                    imageView.setLayoutParams(layoutParams);
//
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    imageView.setFocusable(false);
//                    new BitmapUtils(getActivity()).display(imageView, url.get(j));
//                    gl_home_goodsimg.addView(imageView);
//                }
//            }
//
//            tv_oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            tv_oldPrice.setText("原价:￥" + goods.getgOldPrice() + ".00");
//            TextView tv_price = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_price);
//
//            tv_price.setText("￥" + goods.getgPrice() + ".00");
//            TextView tv_nickname = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_nickname);
//            tv_nickname.setText(goods.getuNickName());
//            TextView tv_time = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_time);
//            String time = "";
//            try {
//                time = DateUtils.getTimeAfterFormat(goods.getgTime());
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            tv_time.setText(time);
//            final TextView tv_deadline = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_deadline);
//            class MyCount extends CountDownTimer
//            {
//
//                public MyCount(long millisInFuture, long countDownInterval) {
//                    super(millisInFuture, countDownInterval);
//                }
//
//                @Override
//                public void onTick(long l) {
//                    tv_deadline.setText(DateUtils.formartTime(l));
//                }
//
//                @Override
//                public void onFinish() {
//                    tv_deadline.setText("已结束拍卖");
//                }
//            }
//            new MyCount(goods.getgDeaLine() - System.currentTimeMillis(), 60000).start();
//            TextView tv_title = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gtitle);
//            tv_title.setText(goods.getgTitle());
//            TextView tv_maxprice = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_maxprice);
//            tv_maxprice.setText("￥" + goods.getgMaxPrice() + ".00");
//            TextView tv_address = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_address);
//            tv_address.setText(goods.getgAddress());
//            TextView tv_gclassify = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gclassify);
//            tv_gclassify.setText(ClassifyArrays.titls[goods.getgClassify()] + "");
//            TextView tv_comment = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_comment);
//            tv_comment.setText(goods.getgCommentCount() + "");
//            TextView tv_nice = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_nice);
//            tv_nice.setText(goods.getgBrowCount() + "");
//            TextView tv_gPinkage = (TextView) item_view.findViewById(R.id.tv_home_listview_pai_gpinkage);
//            tv_gPinkage.setText(goods.isPinkage() ? "包邮" : "自取");
//            return item_view;
//        }
//
//
//    }

    /**
     * 设置gridview的适配器
     */
    private void setMenuGridViewAdapter() {
        gv_home_menu.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return menu_title.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View item_view = View.inflate(getActivity(), R.layout.gridview_item_home_menu, null);
                TextView tv_title = (TextView) item_view.findViewById(R.id.tv_gv_item_title);
                tv_title.setText(menu_title[i]);
                ImageView iv_menu = (ImageView) item_view.findViewById(R.id.iv_gv_item_menu);
                iv_menu.setImageResource(menu_imgs[i]);
                return item_view;
            }
        });
    }

    /**
     * 初始化监听
     */
//    private void initListener() {
//        vp_main_header_adv.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                currentPostion = position;
//                updatePointBg(currentPostion % advImgUrl_list.size());
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//
//            }
//        });
//    }

    /**
     * 设置viewapger的adapter
     */
//    public void setViewPagerAapter() {
//        vp_main_header_adv.setAdapter(new MyPagerAdapter());
//    }

    public class MyPagerAdapter extends PagerAdapter
    {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView adv_img = new ImageView(getActivity());
            adv_img.setFocusable(false);
            BitmapUtils bUtils = new BitmapUtils(getActivity());
            bUtils.display(adv_img, advImgUrl_list.get(position % advImgUrl_list.size()));//设置图片
            adv_img.setPadding(0, 0, 0, 0);//无边距
            adv_img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));//填充父窗口
            adv_img.setScaleType(ImageView.ScaleType.FIT_XY);//铺满
            container.addView(adv_img);
            return adv_img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
