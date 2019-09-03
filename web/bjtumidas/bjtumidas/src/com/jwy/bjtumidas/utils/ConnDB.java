package com.jwy.bjtumidas.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnDB {
	private Connection conn = null;

	public Connection getConn() {
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 获取链接
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/tzsc_db", "root", "a1b2c3");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
