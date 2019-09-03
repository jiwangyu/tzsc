package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.PublishService;
import com.jwy.bjtumidas.model.GoodsPublishInfo;

/**
 * 发布商品的servlet
 * 
 * @author HDL
 * 
 */
public class PublishGoodsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		System.out.println("接收到了........................");
		GoodsPublishInfo gInfo = new GoodsPublishInfo();
		String isPinkage_str = request.getParameter("isPinkage");
		Boolean isPinkage = false;
		if (isPinkage_str != null && !isPinkage.equals("")) {
			isPinkage = Boolean.parseBoolean(isPinkage_str);
		}
		gInfo.setPinkage(isPinkage);

		gInfo.setUrgent(Boolean.parseBoolean(request.getParameter("isUrgent")));
//		System.out.println("我就不信了-----------" + gInfo.isUrgent());
		String gOldPrice_str = request.getParameter("gOldPrice");
		int gOldPrice = 0;
		if (gOldPrice_str != null && !gOldPrice_str.equals("")) {
			gOldPrice = Integer.parseInt(gOldPrice_str);
		}
		gInfo.setgOldPrice(gOldPrice);
		String gDeadline = request.getParameter("gDeadline");
		gInfo.setgDeadline("" + gDeadline);

		String gDesc = request.getParameter("gDesc");
		gInfo.setgDesc(gDesc);

		String gAddress = request.getParameter("gAddress");
		gInfo.setgAddress(gAddress);

		String gClassify = request.getParameter("gClassify");
		gInfo.setgClassify(gClassify);

		String gTitle = request.getParameter("gTitle");
		gInfo.setgTitle(gTitle);

		String gPrice_str = request.getParameter("gPrice");

		String publish_type = request.getParameter("gPublishType");
		gInfo.setgPublishType(publish_type);
		int gPrice = 0;
		if (gPrice_str != null && !gPrice_str.equals("")) {
			gPrice = Integer.parseInt(gPrice_str);
		}
		gInfo.setgPrice(gPrice);

		String userName = request.getParameter("username");
//		System.out.println("用户名为：" + userName);
//		System.out.println(gInfo);
		String json = "";
		int g_id = new PublishService().publish(userName, gInfo);
		if (g_id != -1) {
			json = "{\"publish\":\"successful\",\"gId\":" + g_id + "}";
		} else {
			json = "{\"publish\":\"failed\"}";
		}
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.flush();
		pw.close();
	}

}
