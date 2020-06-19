package com.happystudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudy.constants.Constants;
import com.happystudy.dao.CourseMapper;
import com.happystudy.dao.DepartMapper;
import com.happystudy.dao.GradeMapper;
import com.happystudy.dao.StudentMapper;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.model.Course;
import com.happystudy.model.Student;
import com.happystudy.model.Teacher;
import com.happystudy.service.CourseService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author LJS
 * @data 2020/6/2 9:48
 */
@Service
public class CourseServiceImpl implements CourseService {
	 	@Autowired
	    private CourseMapper courseMapper;
	    @Autowired
	    private DepartMapper departMapper;
	    @Autowired
	    private TeacherMapper teacherMapper;
	    @Autowired
	    private GradeMapper gradeMapper;
	    @Autowired
	    private StudentMapper studentMapper;
	    
	    //查询课程
	    @Override
	    public JSONObject queryCourse(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
	        JSONObject json=new JSONObject();
	        Map<String,Object> param=new HashMap<>();
	        //计算偏移量
	        int offsert=(pageNo-1)*pageSize;
	        param.put("keyword",keyword);
	        param.put("orderBy",orderby);
	        param.put("orderWay",asc);
	        param.put("offset",offsert);
	        param.put("pageSize",pageSize);
	        //查询结果并分页
	        List<Map<String, Object>> mapList = courseMapper.queryCourse(param);
	        //任课老师、报课人数
	        for (Map<String, Object> courseMap : mapList) {
	        	Teacher teacher = teacherMapper.findTeacherByCourse((String) courseMap.get("co_no"));
	        	String teacherName = teacher == null ? null : teacher.gettName();
	        	courseMap.put("courseTeacher", teacherName);
	        	//courseMap.put("courseTeacher", (teacherMapper.findTeacherByCourse((String) courseMap.get("co_no")).gettName()));
	        	//System.out.println("----------============" + teacherMapper.findTeacherByCourse((String) courseMap.get("co_no")).gettName());
	        	courseMap.put("courseStuCount", queryCourseStudentCount((String)courseMap.get("co_no")).get("count"));
	        }
	        //查询总条数
	        Integer recCount = courseMapper.queryCourseCount(param);
	        //总页数
	        int pageCount=recCount/pageSize;
	        if (recCount%pageSize>0){
	            pageCount++;
	        }
	        json.set("status", Constants.SUCCESS);
	        json.set("courseArray", JSONUtil.parseArray(mapList));
	        json.set("recCount",recCount);
	        json.set("pageCount",pageCount);
	        return json;
	    }

	    //查询课程数（默认为所有课程）
	    @Override
	    public JSONObject queryCourseCount(Map<String,Object> param) {
	        JSONObject json=new JSONObject();
	        Integer count =courseMapper.queryCourseCount(param);
	        json.set("count",count);
	        json.set("status", Constants.SUCCESS);
	        return json;
	    }

	    //添加课程
	    @Override
	    public JSONObject addCourse(String coNo, String coName) {
	        JSONObject json=new JSONObject();
	        Course existCourse = courseMapper.findCourseByNo(coNo);
	        if (existCourse!=null){//课程号已存在
	            json.set("status",Constants.CONO_EXIST);
	            return json;
	        }else {
	            Course course=new Course();
	            course.setCoName(coName);
	            course.setCoNo(coNo);
	            courseMapper.addCourse(coNo,coName);
	            json.set("status",Constants.SUCCESS);
	            return json;
	        }
	    }

	    //修改课程名
	    @Override
	    public JSONObject updateCourseByNo(String coNo,String coName) {
	        JSONObject json=new JSONObject();
	        Course existCourse = courseMapper.findCourseByNo(coNo);
	        if (existCourse!=null){
	            courseMapper.updateCourseByNo(coNo,coName);
	            json.set("status",Constants.SUCCESS);
	            return json;
	        }else {
	            json.set("status",Constants.NULL_COURSE);
	            return json;
	        }
	    }

