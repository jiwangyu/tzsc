package com.jwy.bjtumidas.action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.UpdateService;
import com.jwy.bjtumidas.utils.MD5Encoder;

/**
 * 修改管理员密码
 * 
 * @author HDL
 * 
 */
public class ModifyAdminPwdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String rePwd = request.getParameter("rePwd");
		if (new UpdateService().isAgreement(MD5Encoder.fiveMD5Encode(oldPwd))) {
			if (newPwd.equals(rePwd)) {
				if (new UpdateService().updateAdminPwd(MD5Encoder
						.fiveMD5Encode(newPwd))) {
					response.sendRedirect("../login.jsp?info=successful");
				} else {
					response.sendRedirect("../login.jsp?info=failed");
				}
			} else {
				response.sendRedirect("../login.jsp?info=error");
			}
		} else {
			response.sendRedirect("../login.jsp?info=unagreement");
		}
	}

}
