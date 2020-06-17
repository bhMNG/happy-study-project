package com.happystudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.happystudy.constants.Constants;
import com.happystudy.dao.UserMapper;
import com.happystudy.model.Property;
import com.happystudy.service.UserService;

import cn.hutool.json.JSONObject;

@Component
public class UserInterceptor implements HandlerInterceptor { 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if (handler instanceof HandlerMethod) {
			JSONObject currentUser = getCurrentUser(request.getSession());
			if (currentUser == null) {
				response.sendRedirect(request.getAttribute("ctxPath")+"/login");
				return false;
			}
			request.setAttribute("currentUser", currentUser);
			
			
		}
		return true;
	}
	
	
	private JSONObject getCurrentUser(HttpSession session) {
		//username userProperty userPosition userPhoto userIdcard
		if (session.getAttribute(Constants.CURRENT_USER_KEY)== null) {
			return null;
		}
		JSONObject currentUserInfo = (JSONObject)session.getAttribute(Constants.CURRENT_USER_KEY); 
		if (currentUserInfo.getStr("userPhoto")==null) {
			currentUserInfo.set("userPhoto", "");
		}
		if (currentUserInfo.getStr("userIdcard")==null) {
			currentUserInfo.set("userIdcard", "");
		}
		if (currentUserInfo.getStr("phonenum") == null) {
			currentUserInfo.set("phonenum", "");
		}
		
		
		//JSONObject currentUser = new JSONObject();
		//currentUser.set("u_name", "usertest");
		//currentUser.set("u_photo", "");
		//currentUser.set("u_idcard", "");
		return currentUserInfo;
	}

	
}
