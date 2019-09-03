package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.utils.DBHelper;

/**
 * 增加浏览量
 * 
 * @author HDL
 * 
 */
public class BrowCountServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		DBHelper dbHelper = new DBHelper();
		String gId = request.getParameter("gid");
		try {
			String sql = "update goods_tb set g_browCount=g_browCount+1 where g_id="+gId;
			if (dbHelper.updateSql(sql)) {
//				System.out.println("浏览记录成功了");
			} else {
//				System.out.println("浏览记录失败了");
			}
		} catch (Exception e) {

		} finally {
			dbHelper.closeDB();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
