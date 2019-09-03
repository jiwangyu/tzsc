package com.jwy.bjtumidas.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库帮助类
 * 
 * @author HDL
 * 
 */
public class DBHelper {
	private ConnDB db = null;// 声明连接对象
	private Connection conn = null;// 获取连接
	private PreparedStatement state = null;

	/**
	 * 插入数据到表
	 * 
	 * @param sql
	 */
	public boolean insertSql(String sql) {
		try {
			db = new ConnDB();// 声明连接对象
			conn = db.getConn();
			state = conn.prepareStatement(sql);
			if (state.executeUpdate() == 1) {// 返回影响的行数,返回为0表示没有插入成功
				// System.out.println("注册成功了");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 查询数据库中的信息
	 * 
	 * @param sql
	 * @return
	 */
	public ResultSet querySql(String sql) {
		try {
			db = new ConnDB();// 声明连接对象
			conn = db.getConn();
			state = conn.prepareStatement(sql);
			return state.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 数据库的更新操作
	 * 
	 * @param sql
	 * @return
	 */
	public boolean updateSql(String sql) {
		try {
			db = new ConnDB();// 声明连接对象
			conn = db.getConn();
			state = conn.prepareStatement(sql);
			int afact = state.executeUpdate();
			if (afact >= 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 根据sql语句 获取到有多少个符合条件的对象
	 * 
	 * @param sql
	 * @return
	 */
	public int getCountSql(String sql) {
		try {
			db = new ConnDB();// 声明连接对象
			conn = db.getConn();
			state = conn.prepareStatement(sql);
			ResultSet result = state.executeQuery();
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 根据sql语句 删除相应的记录
	 * 
	 * @param sql
	 * @return
	 */
	public boolean deleteSql(String sql) {
		try {
			db = new ConnDB();// 声明连接对象
			conn = db.getConn();
			state = conn.prepareStatement(sql);
			int i = state.executeUpdate();
			return i == 1 ? true : false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 关闭数据库的相关连接
	 */
	public void closeDB() {
		try {
			if (conn != null) {
				conn.close();
			}
			if (state != null) {
				state.close();
			}
		} catch (Exception e) {
		}

	}
}
