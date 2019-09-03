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
import com.jwy.bjtumidas.model.AdminComment;
import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.utils.ObjectUtils;

public class MCommentServlet extends HttpServlet {

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
			ArrayList<AdminComment> goodsInfo = ObjectUtils
					.orderComment(new QueryComment().queryCommentInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		} else if ("querywhere".equals(type)) {
			String select = request.getParameter("select");
			select = new String(select.getBytes("ISO-8859-1"), "UTF-8");
			ArrayList<AdminComment> goodsInfo = ObjectUtils
					.orderComment(new QueryComment().queryCommentInfo(select));
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		} else if ("delete".equals(type)) {
			String cId = request.getParameter("cId");
			new MangePersonalGoodsServeice().deleteComment(cId);
			ArrayList<AdminComment> goodsInfo = ObjectUtils
					.orderComment(new QueryComment().queryCommentInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + goodsInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(goodsInfo) + "}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
		// System.out.println("查询商品信息"+json);
	}

}
