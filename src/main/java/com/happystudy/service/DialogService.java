package com.happystudy.service;

import cn.hutool.json.JSONObject;

public interface DialogService {
	//查询日志
	public JSONObject queryDialog(String keyword);
	//删除日志
	public JSONObject deleteDialog(Integer id);
}
