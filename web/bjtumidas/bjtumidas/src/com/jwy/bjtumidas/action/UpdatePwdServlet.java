package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.UpdateService;

public class UpdatePwdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("username");
		String pwd=request.getParameter("pwd");
		String json="";
		if(new UpdateService().isSuccessfulUpdatePwd(userName, pwd)){
			json="{\"update_pwd\":\"successful\"}";
//			System.out.println("修改成功了");
		}else{
			json="{\"update_pwd\":\"failed\"}";
//			System.out.println("修改失败了");
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
