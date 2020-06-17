package com.happystudy.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.happystudy.constants.Constants;
import com.happystudy.dao.DialogMapper;
import com.happystudy.dao.UserMapper;
import com.happystudy.model.Dialog;
import com.happystudy.model.Property;

import cn.hutool.json.JSONObject;

@Component
public class PropGuardianInterceptor implements HandlerInterceptor {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DialogMapper dialogMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		if (handler instanceof HandlerMethod) {
			boolean propJudg = happyStudyGuardian(request);
			if (!propJudg) {
				//response.sendRedirect(request.getAttribute("ctxPath")+"/happy-study/welcome");
				return false;
			}
		}
		doDialog(request);
		return true;
	}
	/**
	 * 权限保护
	 * @param rProperty 权限值
	 * @param request 
	 * @return
	 */
	private boolean happyStudyGuardian(HttpServletRequest request) {
		HttpSession session = request.getSession();
		JSONObject currentUserInfo = (JSONObject)session.getAttribute(Constants.CURRENT_USER_KEY);
		Integer rProperty = currentUserInfo.getInt("userProperty");
		if (rProperty == 0) {
			return true;
		}
		String url = request.getRequestURL().toString();
		String ctxPath = (String)request.getAttribute("ctxPath");
		String duty = url.split(ctxPath)[1];
		System.out.println("=========================== !!!!!!!!!!!!!!!!!!!!!! " +duty);
		try {
			String pNo = userMapper.getpNoByDuty(duty);
			if (pNo==null) {
				return true;//没有记录入拦截表里的默认可以通过
			}
			Property prop = userMapper.findRolePropertyByPNo(rProperty, pNo);
			if (prop == null) {
				//没有权限
				return false;
			}else {
				//有权限
				return true;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
			
	}
	/**
	 * 日志记录
	 */
	private void doDialog(HttpServletRequest request) {
		HttpSession session = request.getSession();
		JSONObject currentUserInfo = (JSONObject)session.getAttribute(Constants.CURRENT_USER_KEY);
		String dlTriggerObj = currentUserInfo.getStr("username");
		String dlAction = request.getRequestURL().toString().split((String)request.getAttribute("ctxPath"))[1];
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dlTime = df.format(new Date());
		String dlInfo = "";
		Dialog dialog = new Dialog();
		dialog.setDlInfo(dlInfo);
		dialog.setDlAction(dlAction);
		dialog.setDlTime(dlTime);
		dialog.setDlTriggerObj(dlTriggerObj);
		dialogMapper.writeDialog(dialog);
	}
}
