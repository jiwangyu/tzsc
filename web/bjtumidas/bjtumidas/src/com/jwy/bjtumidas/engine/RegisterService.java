package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;

import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.DBHelper;

/**
 * 注册服务
 * 
 * @author HDL
 * 
 */
public class RegisterService {
	/**
	 * 判断学号或者手机号是否已经被注册了
	 * 
	 * @param username
	 * @return 是否注册
	 */
	public boolean isExisted(String uNo, String phone) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select count(u_no) from users_tb where u_no='" + uNo
					+ "' or u_phone='" + phone + "'";
			if (dbHelper.getCountSql(sql) > 0) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}

		return false;
	}

	public boolean register(Users users) {
		DBHelper dbHelper = new DBHelper();
		try {
			Long time = System.currentTimeMillis();
			byte sex = (byte) (users.isMan() ? 1 : 0);
			String sql = "insert into users_tb(u_nickname,u_no,u_phone,u_pwd,u_sex,u_time) values('"
					+ users.getuNickName()
					+ "','"
					+ users.getuNo()
					+ "','"
					+ users.getuPhone()
					+ "','"
					+ users.getuPwd()
					+ "',"
					+ sex
					+ ",'" + time + "')";
			if (dbHelper.insertSql(sql)) {
				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}

		return false;
	}

	public Users getUserInfo(String username) {
		DBHelper dbHelper = new DBHelper();
		try {
			Users users = new Users();
			String sql = "select * from users_tb where u_no='" + username
					+ "' or u_phone='" + username + "'";
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			users.setuId(resultSet.getInt("u_id"));
			users.setuNickName(resultSet.getString("u_nickName"));
			users.setuNo(resultSet.getString("u_no"));
			users.setuPhone(resultSet.getString("u_phone"));
			users.setuPwd(resultSet.getString("u_pwd"));
			users.setMan(resultSet.getInt("u_sex") == 1 ? true : false);
			users.setuTime(Long.parseLong(resultSet.getString("u_time")));
			resultSet.close();
			return users;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}
}
