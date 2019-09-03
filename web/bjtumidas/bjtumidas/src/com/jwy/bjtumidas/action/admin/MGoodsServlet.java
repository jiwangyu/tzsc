package com.jwy.bjtumidas.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.MangePersonalGoodsServeice;
import com.jwy.bjtumidas.engine.QueryGoodsService;
import com.jwy.bjtumidas.engine.UsersService;
import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.model.AdminUsers;
import com.jwy.bjtumidas.utils.ObjectUtils;

/**
 * 管理商品的servlet
 * 
 * @author HDL
 * 
 */
public class MGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String json = "";
		if ("query".equals(type)) {
			ArrayList<AdminGoods> goodsInfo = ObjectUtils
					.orderGoods(new QueryGoodsService().queryAllGoodsInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		} else if ("querywhere".equals(type)) {
			String select = request.getParameter("select");
			select = new String(select.getBytes("ISO-8859-1"), "UTF-8");
			ArrayList<AdminGoods> goodsInfo = ObjectUtils
					.orderGoods(new QueryGoodsService()
							.queryGoodsInfoBySelect(select));
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		} else if ("delete".equals(type)) {
			String gId = request.getParameter("gId");
			new MangePersonalGoodsServeice().deleteGoods(gId);
			ArrayList<AdminGoods> goodsInfo = ObjectUtils
					.orderGoods(new QueryGoodsService().queryAllGoodsInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
		// System.out.println("查询商品信息"+json);
	}

}
