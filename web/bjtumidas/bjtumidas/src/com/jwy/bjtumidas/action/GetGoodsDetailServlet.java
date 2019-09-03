package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.QueryGoodsService;
import com.jwy.bjtumidas.model.CommentInfo;
import com.jwy.bjtumidas.model.Goods;
import com.jwy.bjtumidas.model.PaiGoods;
import com.jwy.bjtumidas.utils.ObjectUtils;

/**
 * 获取商品详细信息的servlet
 * 
 * @author HDL
 * 
 */
public class GetGoodsDetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String gId_str = request.getParameter("gId");
		String type = request.getParameter("type");
		int gId = 0;
		gId = Integer.parseInt(gId_str);
		PaiGoods goods = new QueryGoodsService().getGoodsDetailByGId(gId, type);
		ArrayList<PaiGoods> goodsList = new ArrayList<PaiGoods>();
		goodsList.add(goods);
		ArrayList<CommentInfo> goodsCommentList = new QueryGoodsService()
				.getGoodsCommentByGId(gId);
		String phone = new QueryGoodsService().getUserPhone(gId);
		String json = "";
		if (goods != null) {
			json = "{\"response\":\"successful\",\"phone\":\"" + phone
					+ "\",\"goods\":" + ObjectUtils.listToJson(goodsList)
					+ ",\"comment\":"
					+ ObjectUtils.listToJson(goodsCommentList) + "}";
		} else {
			json = "{\"response\":\"failed\"}";
		}
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
