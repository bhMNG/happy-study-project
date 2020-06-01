package com.happystudy.dao;

import java.util.List;
import java.util.Map;

import com.happystudy.model.Clazz;
import com.happystudy.model.Student;
import org.springframework.stereotype.Repository;

public interface ClazzMapper {

	//查询班级（5个参数）
	public List<Map<String, Object>> queryClazz(Map<String, Object> param);
	//查询班级个数
	public Integer queryClazzCount(Map<String, Object> param);
	//添加班级
	public void addClazz(Clazz clazz);
	//修改班级信息
	public void updateClazzByNo(Map<String, Object> param);
	//删除班级
	public void deleteClazzByNo(String cNo);
	//根据班级号精确匹配班级
	public Clazz findClazzByNo(String cNo);
	//通过老师找班级
	public Clazz findClazzByTeacher(String tNo);
	//查询该班级的所有学生
	public List<Student> queryClazzAllStu(String cNo);
	//查询该班级的学生人数
	public Integer queryClazzStuCount(Map<String, Object> param);
}
