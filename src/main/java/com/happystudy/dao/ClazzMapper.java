package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import com.happystudy.model.Clazz;
import com.happystudy.model.Student;

public interface ClazzMapper {

	//查询班级（5个参数）
	public Map<String, Object> queryClazz(String keyword,String orderBy,String orderWay,String offset,String pageSize);
	//查询班级个数
	public Integer queryClazzCount(String keyword);
	//添加班级
	public void addClazz(Clazz clazz);
	//修改班级信息
	public void updateClazzByNo(String cNo);
	//删除班级
	public void deleteClazzByNo(String cNo);
	//通过老师找班级
	public Clazz findClazzByTeacher(String tNo);
	//查询该班级的所有学生
	public List<Student> queryClazzAllStu(String cNo);
	//查询该班级的学生人数
	public Integer queryClazzStuCount(String cNo);
}
