package com.happystudy.util;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.happystudy.constants.Constants;

import cn.hutool.json.JSONObject;

public class UserUtil {
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
	private static boolean isLetterDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}

	public static boolean strIsUsername(String str) {
		return ((!UserUtil.isNumeric(str))&&UserUtil.isLetterDigit(str));

	}
	
	public static String getCurrentUsername(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = (String)request.getAttribute("username");
		if (username == null) {
			username = ((JSONObject)session.getAttribute(Constants.CURRENT_USER_KEY)).getStr("username");
			if (username == null) {
				username = "严重错误： 当前用户为null 正在执行操作";
			}
		}
		return username;
	}

	
	
	/*
	public static void main(String[] args) {
		String str = "aaa111";
		System.out.println(UserUtil.strIsUsername(str));
	}*/
}
