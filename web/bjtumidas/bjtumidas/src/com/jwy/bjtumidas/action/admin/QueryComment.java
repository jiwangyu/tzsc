package com.jwy.bjtumidas.action.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import com.jwy.bjtumidas.model.AdminComment;
import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.utils.DBHelper;
import com.jwy.bjtumidas.utils.DateUtils;

public class QueryComment {
	/**
	 * 通过id查询用户的昵称
	 * 
	 * @param id
	 * @return
	 */
	public static String getUserInfoById(int id) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_nickName,u_no from users_tb where u_id="
					+ id;
			ResultSet resultSet = dbHelper.querySql(sql);
			try {
				resultSet.next();
				String nickName = resultSet.getString("u_nickName");
				String no = resultSet.getString("u_no");
				resultSet.close();
				return no + "(" + nickName + ")";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}

		return null;
	}

	/**
	 * 查询评论信息
	 */
	public ArrayList<AdminComment> queryCommentInfo() {
		DBHelper dbHelper = new DBHelper();
		try {

			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "select c_id,c_content,comment_tb.g_id,comment_tb.u_id,c_time from comment_tb,goods_tb where comment_tb.g_id=goods_tb.g_id and g_state<2";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminComment> gList = new ArrayList<AdminComment>();
			while (resultSet.next()) {
				AdminComment comment = new AdminComment();
				int u_id = resultSet.getInt("u_id");
				String uInfo = getUserInfoById(u_id);
				comment.setuInfo(uInfo);
				int gId = resultSet.getInt("g_id");
				comment.setgId(gId);
				String time_str = resultSet.getString("c_time");
				long cTime = Long.parseLong(time_str);
				comment.setcTime(DateUtils.getTimeAfterFormat(cTime));
				comment.setcContent(resultSet.getString("c_content"));
				comment.setcId(resultSet.getInt("c_id"));
				gList.add(comment);
			}
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 按条件查询评论
	 */
	public ArrayList<AdminComment> queryCommentInfo(String select) {
		DBHelper dbHelper = new DBHelper();
		try {

			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "select c_id,c_content,comment_tb.g_id,comment_tb.u_id,c_time from comment_tb,goods_tb where comment_tb.g_id=goods_tb.g_id and g_state=0 and c_content like '%"
					+ select + "%'";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminComment> gList = new ArrayList<AdminComment>();
			while (resultSet.next()) {
				AdminComment comment = new AdminComment();
				int u_id = resultSet.getInt("u_id");
				String uInfo = getUserInfoById(u_id);
				comment.setuInfo(uInfo);
				int gId = resultSet.getInt("g_id");
				comment.setgId(gId);
				String time_str = resultSet.getString("c_time");
				long cTime = Long.parseLong(time_str);
				comment.setcTime(DateUtils.getTimeAfterFormat(cTime));
				comment.setcContent(resultSet.getString("c_content"));
				comment.setcId(resultSet.getInt("c_id"));
				gList.add(comment);
			}
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

}
