package com.happystudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HappyStudyInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if (handler instanceof HandlerMethod) {
			setContextPath(request);
			request.setAttribute("sysTitle", "HappyStudy");
		}
		
		
		return true;
	}
	
	/**
	 * 设置上下文 为 ----> ctxPath
	 * @param request
	 */
	private void setContextPath(HttpServletRequest request) {
		//contextPath -> ctxPath
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int port = request.getServerPort();
		String ctxPath = request.getContextPath();
		if (port == 80) {
			ctxPath = scheme+"://"+serverName+ctxPath;
		}else {
			ctxPath = scheme+"://"+serverName+":"+port+ctxPath;
		}
		request.setAttribute("ctxPath", ctxPath);
	}
}
