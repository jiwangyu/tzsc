package com.jwy.bjtumidas.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.ChartService;

/**
 * 图标的servelt
 * 
 * @author HDL 获取 男女数量 月份 年级----->12个月 各个年级，男的数量和女的数量
 */
public class ChartServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		if ("users".equals(type)) {
			/**
			 * @格式
			 * @月份 女 男
			 * @1 20 40
			 * @2 40 50
			 */
			List data = new ChartService().getUsersData();
			int[][] charts = new int[12][];
			charts = (int[][]) data.get(0);
			int[][] grades = (int[][]) data.get(1);

//			System.out.println("年级\t女\t男");
			request.setAttribute("charts", charts);
			request.setAttribute("grades", grades);
			request.getRequestDispatcher("suserscount.jsp").forward(request,
					response);
		} else if ("goods".equals(type)) {
			List data = new ChartService().getGoodsData();
			int[][] charts = new int[12][];
			charts = (int[][]) data.get(0);
			int[][] grades = (int[][]) data.get(1);

//			System.out.println("年级\t女\t男");
			// for (int i = 0; i < grades.length; i++) {
			// for (int j = 0; j < grades[i].length; j++) {
			// System.out.print(grades[i][j] + "\t");
			// }
			// System.out.println();
			// }
			request.setAttribute("charts", charts);
			request.setAttribute("grades", grades);
			request.getRequestDispatcher("sgoodscount.jsp").forward(request,
					response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
