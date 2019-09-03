package com.jwy.bjtumidas.engine;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.deploy.model.DDBean;

import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.model.CommentInfo;
import com.jwy.bjtumidas.model.Goods;
import com.jwy.bjtumidas.model.GoodsType;
import com.jwy.bjtumidas.model.PaiGoods;
import com.jwy.bjtumidas.model.UrgentGoods;
import com.jwy.bjtumidas.utils.DBHelper;
import com.jwy.bjtumidas.utils.DateUtils;
import com.jwy.bjtumidas.utils.UrlContoller;

public class QueryGoodsService {
	/**
	 * 通过gid查询商品评论内容
	 * 
	 * @param gId
	 * @return
	 */
	public ArrayList<CommentInfo> getGoodsCommentByGId(int gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select c_id,c_content,u_id,c_time from comment_tb where g_id="
					+ gId + " ORDER BY c_time DESC";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<CommentInfo> comment_list = new ArrayList<CommentInfo>();
			while (resultSet.next()) {
				CommentInfo info = new CommentInfo();
				info.setcId(resultSet.getInt("c_id"));
				int u_id = resultSet.getInt("u_id");
				info.setuId(u_id);
				info.setcContent(resultSet.getString("c_content"));
				info.setcTime(resultSet.getLong("c_time"));
				info.setuNickName(getNickNameById(u_id));
				info.setgId(gId);
				comment_list.add(info);
			}
			resultSet.close();
			return comment_list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 通过gid查询商品
	 * 
	 * @param gId
	 * @return
	 */
	public PaiGoods getGoodsDetailByGId(int gId, String type) {
		DBHelper dbHelper = new DBHelper();
		try {
			PaiGoods goods = new PaiGoods();
			String sql = "select * from goods_tb where g_state=0 and g_id="
					+ gId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			int u_id = resultSet.getInt("u_id");

			String nickName = getNickNameById(u_id);
			goods.setuNickName(nickName);
			goods.setuId(u_id);
			goods.setgId(gId);
			String time_str = resultSet.getString("g_time");
			long gTime = Long.parseLong(time_str);
			goods.setgTime(gTime);
			goods.setgMaxPrice(getMaxPriceByGId(gId));
			goods.setgPublishType(resultSet.getByte("g_publishType"));
			goods.setgIsSaled((resultSet.getInt("g_state") == 0 ? true : false));
			String deadline_str = resultSet.getString("g_deadline");
			if ("pai".equals(type)) {
				long g_deadline = 0;
				if (deadline_str != null && !deadline_str.equals("null")) {
					g_deadline = Long.parseLong(deadline_str);
				}
				goods.setgDeaLine(g_deadline);
			}
			goods.setgCommentCount(getCommetCountByGId(gId));
			String address = resultSet.getString("g_address");
			goods.setgAddress("" + address);
			goods.setgImgUrls(getImgsToList(gId));
			String title = resultSet.getString("g_title");
			goods.setgTitle("" + title);
			String gDesc = resultSet.getString("g_desc");
			goods.setgDesc("" + gDesc);
			int gClassify = resultSet.getInt("t_id");
			goods.setgClassify(gClassify);
			int price = resultSet.getInt("g_price");
			goods.setgPrice(price);
			int oldPrice = resultSet.getInt("g_oldPrice");
			goods.setgOldPrice(oldPrice);
			Byte browCount = resultSet.getByte("g_browCount");
			goods.setgBrowCount(browCount);
			Byte g_nice = resultSet.getByte("g_nice");
			goods.setgNice(g_nice);
			byte pinkage = resultSet.getByte("g_pinkage");
			if (pinkage == 1) {
				goods.setPinkage(true);
			} else {
				goods.setPinkage(false);
			}
			goods.setUrgent(resultSet.getByte("g_urgent") == 1 ? true : false);
//			System.out.println("查询了gid=" + gId + "的商品哦");
			resultSet.close();
			return goods;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 通过gid获取图片,放到list集合中
	 * 
	 * @param gId
	 * @return
	 */
	private List<String> getImgsToList(int gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select p_url from pic_url_tb where g_id=" + gId;
			ResultSet resultSet = dbHelper.querySql(sql);
			List<String> pUrl_list = new ArrayList<String>();
			while (resultSet.next()) {
				pUrl_list.add(UrlContoller.URL_PUBLIC
						+ resultSet.getString("p_url"));
			}
			resultSet.close();
			return pUrl_list;
		} catch (Exception e) {
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 通过id查询用户的昵称
	 * 
	 * @param id
	 * @return
	 */
	public static String getNickNameById(int id) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_nickName from users_tb where u_id=" + id;
			ResultSet resultSet = dbHelper.querySql(sql);
			try {
				resultSet.next();
				String nickName = resultSet.getString("u_nickName");
				resultSet.close();
				return nickName;
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
	 * 查询紧急出售的商品信息
	 * 
	 * @param flag
	 * 
	 * @return
	 */
	public ArrayList<UrgentGoods> queryUrgentGoods() {
		DBHelper dbHelper = new DBHelper();
		try {

			String sql = "select u_id,g_id,g_title,t_id from goods_tb where g_state=0 and g_urgent=1  ORDER BY g_time DESC";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<UrgentGoods> gList = new ArrayList<UrgentGoods>();
			while (resultSet.next()) {
				UrgentGoods goods = new UrgentGoods();
				int u_id = resultSet.getInt("u_id");
				goods.setuId(u_id);
				goods.setgId(resultSet.getInt("g_id"));
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				String classify = resultSet.getString("t_id");
				goods.setgClassify("" + classify);
//				System.out.println(goods.toString());
				gList.add(goods);
			}
//			System.out.println("获取完了数据库中的紧急出售商品数据:" + gList);
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	// g_id | g_title | u_id | g_classify | g_imgPath | g_price | g_oldPrice |
	// g_publishType | g_state | g_desc | g_pinkage | g_urgent | g_address |
	// g_deadline | g_browCount | g_nice | g_time
	/**
	 * 查询新品
	 * 
	 * @param flag
	 * 
	 * @return
	 */
	public ArrayList<Goods> queryNewGoodsInfo(String flag) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "";
			if ("all".equals(flag)) {
				sql = "select * from goods_tb where g_state=0 ORDER BY g_time DESC";
			} else if ("hot".equals(flag)) {
				sql = "select * from goods_tb where g_state=0 ORDER BY g_browCount DESC";
			} else if ("free".equals(flag)) {
				sql = "select * from goods_tb where g_state=0 and g_publishType=2 ORDER BY g_browCount DESC";
			} else if ("urgent".equals(flag)) {
				sql = "select * from goods_tb where g_state=0 and g_urgent=1 ORDER BY g_browCount DESC";
			} else {
				sql = "select * from goods_tb where g_state=0 ORDER BY g_time DESC limit 0,5";
			}
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<Goods> gList = new ArrayList<Goods>();
			while (resultSet.next()) {
				Goods goods = new Goods();
				int u_id = resultSet.getInt("u_id");
				String nickName = getNickNameById(u_id);
				goods.setuNickName(nickName);
				goods.setuId(u_id);
				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);
				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(gTime);
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int gClassify = resultSet.getInt("t_id");
				goods.setgClassify(gClassify);
				goods.setgImgUrls(getImgsToList(gId));
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				int oldPrice = resultSet.getInt("g_oldPrice");
				goods.setgOldPrice(oldPrice);
				Byte browCount = resultSet.getByte("g_browCount");
				goods.setgBrowCount(browCount);
				byte pinkage = resultSet.getByte("g_pinkage");
				if (pinkage == 1) {
					goods.setPinkage(true);
				} else {
					goods.setPinkage(false);
				}
//				System.out.println("新品列表" + goods.toString());
				gList.add(goods);
			}
//			System.out.println("获取完了数据库中的数据:" + gList);
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 通过gid获取最高的出价
	 * 
	 * @param gId
	 * @return
	 */
	public int getMaxPriceByGId(int gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select max(p_maxPrice) from paiprice_tb where g_id="
					+ gId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			int maxPrice = resultSet.getInt(1);
			resultSet.close();
			return maxPrice;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return 0;
	}

	/**
	 * 通过gid获取评论条数
	 * 
	 * @param gId
	 * @return
	 */
	public int getCommetCountByGId(int gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select count(*) from comment_tb where g_id=" + gId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			int commentCount = resultSet.getInt(1);
			resultSet.close();
			return commentCount;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return 0;
	}

	/**
	 * 查询拍卖商品的信息
	 * 
	 * @param t_id
	 * 
	 * @return
	 */
	public ArrayList<PaiGoods> queryAllGoodsInfo(String t_id) {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "";
			if ("200".equals(t_id)) {
				sql = "select * from goods_tb where g_state=0";
			} else if ("100".equals(t_id)) {
				sql = "select * from goods_tb where g_state=0";
			} else {
				sql = "select * from goods_tb where g_state=0 and t_id=" + t_id;
			}
//			System.out.println("查询条件为：" + sql);
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<PaiGoods> gList = new ArrayList<PaiGoods>();
			int i = 0;
			while (resultSet.next()) {
				PaiGoods goods = new PaiGoods();
				String deadline_str = resultSet.getString("g_deadline");
				long g_deadline = 0;
				if (deadline_str != null && !deadline_str.equals("null")) {
					g_deadline = Long.parseLong(deadline_str);
				}
				// if (System.currentTimeMillis() + 30000 > g_deadline) {
				// continue;
				// }
				goods.setgDeaLine(g_deadline);

				int u_id = resultSet.getInt("u_id");
				String nickName = getNickNameById(u_id);
				goods.setuNickName(nickName);
				goods.setuId(u_id);

				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);

				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(gTime);

				goods.setgMaxPrice(getMaxPriceByGId(gId));

				goods.setgCommentCount(getCommetCountByGId(gId));
				goods.setgImgUrls(getImgsToList(gId));
				String address = resultSet.getString("g_address");
				goods.setgAddress("" + address);
				goods.setgDesc(resultSet.getString("g_desc"));
				goods.setgIsSaled(resultSet.getByte("g_state") == 0 ? false
						: true);
				goods.setgIsSaled(resultSet.getByte("g_state") == 0 ? false
						: true);
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int gClassify = resultSet.getInt("t_id");
				goods.setgClassify(gClassify);
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				int oldPrice = resultSet.getInt("g_oldPrice");
				goods.setgOldPrice(oldPrice);
				Byte browCount = resultSet.getByte("g_browCount");
				goods.setgBrowCount(browCount);
				Byte g_nice = resultSet.getByte("g_nice");
				goods.setgNice(g_nice);
				byte pinkage = resultSet.getByte("g_pinkage");

				if (pinkage == 1) {
					goods.setPinkage(true);
				} else {
					goods.setPinkage(false);
				}
				gList.add(goods);
			}
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 查询拍卖商品的信息
	 * 
	 * @param t_id
	 * 
	 * @return
	 */
	public ArrayList<AdminGoods> queryAllGoodsInfo() {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "select * from goods_tb where g_state=0";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminGoods> gList = new ArrayList<AdminGoods>();
			while (resultSet.next()) {
				AdminGoods goods = new AdminGoods();
				int u_id = resultSet.getInt("u_id");
				String uInfo = getUserInfoById(u_id);
				goods.setuInfo(uInfo);
				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);
				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(DateUtils.getTimeAfterFormat(gTime));
				goods.setgMaxPrice(getMaxPriceByGId(gId));

				goods.setgDesc(resultSet.getString("g_desc"));
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int t_id = resultSet.getInt("t_id");
				goods.setgType(getGType(t_id));
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				Byte browCount = resultSet.getByte("g_browCount");
				byte publishType = resultSet.getByte("g_publishType");
				if (publishType == 0) {
					goods.setgPublishType("新品");
				} else if (publishType == 1) {
					goods.setgPublishType("拍卖");
				} else if (publishType == 2) {
					goods.setgPublishType("免费");
				}
				goods.setgBrowCount(browCount);
				gList.add(goods);
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
	 * 通过id获取类型名称
	 * 
	 * @param t_id
	 * @return
	 */
	private String getGType(int t_id) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select t_name from type_tb where t_id=" + t_id;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			String t_name = resultSet.getString("t_name");
			return t_name;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	// g_id | g_title | u_id | g_classify | g_imgPath | g_price | g_oldPrice |
	// g_publishType | g_state | g_desc | g_pinkage | g_urgent | g_address |
	// g_deadline | g_browCount | g_nice | g_time
	/**
	 * 查询拍卖商品的信息
	 * 
	 * @param flag
	 * 
	 * @return
	 */
	public ArrayList<PaiGoods> queryPaiGoodsInfo() {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC,
			String sql = "";
			sql = "select * from goods_tb where g_state=0 and g_publishType=1 ORDER BY g_time DESC";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<PaiGoods> gList = new ArrayList<PaiGoods>();
			while (resultSet.next()) {
				PaiGoods goods = new PaiGoods();
				String deadline_str = resultSet.getString("g_deadline");
				long g_deadline = Long.parseLong(deadline_str);
				if (System.currentTimeMillis() + 30000 > g_deadline) {
					continue;
				}
				goods.setgDeaLine(g_deadline);
				goods.setgDesc(resultSet.getString("g_desc"));
				int u_id = resultSet.getInt("u_id");
				String nickName = getNickNameById(u_id);
				goods.setuNickName(nickName);
				goods.setuId(u_id);

				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);

				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(gTime);

				goods.setgMaxPrice(getMaxPriceByGId(gId));

				goods.setgCommentCount(getCommetCountByGId(gId));
				goods.setgImgUrls(getImgsToList(gId));
				String address = resultSet.getString("g_address");
				goods.setgAddress("" + address);
				goods.setgIsSaled(resultSet.getByte("g_state") == 0 ? false
						: true);
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int gClassify = resultSet.getInt("t_id");
				goods.setgClassify(gClassify);
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				int oldPrice = resultSet.getInt("g_oldPrice");
				goods.setgOldPrice(oldPrice);
				Byte browCount = resultSet.getByte("g_browCount");
				goods.setgBrowCount(browCount);
				Byte g_nice = resultSet.getByte("g_nice");
				goods.setgNice(g_nice);

				byte pinkage = resultSet.getByte("g_pinkage");

				if (pinkage == 1) {
					goods.setPinkage(true);
				} else {
					goods.setPinkage(false);
				}
//				System.out.println(goods.toString());
				gList.add(goods);
			}
//			System.out.println("获取完了数据库中拍卖的数据哦------------的数据:" + gList);
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 获取卖家的电话
	 * 
	 * @param gId
	 * 
	 * @return
	 */
	public String getUserPhone(int gId) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "select u_phone from users_tb,goods_tb where users_tb.u_id=goods_tb.u_id and g_id="
					+ gId;
			ResultSet resultSet = dbHelper.querySql(sql);
			resultSet.next();
			String phone = resultSet.getString("u_phone");
			return phone;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 查询商品
	 * 
	 * @param select
	 * @param j
	 * @return
	 */
	public ArrayList<PaiGoods> queryGoodsInfo(String select, int j) {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "";
			if (j == 0) {
				String u_id = getUId(select);
				sql = "select * from goods_tb where u_id= " + u_id;
			} else {
				sql = "select * from goods_tb where g_state=0 and g_title like '%"
						+ select + "%' or g_desc like'%" + select + "%'";
			}
//			System.out.println(sql);
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<PaiGoods> gList = new ArrayList<PaiGoods>();
			int i = 0;
			while (resultSet.next()) {
				PaiGoods goods = new PaiGoods();
				String deadline_str = resultSet.getString("g_deadline");
				long g_deadline = 0;
				if (deadline_str != null && !deadline_str.equals("null")) {
					g_deadline = Long.parseLong(deadline_str);
				}
				// if (System.currentTimeMillis() + 30000 > g_deadline) {
				// continue;
				// }
				goods.setgDeaLine(g_deadline);

				int u_id = resultSet.getInt("u_id");
				String nickName = getNickNameById(u_id);
				goods.setuNickName(nickName);
				goods.setuId(u_id);

				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);

				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(gTime);

				goods.setgMaxPrice(getMaxPriceByGId(gId));

				goods.setgCommentCount(getCommetCountByGId(gId));
				goods.setgImgUrls(getImgsToList(gId));
				String address = resultSet.getString("g_address");
				goods.setgAddress("" + address);
				goods.setgDesc(resultSet.getString("g_desc"));
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int gClassify = resultSet.getInt("t_id");
				goods.setgClassify(gClassify);
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				int oldPrice = resultSet.getInt("g_oldPrice");
				goods.setgIsSaled(resultSet.getByte("g_state") == 0 ? false
						: true);
				goods.setgOldPrice(oldPrice);
				Byte browCount = resultSet.getByte("g_browCount");
				goods.setgBrowCount(browCount);
				Byte g_nice = resultSet.getByte("g_nice");
				goods.setgPublishType(resultSet.getByte("g_publishType"));
				goods.setgNice(g_nice);
				byte pinkage = resultSet.getByte("g_pinkage");

				if (pinkage == 1) {
					goods.setPinkage(true);
				} else {
					goods.setPinkage(false);
				}
				gList.add(goods);
			}
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 通过用户名获取用户id
	 * 
	 * @param userName
	 */
	public static String getUId(String userName) {
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
	 * 按条件查询商品
	 */
	public ArrayList<AdminGoods> queryGoodsInfoBySelect(String select) {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "select * from goods_tb where g_state=0 and g_title like '%"
					+ select + "%' or g_desc like '%" + select + "%'";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<AdminGoods> gList = new ArrayList<AdminGoods>();
			while (resultSet.next()) {
				AdminGoods goods = new AdminGoods();
				int u_id = resultSet.getInt("u_id");
				String uInfo = getUserInfoById(u_id);
				goods.setuInfo(uInfo);
				int gId = resultSet.getInt("g_id");
				goods.setgId(gId);
				String time_str = resultSet.getString("g_time");
				long gTime = Long.parseLong(time_str);
				goods.setgTime(DateUtils.getTimeAfterFormat(gTime));
				goods.setgMaxPrice(getMaxPriceByGId(gId));

				goods.setgDesc(resultSet.getString("g_desc"));
				String title = resultSet.getString("g_title");
				goods.setgTitle("" + title);
				int t_id = resultSet.getInt("t_id");
				goods.setgType(getGType(t_id));
				int price = resultSet.getInt("g_price");
				goods.setgPrice(price);
				Byte browCount = resultSet.getByte("g_browCount");
				byte publishType = resultSet.getByte("g_publishType");
				if (publishType == 0) {
					goods.setgPublishType("新品");
				} else if (publishType == 1) {
					goods.setgPublishType("拍卖");
				} else if (publishType == 2) {
					goods.setgPublishType("免费");
				}
				goods.setgBrowCount(browCount);
				gList.add(goods);
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
	 * 查询商品的类型
	 */
	public ArrayList<GoodsType> queryGoodsType() {
		DBHelper dbHelper = new DBHelper();
		try {
			// g_browCount DESC, ORDER BY g_time DESC
			String sql = "select t_id,t_name from type_tb";
			ResultSet resultSet = dbHelper.querySql(sql);
			ArrayList<GoodsType> gList = new ArrayList<GoodsType>();
			while (resultSet.next()) {
				GoodsType goodsType = new GoodsType();
				int t_id = resultSet.getInt("t_id");
				goodsType.settId(t_id);
				goodsType.settName(resultSet.getString("t_name"));
				gList.add(goodsType);
			}
			resultSet.close();
			return gList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbHelper.closeDB();
		}
		return null;
	}

	/**
	 * 添加商品类型
	 */
	public boolean addGoodsType(String tName) {
		DBHelper dbHelper = new DBHelper();
		try {
			String sql = "insert into type_tb values(null,'" + tName + "')";
//			System.out.println(sql);
			return dbHelper.updateSql(sql);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			dbHelper.closeDB();
		}
		return false;
	}

}
