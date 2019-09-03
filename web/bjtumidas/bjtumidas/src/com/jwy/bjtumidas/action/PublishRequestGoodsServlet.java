package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.PublishService;
import com.jwy.bjtumidas.model.RequestGoodsInfo;

public class PublishRequestGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String rTitle = request.getParameter("rTitle");
		String rContent = request.getParameter("rContent");
		RequestGoodsInfo gInfo = new RequestGoodsInfo(rTitle, rContent);
//		System.out.println("username="+username+"----"+gInfo);
		String json = "";
		if (new PublishService().publishReq(username, gInfo)) {
			json = "{\"publishreq\":\"successful\"}";
		} else {
			json = "{\"publishreq\":\"failed\"}";
		}
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.flush();
		pw.close();
	}

}
