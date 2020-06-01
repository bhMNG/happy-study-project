package com.happystudy.util;

import java.util.List;
import java.util.Map;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class MyUtil {
	public static JSONArray stuListToArray(List<Object> stuList) {
		JSONArray array = new JSONArray();
		for (Object obj : stuList) {
			JSONObject json = JSONUtil.parseObj(obj);
			array.add(json);
		}
		return array;
	}
	
	public static JSONArray mapListToArray(List<Map<String, Object>> mapList) {
		JSONArray array = new JSONArray();
		for (Map<String, Object> map : mapList) {
			//map.put("s_birthday", (Object)((String)map.get("s_birthday")));
			JSONObject json = new JSONObject(map);
			System.out.println(map.get("s_birthday"));
			System.out.println(json.get("s_birthday"));
			array.add(json);
			
		}
		System.out.println("=================================== " + array);
		return array;
	}
}