	    //根据课程号删除课程
	    @Transactional
	    @Override
	    public JSONObject deleteCourseByNo(String coNo) {
	        JSONObject json=new JSONObject();
	        try {
	        	Course existCourse = courseMapper.findCourseByNo(coNo);
		        if (existCourse==null){//课程不存在
		            json.set("status",Constants.NULL_COURSE);
		            return json;
		        }else {
		        	gradeMapper.setGradeFk(Constants.G_COURSE_FK, coNo, null);
		        	teacherMapper.setTeacherFk(Constants.T_COURSE_FK, coNo, null);
		        	//删除课程表与学生表的中间表中的数据 （当某门课程被删除时）
		        	courseMapper.deleteCourseStuListDataRow(coNo);
		        	courseMapper.deleteCourseApplyDataRow(Constants.CAL_COURSE_FK, coNo);
		            courseMapper.deleteCourseByNo(coNo);
		            json.set("status",Constants.SUCCESS);
		            return json;
		        }
	        }catch(Exception e) {
	        	e.printStackTrace();
	        	return json.set("status", Constants.DB_ERROR);
	        }
	    }
	    

	    //根据课程号精确匹配课程
	    @Override
	    public JSONObject findCourseByNo(String coNo) {
	        JSONObject json=new JSONObject();
	        Course course = courseMapper.findCourseByNo(coNo);
	        if (course!=null){
	            json.set("status",Constants.SUCCESS);
	            json.set("course",course);
	            return json;
	        }else {
	            json.set("status",Constants.NULL_COURSE);
	            return json;
	        }
	    }
	    
	    //通过老师找课程
	    @Override
	    public JSONObject getTeacherCourse(String tNo) {
	        JSONObject json=new JSONObject();
	        Course course = courseMapper.getTeacherCourse(tNo);
	        if (course!=null){
	            json.set("status",Constants.SUCCESS);
	            json.set("course",course);
	            return json;
	        }else {
	            json.set("status",Constants.NULL_COURSE);
	            return json;
	        }
	    }

	    //通过学院找课程
	    @Override
	    public JSONObject findCourseByDepart(String dNo) {
	        JSONObject json=new JSONObject();
	        if (departMapper.findDepartByNo(dNo)==null){
	            return json.set("status",Constants.NULL_DEPART);
	        }
	        else {
	        List<Course> courseList = courseMapper.findCourseByDepart(dNo);
	        json.set("status",Constants.SUCCESS);
	        json.set("courseList",JSONUtil.parseArray(courseList));
	        return json;
	        }
	    }

	    //查询报了该课程的所有学生
	    @Override
	    public JSONObject queryCourseAllStu(String cNo) {
	        JSONObject json=new JSONObject();
	        Course course = courseMapper.findCourseByNo(cNo);
	        if (course==null){
	            json.set("status",Constants.NULL_COURSE);
	            return json;
	        }else {
	            List<Student> studentList = courseMapper.queryCourseAllStu(cNo);
	            json.set("status",Constants.SUCCESS);
	            json.set("studentList",JSONUtil.parseArray(studentList));
	            return json;
	        }
	    }

	    //查询该课程的学生人数
	    @Override
	    public JSONObject queryCourseStudentCount(String coNo) {
	        JSONObject json=new JSONObject();
	        Integer count = courseMapper.queryCourseStudentCount(coNo);
	        json.set("status",Constants.SUCCESS);
	        json.set("count",count);
	        return json;
	    }
	    
