package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import com.jwy.bjtumidas.model.AdminUsers;
import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.DBHelper;
import com.jwy.bjtumidas.utils.DateUtils;

public class UsersService {
	/**
	 * 查询用户信息
	 * 
	 * @param gId
	 * @return
	 */
	public ArrayList<AdminUsers> getUsersInfo() {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select * from users_tb";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminUsers> user_list = new ArrayList<AdminUsers>();
			while (resultSet.next()) {
				AdminUsers users = new AdminUsers();
				users.setuId(resultSet.getInt("u_id"));
				users.setuNo(resultSet.getString("u_no"));
				users.setuGrade(users.getuNo().substring(0, 2) + "级");
				users.setuSex(resultSet.getByte("u_sex") == 1 ? "男" : "女");
				users.setuNickName(resultSet.getString("u_nickname"));
				users.setuPhone(resultSet.getString("u_phone"));
				try {
					users.setuTime(DateUtils.getTimeAfterFormat(resultSet
							.getLong("u_time")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user_list.add(users);
			}
			resultSet.close();
			return user_list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 按条件查询用户
	 * 
	 * @param select
	 * @return
	 */
	public ArrayList<AdminUsers> getUsersInfo(String select) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select * from users_tb where u_nickname like '%"
					+ select + "%' or u_no like '%" + select
					+ "%' or u_phone like '%" + select + "%'";
//			System.out.println("查询语句为：" + sql);
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminUsers> user_list = new ArrayList<AdminUsers>();
			while (resultSet.next()) {
				AdminUsers users = new AdminUsers();
				users.setuId(resultSet.getInt("u_id"));
				users.setuNo(resultSet.getString("u_no"));
				users.setuGrade(users.getuNo().substring(0, 2) + "级");
				users.setuSex(resultSet.getByte("u_sex") == 1 ? "男" : "女");
				users.setuNickName(resultSet.getString("u_nickname"));
				users.setuPhone(resultSet.getString("u_phone"));
				try {
					users.setuTime(DateUtils.getTimeAfterFormat(resultSet
							.getLong("u_time")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				user_list.add(users);
			}
			resultSet.close();
			return user_list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 删除用户
	 * 
	 * @param uId
	 * @return
	 */
	public boolean deleteUsers(String uId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "delete from users_tb where u_id=" + uId;
			return dbHelper.deleteSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}
}
