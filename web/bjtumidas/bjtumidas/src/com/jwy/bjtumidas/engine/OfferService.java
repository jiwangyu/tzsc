package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jwy.bjtumidas.utils.DBHelper;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * 出价的服务类
 * 
 * @author HDL
 * 
 */
public class OfferService {
	/**
	 * 增加拍卖
	 * 
	 * @param userName
	 * @param gId
	 * @param price
	 * @return
	 */
	public boolean addPrice(String userName, String gId, String price) {
		DBHelper dbHelper = new DBHelper();
		String u_id = getUId(userName);
		try {
			String sql = "insert into paiprice_tb values(null," + price + ","
					+ gId + "," + u_id + ")";
			if (dbHelper.insertSql(sql)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
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
