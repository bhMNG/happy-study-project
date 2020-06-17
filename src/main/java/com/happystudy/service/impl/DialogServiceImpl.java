package com.happystudy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.happystudy.constants.Constants;
import com.happystudy.dao.DialogMapper;
import com.happystudy.model.Dialog;
import com.happystudy.service.DialogService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service
public class DialogServiceImpl implements DialogService{
	@Autowired
	private DialogMapper dialogMapper;

	@Override
	public JSONObject queryDialog(String keyword) {
		JSONObject result = new JSONObject();
		try {
			List<Dialog> dialogList = dialogMapper.queryDialog(keyword);
			result.set("dialogArray", JSONUtil.parse(dialogList));
			result.set("status", Constants.SUCCESS);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return result.set("status", Constants.DB_ERROR);
		}
	}

	@Override
	public JSONObject deleteDialog(Integer id) {
		JSONObject result = new JSONObject();
		try {
			dialogMapper.deleteDialog(id);
			result.set("status", Constants.SUCCESS);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return result.set("status", Constants.SUCCESS);
		}
	}
	
}
