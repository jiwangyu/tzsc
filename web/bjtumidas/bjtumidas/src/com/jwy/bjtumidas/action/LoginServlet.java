package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.LoginService;

public class LoginServlet extends HttpServlet {

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
//		System.out.println(userName + "--------" + pwd);
		PrintWriter pw = response.getWriter();
		String json = "";
		// String md5_pwd = MD5Encoder.fiveMD5Encode(pwd);
		if (new LoginService().isLogin(userName, pwd)) {
			String nickName = new LoginService().getNickName(userName);
			json = "{\"login\":\"successful\",\"nickname\":\"" + nickName
					+ "\"}";
		} else {
			json = "{\"login\":\"failed\",\"nickname\":\"1\"}";
		}
//		System.out.println(json);
		pw.write(json);
		pw.flush();
		pw.close();
	}
}
