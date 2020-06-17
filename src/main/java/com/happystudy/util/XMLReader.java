package com.happystudy.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.happystudy.cache.MyCache;
import com.happystudy.constants.FinalData;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class XMLReader {
	public static void initDefaultRoleDutyMap() {
		if (!MyCache.DEFAULT_PROP.isEmpty()) {
			return ;
		}
		try {
			ClassPathResource classPathResource = new ClassPathResource("dataxml/role_prop.xml");
			InputStream is = classPathResource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Element> propList = root.elements();
			Map<Integer, List<String>> propMap = MyCache.DEFAULT_PROP;
			//Map<Integer, List<String>> propMap = new HashMap<>();
			for (Element propEle : propList) {
				Integer key = Integer.parseInt(propEle.attributeValue("id"));
				List<String> roleDutys = new ArrayList<>();
				List<Element> subProps = propEle.elements();
				if (subProps.isEmpty()) {
					return ;
				}
				for (Element elem : subProps) {
					roleDutys.add(elem.attributeValue("pNo"));
				}
				propMap.put(key, roleDutys);
			}
			is.close();
		}catch(Exception e) {
			e.printStackTrace();
			return ;
		}
	}
	
	public static void initMenuXML() {
		if (!FinalData.MENU.isEmpty()) {
			return ;
		}
		try {
			ClassPathResource classPathResource = new ClassPathResource("dataxml/menu.xml");
			InputStream is = classPathResource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			List<Element> menus = root.elements();
			JSONArray menuJsonArray = FinalData.MENU;
			for (Element menuEle : menus) {
				JSONObject menuJson = new JSONObject();
				menuJson.set("id", menuEle.attributeValue("id"));
				menuJson.set("name", menuEle.attributeValue("name"));
				menuJson.set("icon", menuEle.attributeValue("icon"));
				menuJsonArray.add(menuJson);
				//二级菜单
				JSONArray subMenuArray = new JSONArray();
				List<Element> subMenus = menuEle.elements();
				if (!subMenus.isEmpty()) {
					menuJson.set("id", "= "+menuEle.attributeValue("id"));
				}
				for (Element subMenuEle : subMenus) {
					JSONObject subMenuJson = new JSONObject();
					subMenuJson.set("id",  subMenuEle.attributeValue("id"));
					subMenuJson.set("name",  subMenuEle.attributeValue("name"));
					subMenuJson.set("icon", subMenuEle.attributeValue("icon"));
					subMenuArray.add(subMenuJson);
				}
				menuJson.set("subMenus", subMenuArray);
			}
			is.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//public static void main(String[] args) {
	//	Map<Integer, List<String>> map = XMLReader.initDefaultRoleDutyMap();
	//	List<String> dutyList = map.get(1);
	//	String[] pNos = new String[dutyList.size()];
	//	dutyList.toArray(pNos);
	//	System.out.println(pNos.length);
	//}
}
