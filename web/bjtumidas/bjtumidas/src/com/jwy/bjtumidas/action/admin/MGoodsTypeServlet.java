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
import com.jwy.bjtumidas.engine.RegisterService;
import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.model.GoodsType;
import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.ObjectUtils;

/**
 * 管理商品类型的servlet
 * 
 * @author HDL
 * 
 */
public class MGoodsTypeServlet extends HttpServlet {

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
			ArrayList<GoodsType> goodsTypeInfo = new QueryGoodsService()
					.queryGoodsType();
			json = "{\"page\":1,\"total\":1,\"records\":"
					+ goodsTypeInfo.size() + ",\"rows\":"
					+ ObjectUtils.listToJson(goodsTypeInfo) + "}";
		} else if ("delete".equals(type)) {
			String tId = request.getParameter("tId");
			new MangePersonalGoodsServeice().deleteGoodsType(tId);
			ArrayList<GoodsType> goodsTypeInfo = new QueryGoodsService()
					.queryGoodsType();
			json = "{\"page\":1,\"total\":1,\"records\":"
					+ goodsTypeInfo.size() + ",\"rows\":"
					+ ObjectUtils.listToJson(goodsTypeInfo) + "}";
		} else if ("add".equals(type)) {
			String tName = request.getParameter("tName");// 获取类型名称
			if (tName != null) {
				if (new QueryGoodsService().addGoodsType(tName)) {
					response.sendRedirect("mgoodstype.jsp?info=successful");
				} else {
					response.sendRedirect("mgoodstype.jsp?info=failed");
				}
			}
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
	}
}
