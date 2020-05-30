package com.happystudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.hutool.json.JSONObject;

@Component
public class UserInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if (handler instanceof HandlerMethod) {
			request.setAttribute("currentUser", getCurrentUser());
		}
		
		
		return true;
	}
	private JSONObject getCurrentUser() {
		JSONObject currentUser = new JSONObject();
		currentUser.set("u_name", "testUsername");
		currentUser.set("u_photo", "");
		currentUser.set("u_idcard", "");
		return currentUser;
	}
}
