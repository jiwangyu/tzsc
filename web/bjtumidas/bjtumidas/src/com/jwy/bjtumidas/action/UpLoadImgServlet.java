package com.jwy.bjtumidas.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jwy.bjtumidas.engine.PublishService;
import com.jwy.bjtumidas.engine.UpdateService;
import com.jwy.bjtumidas.utils.Base64Coder;
import com.jwy.bjtumidas.utils.DBHelper;
import com.jwy.bjtumidas.utils.UrlContoller;

public class UpLoadImgServlet extends HttpServlet {
	private String file;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// super.doPost(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		file = request.getParameter("file");
		String userName = request.getParameter("username");
		String gId = request.getParameter("gId");
		String gTime = request.getParameter("gTime");
		String g_imgPath = "";
		if (file != null) {
			byte[] b = Base64Coder.decodeLines(file);
			String filepath = request.getSession().getServletContext()
					.getRealPath("/users/" + userName);
			File dir = new File(filepath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String path = userName + "_" + System.currentTimeMillis() + ".png";
			String img_file = dir.getPath() + "/" + path;
			FileOutputStream fos = new FileOutputStream(img_file);
			fos.write(b);
			fos.flush();
			fos.close();
//			System.out.println("文件已经存放到了  /users/" + path + "\n准备存入数据库,gid="
//					+ gId);
			g_imgPath += "/users/" + userName + "/" + path;
		}
		if (new PublishService().insertImgUrl(gId, g_imgPath)) {
//			System.out.println("存放的地址为:" + g_imgPath);
//			System.out.println("上传完毕");
		}
	}
}
