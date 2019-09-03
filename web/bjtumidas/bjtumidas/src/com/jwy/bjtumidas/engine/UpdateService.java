package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;

import com.jwy.bjtumidas.utils.DBHelper;
import com.sun.org.apache.bcel.internal.generic.DADD;

/**
 * 修改服务
 * 
 * @author HDL
 * 
 */
public class UpdateService {
	/**
	 * 判断密码是否修改成功了
	 * 
	 * @param phone
	 * @param pwd
	 * @return
	 */
	public boolean isSuccessfulUpdatePwd(String phone, String pwd) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "update users_tb set u_pwd='" + pwd
					+ "' where u_phone='" + phone + "'";
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

	/**
	 * 判断是否修改昵称成功了
	 * 
	 * @param username
	 * @param newNickName
	 * @return
	 */
	public boolean isUpdated(String username, String newNickName) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "update users_tb set u_nickName='" + newNickName
					+ "' where u_phone='" + username + "' or u_no='" + username
					+ "'";
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

	/**
	 * 判断原密码是否正确
	 * 
	 * @param oldPwd
	 * @return
	 */
	public boolean isAgreement(String oldPwd) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select a_pwd from admin_tb";
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			String pwd = resultSet.getString("a_pwd");
			if (pwd.equals(oldPwd)) {
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
	 * 修改管理员密码
	 * 
	 * @param fiveMD5Encode
	 * @return
	 */
	public boolean updateAdminPwd(String pwd) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "update admin_tb set a_pwd='" + pwd + "'";
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}
}
