package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.RegisterService;
import com.jwy.bjtumidas.model.Users;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		Users users = new Users();

		String uNo = request.getParameter("sNo");
		users.setuNo(uNo);
		String pwd = request.getParameter("pwd");
		users.setuPwd(pwd);
		String nickName = request.getParameter("nickName");
		users.setuNickName(nickName);
		String str_sex = request.getParameter("sex");
		Boolean isMan = Boolean.parseBoolean(str_sex);
		users.setMan(isMan);
		String phone = request.getParameter("phone");
		users.setuPhone(phone);
//		System.out.println("注册信息   --- " + uNo + " - " + pwd + " - " + nickName
//				+ " - " + isMan + " - " + phone);
		String json = "";
		if (new RegisterService().isExisted(uNo, phone)) {// 判断是否已经被注册了
			json = "{\"register\":\"existed\"}";
		} else {
			// 没有被注册的话就立即注册
			if (new RegisterService().register(users)) {
				json = "{\"register\":\"successful\"}";
			} else {
				json = "{\"register\":\"failed\"}";
			}
		}
		PrintWriter pw = response.getWriter();
		pw.write(json);
		pw.flush();
		pw.close();
	}
}
