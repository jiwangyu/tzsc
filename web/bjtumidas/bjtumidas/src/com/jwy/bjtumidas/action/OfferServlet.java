package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.OfferService;

/**
 * 出价的servelt
 * 
 * @author HDL
 * 
 */
public class OfferServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("username");
		String gId = request.getParameter("gid");
		String price = request.getParameter("price");
		if (new OfferService().addPrice(userName, gId, price)) {
//			System.out.println("出价成功了");
		} else {
//			System.out.println("出价失败了");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
