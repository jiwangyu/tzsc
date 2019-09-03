package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.RegisterService;
import com.jwy.bjtumidas.engine.UpdateService;
import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.ObjectUtils;

public class UsersInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		String username = request.getParameter("username");
		String json = null;
		if ("get".equals(type)) {
			Users users = new RegisterService().getUserInfo(username);
			json = ObjectUtils.objToJson(users);
		} else if ("update".equals(type)) {
			String newNickName = request.getParameter("newnickname");
			newNickName = new String(newNickName.getBytes("UTF-8"),
					"UTF-8");
			if (new UpdateService().isUpdated(username, newNickName)) {
				json = "{\"response\":\"successful\"}";
			}
		}
//		System.out.println("---------------------json数据格式为:" + json);
		if (json != null && json.length() > 10) {
			if ("get".equals(type)) {
				json = "{\"response\":\"successful\",\"userinfo\":" + json
						+ "}";
			}
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
