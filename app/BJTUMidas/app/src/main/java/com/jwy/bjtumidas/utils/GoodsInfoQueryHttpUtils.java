package com.jwy.bjtumidas.utils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jwy.bjtumidas.db.CommentInfo;
import com.jwy.bjtumidas.db.Goods;
import com.jwy.bjtumidas.db.PaiGoods;
import com.jwy.bjtumidas.db.UrgentGoods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询商品信息的http工具
 * Created by HDL on 2016/4/22.
 */
public class GoodsInfoQueryHttpUtils
{
    public static int WHAT_GET_DATA_FINISHED = 200;
    public static int WHAT_GET_DATA_FAILED = 201;
    public static int WHAT_GET_URGENT_FINSHED = 202;
    public static int WHAT_DATA_PARSE_FAILED = 203;
    public static int WHAT_GET_PAI_FINISHED = 205;
    public static int WHAT_GET_GOODS_DETIAL_FINISHED = 206;
    private static final int WHAT_GOODS_UNEXISTED = 207;

    /**
     * 获取最新发布的商品的信息变为list集合
     *
     * @param context
     * @param url
     * @param handler
     */
    public static void getGoodsInfoToList(Context context, String url, Handler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new PaiGoodsJsonObjectListener(handler), new MyErrorListener(handler));
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    /**
     * 获取最新发布的商品的信息变为list集合
     *
     * @param context
     * @param url
     * @param handler
     */
    public static void getNewGoodsInfoToList(Context context, String url, Handler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new NewGoodsJsonObjectListener(handler), new MyErrorListener(handler));
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    /**
     * 获取最新发布的商品的信息变为list集合
     *
     * @param context
     * @param url
     * @param handler
     */
    public static void getGoodsDetailToList(Context context, String url, Handler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new GoodsDetialJsonObjectListener(handler), new MyErrorListener(handler));
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }


    /**
     * 获取紧急出售的商品的信息变为list集合
     *
     * @param context
     * @param url
     * @param handler
     */
    public static void getUrgentGoodsInfoToList(Context context, String url, Handler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new UrgentGoodsJsonObjectListener(handler), new MyErrorListener(handler));
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    /**
     * 获取拍卖的商品的信息变为list集合
     *
     * @param context
     * @param url
     * @param handler
     */
    public static void getPaiGoodsInfoToList(Context context, String url, Handler handler) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new PaiGoodsJsonObjectListener(handler), new MyErrorListener(handler));
        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }

    /**
     * 解析出错的回调
     */
    static class MyErrorListener implements Response.ErrorListener {
        Handler handler;

        public MyErrorListener(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {
            handler.sendEmptyMessage(WHAT_GET_DATA_FAILED);
        }
    }

    /**
     * 商品详细信息成功回调类
     */

    static class GoodsDetialJsonObjectListener implements Response.Listener<JSONObject> {

        Handler handler;

        public GoodsDetialJsonObjectListener(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                String response = jsonObject.getString("response");
                if ("successful".equals(response)) {
                    String phone = jsonObject.getString("phone");
                    JSONArray gList_jArray = jsonObject.getJSONArray("goods");
                    PaiGoods goods = new PaiGoods();
                    JSONObject goods_jObject = (JSONObject) gList_jArray.get(0);
                    int gId = goods_jObject.getInt("gId");
                    goods.setgId(gId);
                    int uId = goods_jObject.getInt("uId");
                    goods.setuId(uId);
                    int gPrice = goods_jObject.getInt("gPrice");
                    goods.setgPrice(gPrice);
                    int gOldPrice = goods_jObject.getInt("gOldPrice");
                    goods.setgOldPrice(gOldPrice);
                    long gDeadline = goods_jObject.getLong("gDeaLine");
                    goods.setgDeaLine(gDeadline);

                    JSONArray gImgUrls_arr = goods_jObject.getJSONArray("gImgUrls");
                    List<String> pUrl_list = new ArrayList<String>();
                    for (int j = 0; j < gImgUrls_arr.length(); j++) {
                        pUrl_list.add(gImgUrls_arr.get(j).toString());
                    }
                    goods.setgImgUrls(pUrl_list);

                    int gBrowCount = goods_jObject.getInt("gBrowCount");
                    goods.setgBrowCount((byte) gBrowCount);
                    int gNice = goods_jObject.getInt("gNice");
                    goods.setgNice((byte) gNice);
                    long gTime = goods_jObject.getLong("gTime");
                    goods.setgTime(gTime);
                    String gTitle = goods_jObject.getString("gTitle");
                    goods.setgTitle(gTitle);
                    goods.setgDesc(goods_jObject.getString("gDesc"));
                    int gPublishType = goods_jObject.getInt("gPublishType");
                    goods.setgPublishType((byte) gPublishType);
                    String uNickName = goods_jObject.getString("uNickName");
                    goods.setuNickName(uNickName);
                    int t_id = goods_jObject.getInt("gClassify");
                    goods.setgClassify(t_id);
                    boolean gIsSaled = goods_jObject.getBoolean("gIsSaled");
                    goods.setgIsSaled(gIsSaled);
                    boolean isPinkage = goods_jObject.getBoolean("isPinkage");
                    goods.setPinkage(isPinkage);
                    boolean isUrgent = goods_jObject.getBoolean("isUrgent");
                    goods.setUrgent(isUrgent);
                    String gAddress = goods_jObject.getString("gAddress");
                    goods.setgAddress(gAddress);
                    goods.setgCommentCount(goods_jObject.getInt("gCommentCount"));
                    goods.setgMaxPrice(goods_jObject.getInt("gMaxPrice"));

//                    [{"cId":1,"cContent":"价格太贵了,能便宜点不","uId":12,"gId":38,"cTime":1342515123552452}]
                    //解析评论
                    JSONArray comment_jArray = jsonObject.getJSONArray("comment");
                    ArrayList<CommentInfo> cList = new ArrayList<CommentInfo>();
                    for (int i = 0; i < comment_jArray.length(); i++) {
                        CommentInfo cInfo = new CommentInfo();
                        JSONObject cItem = (JSONObject) comment_jArray.get(i);
                        cInfo.setcId(cItem.getInt("cId"));
                        cInfo.setcContent(cItem.getString("cContent"));
                        cInfo.setcTime(cItem.getLong("cTime"));
                        cInfo.setuNickName(cItem.getString("uNickName"));
                        cList.add(cInfo);
                    }
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("commentList", cList);
                    bundle.putString("phone", phone);
                    msg.setData(bundle);
                    msg.obj = goods;

                    msg.what = WHAT_GET_GOODS_DETIAL_FINISHED;
                    handler.sendMessage(msg);
                } else {
                    handler.sendEmptyMessage(WHAT_GOODS_UNEXISTED);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(WHAT_DATA_PARSE_FAILED);
            }
        }
    }

    /**
     * 拍卖的商品成功回调类
     */

    static class PaiGoodsJsonObjectListener implements Response.Listener<JSONObject> {
        Handler handler;

        public PaiGoodsJsonObjectListener(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                JSONArray gList_jArray = jsonObject.getJSONArray("goodslist");
                List<PaiGoods> goodsList = new ArrayList<PaiGoods>();
                for (int i = 0; i < gList_jArray.length(); i++) {
                    PaiGoods goods = new PaiGoods();
                    JSONObject goods_jObject = (JSONObject) gList_jArray.get(i);
                    int gId = goods_jObject.getInt("gId");
                    goods.setgId(gId);
                    int uId = goods_jObject.getInt("uId");
                    goods.setuId(uId);
                    int gPrice = goods_jObject.getInt("gPrice");
                    goods.setgPrice(gPrice);
                    int gOldPrice = goods_jObject.getInt("gOldPrice");
                    goods.setgOldPrice(gOldPrice);
                    long gDeadline = goods_jObject.getLong("gDeaLine");
                    goods.setgDeaLine(gDeadline);
                    int gBrowCount = goods_jObject.getInt("gBrowCount");
                    goods.setgBrowCount((byte) gBrowCount);
                    int gNice = goods_jObject.getInt("gNice");
                    goods.setgNice((byte) gNice);
                    long gTime = goods_jObject.getLong("gTime");
                    goods.setgTime(gTime);
                    JSONArray gImgUrls_arr = goods_jObject.getJSONArray("gImgUrls");
                    List<String> pUrl_list = new ArrayList<String>();
                    for (int j = 0; j < gImgUrls_arr.length(); j++) {
                        pUrl_list.add(gImgUrls_arr.get(j).toString());
                    }
                    goods.setgImgUrls(pUrl_list);
                    String gTitle = goods_jObject.getString("gTitle");
                    goods.setgTitle(gTitle);
                    int gPublishType = goods_jObject.getInt("gPublishType");
                    goods.setgPublishType((byte) gPublishType);
                    String uNickName = goods_jObject.getString("uNickName");
                    goods.setuNickName(uNickName);
                    int t_id = goods_jObject.getInt("gClassify");
                    goods.setgClassify(t_id);
                    boolean gIsSaled = goods_jObject.getBoolean("gIsSaled");
                    goods.setgIsSaled(gIsSaled);
                    boolean isPinkage = goods_jObject.getBoolean("isPinkage");
                    goods.setPinkage(isPinkage);
                    boolean isUrgent = goods_jObject.getBoolean("isUrgent");
                    goods.setUrgent(isUrgent);
                    String gAddress = goods_jObject.getString("gAddress");
                    goods.setgAddress(gAddress);
                    goods.setgDesc(goods_jObject.getString("gDesc") + "");
                    goods.setgCommentCount(goods_jObject.getInt("gCommentCount"));
                    goods.setgMaxPrice(goods_jObject.getInt("gMaxPrice"));
                    goodsList.add(goods);
                }
                Message msg = handler.obtainMessage();
                msg.obj = goodsList;
                for (PaiGoods goods : goodsList) {
//                    Log.e("mylog", "到底解析到没有" + goods.toString());
                }
                msg.what = WHAT_GET_PAI_FINISHED;
                handler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(WHAT_DATA_PARSE_FAILED);
            }
        }
    }

    /**
     * 紧急出售商品回调类
     */
    static class UrgentGoodsJsonObjectListener implements Response.Listener<JSONObject> {
        Handler handler;

        public UrgentGoodsJsonObjectListener(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("goodslist");
                ArrayList<UrgentGoods> gList = new ArrayList<UrgentGoods>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    UrgentGoods goods = new UrgentGoods();
                    JSONObject goosList_jObject = (JSONObject) jsonArray.get(i);
                    int uId = goosList_jObject.getInt("uId");
                    goods.setuId(uId);
                    int gId = goosList_jObject.getInt("gId");
                    goods.setgId(gId);
                    String gTitle = goosList_jObject.getString("gTitle");
                    goods.setgTitle("" + gTitle);
                    int gClassify = goosList_jObject.getInt("gClassify");
                    goods.setgClassify(gClassify);
                    gList.add(goods);
                }
                Message msg = handler.obtainMessage();
                msg.what = WHAT_GET_URGENT_FINSHED;
                msg.obj = gList;
                handler.sendMessage(msg);
            } catch (JSONException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(WHAT_DATA_PARSE_FAILED);
            }
        }
    }

    /**
     * 最新发布商品回调类
     */
    static class NewGoodsJsonObjectListener implements Response.Listener<JSONObject> {
        Handler handler;

        public NewGoodsJsonObjectListener(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onResponse(JSONObject jsonObject) {
            try {
                JSONArray gList_jArray = jsonObject.getJSONArray("goodslist");
                List<Goods> goodsList = new ArrayList<Goods>();
                for (int i = 0; i < gList_jArray.length(); i++) {
                    Goods goods = new Goods();
                    JSONObject goods_jObject = (JSONObject) gList_jArray.get(i);

                    //解析图片地址:"gImgUrls":["/users/15519099921/15519099921_1461554165956.png","/users/15519099921/15519099921_1461554166088.png","/users/15519099921/15519099921_1461554166215.png","/users/15519099921/15519099921_1461554166328.png"],
                    JSONArray gImgUrls_arr = goods_jObject.getJSONArray("gImgUrls");
                    List<String> pUrl_list = new ArrayList<String>();
                    for (int j = 0; j < gImgUrls_arr.length(); j++) {
                        pUrl_list.add(gImgUrls_arr.get(j).toString());
                    }
                    goods.setgImgUrls(pUrl_list);
                    int gId = goods_jObject.getInt("gId");
                    goods.setgId(gId);
                    int uId = goods_jObject.getInt("uId");
                    goods.setuId(uId);
                    int gPrice = goods_jObject.getInt("gPrice");
                    goods.setgPrice(gPrice);
                    int gOldPrice = goods_jObject.getInt("gOldPrice");
                    goods.setgOldPrice(gOldPrice);
                    int gBrowCount = goods_jObject.getInt("gBrowCount");
                    goods.setgBrowCount((byte) gBrowCount);
                    int gNice = goods_jObject.getInt("gNice");
                    goods.setgNice((byte) gNice);
                    long gTime = goods_jObject.getLong("gTime");
                    goods.setgTime(gTime);
                    String gTitle = goods_jObject.getString("gTitle");
                    goods.setgTitle(gTitle);
                    int t_id = goods_jObject.getInt("gClassify");
                    goods.setgClassify(t_id);
                    int gPublishType = goods_jObject.getInt("gPublishType");
                    goods.setgPublishType((byte) gPublishType);
                    String uNickName = goods_jObject.getString("uNickName");
                    goods.setuNickName(uNickName);
                    boolean gIsSaled = goods_jObject.getBoolean("gIsSaled");
                    goods.setgIsSaled(gIsSaled);
                    boolean isPinkage = goods_jObject.getBoolean("isPinkage");
                    goods.setPinkage(isPinkage);
                    boolean isUrgent = goods_jObject.getBoolean("isUrgent");
                    goods.setUrgent(isUrgent);
                    goodsList.add(goods);
                }
                Message msg = handler.obtainMessage();
                msg.obj = goodsList;
                msg.what = WHAT_GET_DATA_FINISHED;
                handler.sendMessage(msg);

            } catch (JSONException e) {
                e.printStackTrace();
                handler.sendEmptyMessage(WHAT_DATA_PARSE_FAILED);
            }
        }
    }
}
