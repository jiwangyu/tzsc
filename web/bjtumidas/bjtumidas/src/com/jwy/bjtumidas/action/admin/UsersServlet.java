package com.jwy.bjtumidas.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.RegisterService;
import com.jwy.bjtumidas.engine.UsersService;
import com.jwy.bjtumidas.model.AdminUsers;
import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.ObjectUtils;

/**
 * 管理员管理用户的servlet
 * 
 * @author HDL
 * 
 */
public class UsersServlet extends HttpServlet {

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
			ArrayList<AdminUsers> usersInfo = ObjectUtils
					.orderUsers(new UsersService().getUsersInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + usersInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(usersInfo) + "}";
		} else if ("querywhere".equals(type)) {
			String select = request.getParameter("select");
			ArrayList<AdminUsers> usersInfo = null;
			if (select != null && !"".equals(select)) {
				select = new String(select.getBytes("UTF-8"), "UTF-8");
				usersInfo = ObjectUtils.orderUsers(new UsersService()
						.getUsersInfo(select));
			} else {
				usersInfo = new UsersService().getUsersInfo();
			}
			json = "{\"page\":1,\"total\":1,\"records\":" + usersInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(usersInfo) + "}";
		} else if ("add".equals(type)) {
			String uNo = request.getParameter("uNo");// 获取学号
			String uPhone = request.getParameter("uPhone");// 获取姓名
			String uSex = request.getParameter("uSex");// 性别

			try {
				if (uNo != null && uPhone != null && uSex != null) {
					RegisterService rService = new RegisterService();
					if (rService.isExisted(uNo, uPhone)) {
						response.sendRedirect("musers.jsp?info=existed");
					} else {
						// 添加用户,20122205 011035
						Users users = new Users(0, uNo, uPhone, uPhone,
								MD5Encoder.fiveMD5Encode(uNo.substring(7)),
								"1".equals(uSex), System.currentTimeMillis());
						if (rService.register(users)) {
							response.sendRedirect("musers.jsp?info1=successful");
						} else {
							response.sendRedirect("musers.jsp?info1=failed");
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("delete".equals(type)) {
			String uId = request.getParameter("uId");
			new UsersService().deleteUsers(uId);
			ArrayList<AdminUsers> usersInfo = ObjectUtils
					.orderUsers(new UsersService().getUsersInfo());
			json = "{\"page\":1,\"total\":1,\"records\":" + usersInfo.size()
					+ ",\"rows\":" + ObjectUtils.listToJson(usersInfo) + "}";
		}
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.close();
		// System.out.println(json);
	}

}
