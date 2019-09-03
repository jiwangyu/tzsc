package com.jwy.bjtumidas.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.jwy.bjtumidas.engine.RegisterService;
import com.jwy.bjtumidas.model.Users;
import com.jwy.bjtumidas.utils.MD5Encoder;
import com.jwy.bjtumidas.utils.ObjectUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// try {
		// // ConnDB cDb = new ConnDB();
		// // Connection conn = cDb.getConn();
		// // String sql = "select * from users";
		// // PreparedStatement state = (PreparedStatement) conn
		// // .prepareStatement(sql);
		// // ResultSet resultSet = state.executeQuery();
		// // while (resultSet.next()) {
		// // System.out.println(resultSet.getString("u_no") + "------"
		// // + resultSet.getString("u_phone"));
		// // }
		// DBHelper dHelper=new DBHelper();
		// String
		// sql="select count(*) from users where u_no='20122205011035' or u_phone='201222205011035'";
		//
		// System.out.println(dHelper.getCountSql(sql));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("username", "hdl");
		// params.put("pwd", "132");
		// Set<Entry<String, String>> entrySet = params.entrySet();
		// String data = "";
		// for (Entry<String, String> entry : entrySet) {
		// data += entry.getKey() + "=" + entry.getValue() + "&";
		// }
		// System.out.println(data.substring(0, data.length() - 1));
		// Dog dog=new Dog();
		// dog.name="ddd";
		// Map<String, Object> objToMap = ObjectUtils.objToMap(dog);
		// System.out.println(objToMap);
		// System.out.println("查询出来的用户名为："
		// + new PublishService().getUId("15519099928"));
		// System.out.println(System.currentTimeMillis());
		// long currentTimeMillis = System.currentTimeMillis();
		// // System.out.println(dateToLong("2016年04月21日 21:44"));
		// System.out.println(dateToLong("2016年05月01日 01:00"));
		// long lDeadline = dateToLong("2016年04月21日 21:16");
		// if ( currentTimeMillis + 2592111111L >lDeadline) {
		// System.out.println("--------");
		// }
		// ArrayList<Cat> list = new ArrayList<Cat>();
		// Cat c1 = new Cat("c1", 10);
		// Cat c2 = new Cat("c2", 20);
		// Cat c3 = new Cat("c3", 30);
		// list.add(c1);
		// list.add(c2);
		// list.add(c3);
		// String json = ObjectUtils.listToJson(list);
		// System.out.println(json);
		// System.out.println(System.currentTimeMillis());
		// System.out.println(getDate(1461317616490l));

		// Timer timer = new Timer();
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// System.out.println("-------------");
		//
		// }
		// }, 0, 1000);

		// System.out.println("剩余时间:"
		// + formartTime(dateToLong("2016年04月24日  19:08")
		// - System.currentTimeMillis()));

		// Users users = new RegisterService().getUserInfo("15519099921");
		// System.out.println(ObjectUtils.objToJson(users));
		// long time = 1462885008694l;
		System.out.println(dateToLong("2016年04月24日  19:08"));
//		System.out.println("20122205011035".subSequence(0, 4));
//		.equals("2f422ac875a1feb4364c04d3a9db707e");
//		System.out.println("20122205011035".substring(7));
//		System.out.println(MD5Encoder.encode("011067"));

	}

	public static String formartTime(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		return (day / 10 > 0 ? day : "0" + day) + "天 "
				+ (hour / 10 > 0 ? hour : "0" + hour) + ":"
				+ (minute / 10 > 0 ? minute : "0" + minute) + ":"
				+ (second / 10 > 0 ? second : "0" + second);
	}

	// 秒数转化为日期
	public static String getDateFromSeconds(String seconds) {
		if (seconds == null)
			return " ";
		else {
			Date date = new Date();
			try {
				date.setTime(Long.parseLong(seconds) * 1000);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				return "Input string:" + seconds + "not correct,eg:2011-01-20";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(date);
		}
	}

	public static int getDate(long time) throws ParseException {
		return Integer.parseInt(new SimpleDateFormat("MM")
				.format(new Date(time)));
	}

	/**
	 * 将一个"2016年04月21日 21:44"格式的日期 装换为毫秒值输出
	 * 
	 * @param time
	 * @return
	 * @throws ParseException
	 */
	public static long dateToLong(String time) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyy年MM月dd日 HH:mm");
		return format.parse(time).getTime();
	}

}