	    //根据状态码查询课程
	    @Override
	    public JSONObject queryCourseByStatus(String keyword, String orderBy, String orderWay, 
	    		Integer pageNo, Integer pageSize, Integer status) {
	    	JSONObject result = new JSONObject();
	    	try {
	    		if (status == 0 || status == 1 || status == 2) {
	    			Integer offset=(pageNo-1)*pageSize;
		    		List<Map<String, Object>> courseList = courseMapper.queryCourseByStatus(keyword, orderBy, orderWay, 
		    				offset, pageSize, status);
		    		for (Map<String, Object> course : courseList) {
		    			if (course==null) {
		    				break;
		    			}
		    			course.put("courseStuCount", courseMapper.queryCourseStudentCount((String)course.get("co_no")));
		    		}
		    		result.set("courseArray", JSONUtil.parseArray(courseList));
		    	}else {
		    		result.set("status", Constants.NULL_PARAM_ERROR);
		    	}
		    	result.set("status", Constants.SUCCESS);
		    	return result;
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		result.set("status", Constants.DB_ERROR);
	    		return result;
	    		
	    	}
	    }
	    //设置课程的状态码 (否定申请，执行申请，同意申请)
	    @Transactional
	    @Override
	    public JSONObject setCourseStatus(String tNo, String coNo, Integer status) {
	    	JSONObject result = new JSONObject();
	    	try {
	    		String[] tNos = tNo.split("-");
		    	String[] coNos = coNo.split("-");
		    	if (tNos.length!=coNos.length) {
		    		return result.set("status", Constants.PARAM_FORMATE_ERROR);
		    	}
		    	int size = tNos.length;
		    	for (int i = 0; i < size; i++) {
		    		tNo = tNos[i];
		    		coNo = coNos[i];
		    		courseMapper.setCourseStatus(coNo, status);
			    	if (status == 1) {
			    		try {
			    			try {
				    			courseMapper.insertCourseApplyDataRow(tNo, coNo);
				    		}catch(Exception e) {
				    			//已经存在
				    			courseMapper.deleteCourseApplyDataRow(Constants.CAL_COURSE_FK, coNo);
				    			courseMapper.insertCourseApplyDataRow(tNo, coNo);
				    		}
			    		}catch(Exception e2) {
			    			e2.printStackTrace();
			    			return result.set("status", Constants.DB_ERROR);
			    		}
			    		result.set("status", Constants.SUCCESS);
			    	}else if(status == 2) {
			    		try {
			    			if (teacherMapper.getTeacherCourse(tNo)!=null) {
			    				return result.set("status", Constants.ALREADY_PICK);
			    			}
			    			courseMapper.setCourseStatus(coNo, 2);
			    			courseMapper.deleteCourseApplyDataRow(Constants.CAL_COURSE_FK, coNo);
			    			teacherMapper.setTeacherCourseFkByTNo(tNo, coNo);
			    		}catch(Exception e) {
			    			e.printStackTrace();
			    			return result.set("status", Constants.DB_ERROR);
			    		}
			    		result.set("status", Constants.SUCCESS);
			    	}else if(status == 0) {
			    		try {
			    			courseMapper.setCourseStatus(coNo, 0);
			    			courseMapper.deleteCourseApplyDataRow(Constants.CAL_COURSE_FK, coNo);
			    		}catch(Exception e) {
			    			e.printStackTrace();
			    			return result.set("status", Constants.DB_ERROR);
			    		}
			    		result.set("status", Constants.SUCCESS);
			    	}else {
			    		result.set("status", Constants.NULL_PARAM_ERROR);
			    	}
			    	//return result;
			    }
		    	return result;
		    }catch(Exception ee) {
	    		ee.printStackTrace();
	    		return new JSONObject().set("status", Constants.DB_ERROR);
	    	}
	    }
	    
	    //学生选课（根据学号）
	    @Transactional
	    @Override
	    public JSONObject chooseCourseBySNo(String sNo, String coNo) {
	    	JSONObject result = new JSONObject();
	    	try {
	    		courseMapper.chooseCourseBySNo(sNo, coNo);
	    		return result.set("status", Constants.SUCCESS);
	    	}catch(Exception e) {
	    		e.printStackTrace();
	    		return result.set("status", Constants.DB_ERROR);
	    	}
	    }
	    
	    
	    
}
