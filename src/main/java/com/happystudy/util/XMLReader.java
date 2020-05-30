package com.happystudy.util;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;

import com.happystudy.constants.FinalData;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class XMLReader {
	public static void getMenuXML() {
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
				for (Element subMenuEle : subMenus) {
					JSONObject subMenuJson = new JSONObject();
					subMenuJson.set("id",  subMenuEle.attributeValue("id"));
					subMenuJson.set("name",  subMenuEle.attributeValue("name"));
					subMenuArray.add(subMenuJson);
				}
				menuJson.set("subMenus", subMenuArray);
			}
			
		}catch(Exception e) {
			
		}
		
	}
}
