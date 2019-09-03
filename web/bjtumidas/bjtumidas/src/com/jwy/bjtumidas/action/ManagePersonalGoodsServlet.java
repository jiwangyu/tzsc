package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.MangePersonalGoodsServeice;

public class ManagePersonalGoodsServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String gId = request.getParameter("gid");
		String option = request.getParameter("option");
		String json = "";
		if ("delete".equals(option)) {
			if (new MangePersonalGoodsServeice().deleteGoods(gId)) {
				json = "\"response\":\"successful\"";
			} else {
				json = "\"response\":\"failed\"";
			}
		} else if ("off".equals(option)) {
			if(new MangePersonalGoodsServeice().offGoods(gId)){
				json = "\"response\":\"successful\"";
			}else{
				json = "\"response\":\"successful\"";
			}
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
