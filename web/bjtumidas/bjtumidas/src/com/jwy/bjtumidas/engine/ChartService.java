package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jwy.bjtumidas.utils.DBHelper;

public class ChartService {
	/**
	 * 获取用户数据
	 * 
	 * @return
	 */
	public List getUsersData() {
		List list = new ArrayList();
		int[][] charts = new int[12][];// 用于存放12个月中男女个发表的商品的件数
		for (int i = 0; i < charts.length; i++) {
			charts[i] = new int[3];
			charts[i][0] = (i + 1);
		}
		int[][] grades = new int[4][];
		for (int i = 0; i < grades.length; i++) {
			grades[i] = new int[3];
			grades[i][0] = i;
		}
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select * from users_tb";
			ResultSet resultSet = dbHelper.querySql(sql);
			while (resultSet.next()) {
				long gTime = resultSet.getLong("u_time");
				int month = 1;
				try {
					month = getMonth(gTime);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String grage_str = (String) (resultSet.getString("u_no")
						.subSequence(0, 2));
				int grade = 0;
				if ("15".equals(grage_str)) {
					grade = 0;
				} else if ("16".equals(grage_str)) {
					grade = 1;
				} else if ("17".equals(grage_str)) {
					grade = 2;
				} else if ("18".equals(grage_str)) {
					grade = 3;
				}
				int sex = resultSet.getByte("u_sex");
				grades[grade][sex + 1] += 1;
				charts[month - 1][sex + 1] += +1;
			}
			list.add(charts);
			list.add(grades);
			resultSet.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 获取商品数据
	 * 
	 * @return
	 */
	public List getGoodsData() {
		List list = new ArrayList();
		int[][] charts = new int[12][];// 用于存放12个月中男女个发表的商品的件数
		for (int i = 0; i < charts.length; i++) {
			charts[i] = new int[3];
			charts[i][0] = (i + 1);
		}
		int[][] grades = new int[4][];
		for (int i = 0; i < grades.length; i++) {
			grades[i] = new int[3];
			grades[i][0] = i;
		}
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select * from goods_tb";
			ResultSet resultSet = dbHelper.querySql(sql);
			while (resultSet.next()) {
				long gTime = resultSet.getLong("g_time");
				int month = 1;
				try {
					month = getMonth(gTime);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				int uId = resultSet.getInt("u_id");
				String grage_str = (String) (getUserGrage(uId)
						.subSequence(0, 2));
				int grade = 0;
				if ("15".equals(grage_str)) {
					grade = 0;
				} else if ("16".equals(grage_str)) {
					grade = 1;
				} else if ("17".equals(grage_str)) {
					grade = 2;
				} else if ("18".equals(grage_str)) {
					grade = 3;
				}
				int sex = getUserSex(uId);
				grades[grade][sex + 1] += 1;
				charts[month - 1][sex + 1] += +1;
//				System.out.println("charts[" + (month - 1) + "][" + sex + "]="
//						+ charts[month - 1][sex]);
			}
			list.add(charts);
			list.add(grades);
			resultSet.close();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 根据用户编号获取性别
	 * 
	 * @param gId
	 * 
	 * @return
	 */
	public int getUserSex(int uId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_sex from users_tb where u_id=" + uId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			return resultSet.getByte("u_sex");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return -1;
	}

	/**
	 * 根据用户编号获取年级
	 * 
	 * @param gId
	 * 
	 * @return
	 */
	public String getUserGrage(int uId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_no from users_tb where u_id=" + uId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			return resultSet.getString("u_no");
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 根据毫秒值获取月份
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static int getMonth(long time) throws ParseException {
		return Integer.parseInt(new SimpleDateFormat("MM")
				.format(new Date(time)));
	}
}
