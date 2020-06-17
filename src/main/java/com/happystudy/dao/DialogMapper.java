package com.happystudy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.happystudy.model.Dialog;

@Mapper
public interface DialogMapper {
	//查询日志
	public List<Dialog> queryDialog(String keyword);
	//写入日志
	public void writeDialog(Dialog dialog);
	//删除日志
	public void deleteDialog(Integer id);
}
