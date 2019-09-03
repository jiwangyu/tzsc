package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.QueryGoodsService;
import com.jwy.bjtumidas.model.Goods;
import com.jwy.bjtumidas.model.PaiGoods;
import com.jwy.bjtumidas.model.UrgentGoods;
import com.jwy.bjtumidas.utils.ObjectUtils;

/**
 * 查询商品
 * 
 * @author HDL
 * 
 */
public class QueryGoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String json = "";
		if ("person".equals(type)) {
			String userName = request.getParameter("username");
			ArrayList<PaiGoods> goodsList = new QueryGoodsService()
					.queryGoodsInfo(userName, 0);
			json = ObjectUtils.listToJson(goodsList);
		} else if ("search".equals(type)) {
			String select = request.getParameter("select");
			select = new String(select.getBytes("UTF-8"), "UTF-8");
			ArrayList<PaiGoods> goodsList = new QueryGoodsService()
					.queryGoodsInfo(select, 1);
			json = ObjectUtils.listToJson(goodsList);
		} else if ("new".equals(type)) {
			String flag = request.getParameter("flag");
//			System.out.println("查询最新发布" + flag);
			ArrayList<Goods> goodsList = new QueryGoodsService()
					.queryNewGoodsInfo(flag);
			json = ObjectUtils.listToJson(goodsList);
		} else if ("urgent".equals(type)) {

			ArrayList<UrgentGoods> goodsList_urgent = new QueryGoodsService()
					.queryUrgentGoods();
			json = ObjectUtils.listToJson(goodsList_urgent);
//			System.out.println("查询紧急出售的商品");

		} else if ("pai".equals(type)) {
			ArrayList<PaiGoods> goodsList_pai = new QueryGoodsService()
					.queryPaiGoodsInfo();
			json = ObjectUtils.listToJson(goodsList_pai);
		} else if ("all".equals(type)) {
			String t_id = request.getParameter("position");
			ArrayList<PaiGoods> goodsList = new QueryGoodsService()
					.queryAllGoodsInfo(t_id);
			json = ObjectUtils.listToJson(goodsList);
//			System.out.println("查所有的。。。。。。。。" + goodsList.size() + "\n"
//					+ goodsList.toString());
		}
		json = "{\"response\":\"successful\",\"goodslist\":" + json + "}";
//		System.out.println(json);
		response.setCharacterEncoding("UTF-8");
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.flush();
		pw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
