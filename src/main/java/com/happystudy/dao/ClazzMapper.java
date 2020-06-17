package com.happystudy.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.happystudy.model.Clazz;
import com.happystudy.model.Depart;
import com.happystudy.model.Student;

@Mapper
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
		//根据班级找学院
		// 该班级属于哪个学院不是由负责老师决定的，如果班级的负责老师中途退出，班级变成无负责老师状态，这时班级的学院也不会变。
		// 故负责老师有自己属于的班级，班级也有自己属于的学院。当负责老师去负责不属于自己学院的班级时，会发出提醒
		public Depart queryClazzDepart(String cNo);
		
		//根据旧外键设置新外键(删除外键对象的时候用)
		public void setClazzFk(String FKName, String oldFk, String newFk);
		//查询某个老师负责的班级的人数
		public Integer queryClazzStuCountByTeacherNo(Map<String, Object> param);
}
