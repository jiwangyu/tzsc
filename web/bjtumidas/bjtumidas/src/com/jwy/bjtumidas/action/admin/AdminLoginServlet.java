package com.jwy.bjtumidas.action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jwy.bjtumidas.engine.LoginService;
import com.jwy.bjtumidas.utils.MD5Encoder;

/**
 * 管理员登陆的servelt
 * 
 * @author HDL
 * 
 */
public class AdminLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		// System.out.println(userName + "--:--" + pwd);
		if (new LoginService().adminLogin(userName,
				MD5Encoder.fiveMD5Encode(pwd))) {
			HttpSession session = request.getSession();
			session.setAttribute("user", userName);
			session.setMaxInactiveInterval(600);
			response.sendRedirect("admin/main.jsp");
		} else {
			response.sendRedirect("login.jsp?info=loginfailed");
		}
	}

}
