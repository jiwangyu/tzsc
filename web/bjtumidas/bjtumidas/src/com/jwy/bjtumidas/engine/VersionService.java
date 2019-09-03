package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import com.jwy.bjtumidas.model.AdminUsers;
import com.jwy.bjtumidas.model.VersionBean;
import com.jwy.bjtumidas.utils.DBHelper;
import com.jwy.bjtumidas.utils.DateUtils;

public class VersionService {

	public VersionBean getVersionInfo() {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select * from version_tb order by v_time desc limit 0,1";
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			VersionBean veresion = new VersionBean();
			veresion.setvCode(resultSet.getInt("v_code"));
			veresion.setDownloadURL(resultSet.getString("v_apkUrl"));
			String time = "";
			try {
				time = DateUtils.getTimeAfterFormat(Long.parseLong(resultSet
						.getString("v_time")));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			veresion.setTime(time);
			return veresion;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 更新版本
	 * 
	 * @param vCode
	 * @param url
	 * @return
	 */
	public boolean updateVersion(String vCode, String url) {
		int v_code = Integer.parseInt(vCode);
		if (v_code > getCurrentVCode()) {

			DBHelper dbHelper = new DBHelper();
			try {
				String sql = "insert into version_tb values(null," + vCode
						+ ",'" + url + "','" + System.currentTimeMillis()
						+ "')";
				if (dbHelper.insertSql(sql)) {
					return true;
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				dbHelper.closeDB();
			}
		}
		return false;
	}

	/**
	 * 获取档当前的版本号
	 * 
	 * @return
	 */
	private int getCurrentVCode() {
		return getVersionInfo().getvCode();
	}

}
