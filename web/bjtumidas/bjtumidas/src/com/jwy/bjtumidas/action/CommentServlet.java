package com.jwy.bjtumidas.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.CommentService;

/**
 * 评论的servlet类
 * 
 * @author HDL
 * 
 */
public class CommentServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String userName = request.getParameter("username");
		String content = request.getParameter("content");
		content=new String(content.getBytes("UTF-8"), "UTF-8");
		String gId = request.getParameter("gid");
		if (new CommentService().comment(userName, content, gId)) {
//			System.out.println("评论成功了");
		} else {
//			System.out.println("评论失败了");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
