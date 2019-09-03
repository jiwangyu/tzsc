package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;

import com.jwy.bjtumidas.utils.DBHelper;

/**
 * 用户登录处理类
 * 
 * @author HDL
 * 
 */
public class LoginService {
	/**
	 * 判断是否登录成功了
	 * 
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public boolean isLogin(String userName, String pwd) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select count(u_no) from users_tb where (u_no='"
					+ userName + "' or u_phone='" + userName + "') and u_pwd='"
					+ pwd + "'";
			if (dbHelper.getCountSql(sql) == 1) {
				return true;
			}
		} catch (Exception e) {

		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

	/**
	 * 通过用户名获取昵称
	 * 
	 * @param userName
	 * @return
	 */
	public String getNickName(String userName) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_nickName from users_tb where u_phone='"
					+ userName + "' or u_no='" + userName + "'";
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			String u_nickName = resultSet.getString("u_nickName");
			return u_nickName;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 管理员登陆
	 * 
	 * @param userName
	 * @param pwd
	 * @return
	 */
	public boolean adminLogin(String userName, String pwd) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select count(a_id) from admin_tb where a_name='"
					+ userName + "' and a_pwd='" + pwd + "'";
			if (dbHelper.getCountSql(sql) == 1) {
				return true;
			}
		} catch (Exception e) {

		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

}
