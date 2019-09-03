package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.jwy.bjtumidas.model.GoodsPublishInfo;
import com.jwy.bjtumidas.model.RequestGoodsInfo;
import com.jwy.bjtumidas.utils.ConnDB;
import com.jwy.bjtumidas.utils.DBHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 发布商品的服务类
 * 
 * @author HDL
 * 
 */
public class PublishService {
	/**
	 * 发布
	 * 
	 * @param userName
	 * 
	 * @param gInfo
	 * @return
	 */
	public int publish(String userName, GoodsPublishInfo gInfo) {
		// DBHelper dbHelper = new DBHelper();
		ConnDB db = new ConnDB();// 声明连接对象
		Connection conn = db.getConn();
		PreparedStatement state = null;
		try {
			// 发布的类型,0表示发布新品，1表示拍卖，2表示免费送
			int publishType = 0;
//			System.out.println("发布类型为：" + gInfo.getgPublishType());
			if ("pai".equals(gInfo.getgPublishType())) {
				publishType = 1;
			} else if ("feel".equals(gInfo.getgPublishType())) {
				publishType = 2;
			}
			Long time = System.currentTimeMillis();
			String u_id = getUId(userName);
			String sql = "insert into goods_tb(g_title,t_id,g_price,g_oldPrice,g_desc,g_pinkage,g_urgent,g_address,u_id,g_publishType,g_time,g_deadline) values('"
					+ gInfo.getgTitle()
					+ "','"
					+ gInfo.getgClassify()
					+ "',"
					+ gInfo.getgPrice()
					+ ","
					+ gInfo.getgOldPrice()
					+ ",'"
					+ gInfo.getgDesc()
					+ "',"
					+ (gInfo.isPinkage() ? 1 : 0)
					+ ","
					+ (gInfo.isUrgent() ? 1 : 0)
					+ ",'"
					+ gInfo.getgAddress()
					+ "',"
					+ u_id
					+ ","
					+ publishType
					+ ",'" + time + "','" + gInfo.getgDeadline() + "')";
//			System.out.println(sql);
			state = conn.prepareStatement(sql);
			if (state.executeUpdate() == 1) {// 返回影响的行数,返回为0表示没有插入成功
//				System.out.println("注册成功了,准备获取刚才插入商品的id");
				String sqlId = "select last_insert_id() as g_id";
				ResultSet resultSet = state.executeQuery(sqlId);
				resultSet.next();
				int g_id = resultSet.getInt("g_id");
//				System.out.println("已经获取到刚才插入的gid了================" + g_id);
				resultSet.close();
				return g_id;
			}
			// if (dbHelper.insertSql(sql)) {
			// return true;
			// }
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				if (state != null) {
					state.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e2) {
			}
		}
		return -1;
	}

	/**
	 * 获取用户id
	 * 
	 * @param userName
	 */
	public String getUId(String userName) {
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

	/**
	 * 发布求购商品
	 * 
	 * @param username
	 * @param gInfo
	 * @return
	 */
	public boolean publishReq(String username, RequestGoodsInfo gInfo) {
		DBHelper dbHelper = new DBHelper();
		try {
			Long time = System.currentTimeMillis();
			String u_id = new PublishService().getUId(username);
			String sql = "insert into reqgoods_tb(r_title,r_content,u_id,r_time) values('"
					+ gInfo.getrTitle()
					+ "','"
					+ gInfo.getrContent()
					+ "',"
					+ u_id + ",'" + time + "')";
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

	/**
	 * 插入图片路径
	 * 
	 * @param gId
	 * @param g_imgPath
	 * @return
	 */
	public boolean insertImgUrl(String gId, String g_imgPath) {
		DBHelper dbHelper = new DBHelper();
		try {
//			System.out.println("输出过来我卡看看-------" + g_imgPath + "-------" + gId);
			String sql = "insert into pic_url_tb(g_id,p_url) values(" + gId
					+ ",'" + g_imgPath + "')";
//			System.out.println("输出过来我卡看看-------" + sql);
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

}
