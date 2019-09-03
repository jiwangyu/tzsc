package com.jwy.bjtumidas.action.admin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.jwy.bjtumidas.engine.VersionService;
import com.jwy.bjtumidas.model.VersionBean;
import com.jwy.bjtumidas.utils.UrlContoller;

/**
 * 版本控制的servlet
 * 
 * @author HDL
 * 
 */
public class VersionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		request.setAttribute("info", "query");
		if ("upload".equals(type)) {
			String vCode = "";
			String url = UrlContoller.URL_PUBLIC + "/apk/";
			// 创建DiskFileItemFactory文件项工厂对象
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 通过工厂对象获取文件上传请求核心解析类ServletFileUpload
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				// 使用ServletFileUpload对应Request对象进行解析
				List<FileItem> items = upload.parseRequest(request);
				// 遍历每个fileItem
				for (FileItem fileItem : items) {
					// 判断fileItem是否是上传项
					if (fileItem.isFormField()) {
						// 返回true:表示不是上传项
						vCode = fileItem.getString("utf-8");
					} else {
						// 返回false:表示是上传项
						String name = fileItem.getName();
						url += name;
						InputStream in = fileItem.getInputStream();

						String uploadPath = getServletContext().getRealPath(
								"/apk");
						OutputStream out = new FileOutputStream(new File(
								uploadPath, name));
						IOUtils.copy(in, out);
						out.close();
						in.close();
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			// System.out.println("版本号为：" + vCode + "\t下载地址为：" + url);
			if (new VersionService().updateVersion(vCode, url)) {
				request.setAttribute("info", "successful");
			} else {
				request.setAttribute("info", "failed");
			}
		}
		VersionBean version = new VersionService().getVersionInfo();
		request.setAttribute("vCode", version.getvCode());
		request.setAttribute("vURL", version.getDownloadURL());
		request.setAttribute("vTime", version.getTime());
		request.getRequestDispatcher("mversion.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
