package com.jwy.bjtumidas.engine;

import com.jwy.bjtumidas.utils.DBHelper;

public class MangePersonalGoodsServeice {
	/**
	 * 通过gid删除商品
	 * 
	 * @param gId
	 * @return
	 */
	public boolean deleteGoods(String gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "delete from goods_tb where g_id=" + gId;
			return dbHelper.deleteSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

	/**
	 * 通过id下架正在出售的商品
	 * 
	 * @param gId
	 * @return
	 */
	public boolean offGoods(String gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "update goods_tb set g_state=1 where g_id=" + gId;
//			System.out.println(sql);
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

	/**
	 * 删除评论
	 */
	public void deleteComment(String cId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "delete from comment_tb where c_id=" + cId;
			dbHelper.deleteSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
	}

	/**
	 * 商品类型
	 */
	public void deleteGoodsType(String tId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "delete from type_tb where t_id=" + tId;
			dbHelper.deleteSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
	}

}
