package com.jwy.bjtumidas.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.jwy.bjtumidas.model.AdminComment;
import com.jwy.bjtumidas.model.AdminGoods;
import com.jwy.bjtumidas.model.AdminUsers;
import com.jwy.bjtumidas.model.Goods;

public class ObjectUtils {
	/**
	 * 对象转换为map集合
	 * 
	 * @param object
	 * @return
	 */
	public static Map<String, Object> objToMap(Object object) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		if (object == null) {
			return null;
		}
		Field[] fields = object.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = object.getClass().getDeclaredField(
						fields[i].getName());
				field.setAccessible(true);
				Object obj = field.get(object);
				reMap.put(fields[i].getName(), obj);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reMap;
	}

	/**
	 * 集合转换为json数组
	 * 
	 * @param object
	 * @return
	 */
	public static String listToJson(ArrayList<?> list) {
		Gson gson = new Gson();
		String json = gson.toJson(list);
		// System.out.println("转换之后的json为" + json);
		return json;
	}

	/**
	 * 集合转换为json数组
	 * 
	 * @param object
	 * @return
	 */
	public static String objToJson(Object object) {
		Gson gson = new Gson();
		String json = gson.toJson(object);
		// System.out.println("转换之后的json为" + json);
		return json;
	}

	/**
	 * 按对象的年级排序
	 * 
	 * @param usersInfo
	 * @return
	 */
	public static ArrayList<AdminUsers> orderUsers(
			ArrayList<AdminUsers> usersInfo) {
		Collections.sort(usersInfo);
		return usersInfo;
	}

	/**
	 * 按对象的信息排序
	 * 
	 * @param queryAllGoodsInfo
	 * @return
	 */
	public static ArrayList<AdminGoods> orderGoods(
			ArrayList<AdminGoods> queryAllGoodsInfo) {
		Collections.sort(queryAllGoodsInfo);
		return queryAllGoodsInfo;
	}

	/**
	 * 按对象的id排序
	 * 
	 * @param queryAllGoodsInfo
	 * @return
	 */
	public static ArrayList<AdminComment> orderComment(
			ArrayList<AdminComment> queryAllGoodsInfo) {
		Collections.sort(queryAllGoodsInfo);
		return queryAllGoodsInfo;
	}

}
