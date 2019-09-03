package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jwy.bjtumidas.utils.DBHelper;

/**
 * 评论的服务类
 * 
 * @author HDL
 * 
 */
public class CommentService {
	/**
	 * 获取卖家的电话
	 * 
	 * @param gId
	 * 
	 * @return
	 */
	public String getUserPhone(int uId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_phone from users_tb where u_id=" + uId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			String phone = resultSet.getString("u_phone");
			return phone;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 评论商品
	 * 
	 * @param userName
	 * @param content
	 * @param gId
	 * @return
	 */
	public boolean comment(String userName, String content, String gId) {
		String uId = getUId(userName);
		DBHelper dHelper = new DBHelper();
		long time = System.currentTimeMillis();
		try {
			if (content.contains("【出价】￥")) {
				content += " ( 手机：" + getUserPhone(Integer.parseInt(uId)) + " )";
			}
			String sql = "insert into comment_tb values(null,'" + content
					+ "'," + uId + "," + gId + ",'" + time + "')";
//			System.out.println("评论语句" + sql);
			if (dHelper.insertSql(sql)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {

			// TODO: handle exception
		} finally {
			dHelper.closeDB();
		}
		return false;
	}

	/**
	 * 通过用户名获取用户id
	 * 
	 * @param userName
	 */
	private static String getUId(String userName) {
		DBHelper dHelper = new DBHelper();
		try {
			String sqlId = "select u_id from users_tb where u_phone='"
					+ userName + "' or u_no='" + userName + "'";
			ResultSet resultSet = dHelper.querySql(sqlId);
			String u_id = "";
			try {
				resultSet.next();
				u_id = resultSet.getString("u_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return u_id;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dHelper.closeDB();
		}
		return "";

	}
}
