package com.happystudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudy.constants.Constants;
import com.happystudy.dao.ClazzMapper;
import com.happystudy.dao.CourseMapper;
import com.happystudy.dao.DepartMapper;
import com.happystudy.dao.GradeMapper;
import com.happystudy.dao.StudentMapper;
import com.happystudy.model.Clazz;
import com.happystudy.model.Course;
import com.happystudy.model.Depart;
import com.happystudy.model.Grade;
import com.happystudy.model.Student;
import com.happystudy.service.StudentService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author LJS
 * @data 2020/6/1 18:59
 */
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ClazzMapper clazzMapper;
    @Autowired
    private DepartMapper departMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private CourseMapper courseMapper;
    
    @Transactional
    @Override
    public String addStudentBatch(List<JSONObject> stuList) {
    	StringBuffer error = new StringBuffer();
    	try {
    		for (JSONObject studentJson : stuList) {
        		JSONObject res = doAddStudent(studentJson);
        		int status = res.getInt("status");
        		String err = res.getStr("error");
        		if (status != Constants.SUCCESS) {
        			//导入的过程中出错了
        			error.append(status + ": " + err + ";");
        		}
        	}
        	if (error.length()>0) {
        		error.deleteCharAt(error.length()-1);
        		return error.toString();
        	}
    	}catch(Exception e) {
    		error.append(e.getMessage());
    		return error.toString();
    	}
    	return null;	
    }
    /**
     * 无事务控制的添加学生
     */
    private JSONObject doAddStudent(JSONObject studentJson) {
    	JSONObject result = new JSONObject();
    	String sNo = studentJson.getStr("s_no");
    	String sName = studentJson.getStr("s_name");
    	String sBirthday = studentJson.getStr("s_birthday");
    	String sSex = studentJson.getStr("s_sex");
    	String sEnterYear = studentJson.getStr("s_enter_year");
    	String sClazzFk = studentJson.getStr("s_clazz_fk");
    	String sDepartFk = studentJson.getStr("s_depart_fk");
    	if(studentMapper.findStudentByNo(sNo)!=null) {
    		result.set("status", Constants.STU_EDIT_FAILD);
    		result.set("error", "学生: " + sNo + " 已存在");
    		return result;
    	}
    	if(sClazzFk!=null&&(!sClazzFk.equals(""))&&clazzMapper.findClazzByNo(sClazzFk)==null) {
    		result.set("status", Constants.NULL_CLAZZ);
    		result.set("error", "班级: " + sClazzFk + " 不存在");
    		return result;
    	}
    	if(sDepartFk!=null&&(!sDepartFk.equals(""))&&departMapper.findDepartByNo(sDepartFk)==null) {
    		result.set("status", Constants.NULL_DEPART);
    		result.set("error", "学院: " + sDepartFk + " 不存在");
    		return result;
    	}
    	try {
    		studentMapper.doAddStudent(sNo,sName,sBirthday,sSex,sEnterYear,sClazzFk,sDepartFk);
    		result.set("status", Constants.SUCCESS);
    		return result;
    	}catch(Exception e) {
    		e.printStackTrace();
    		result.set("status", Constants.DB_ERROR);
    		return result;
    	}
    	
    }
    
    //根据关键字查询学生信息（排序、分页）（5个参数）
    @Override
    public JSONObject queryStudent(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
        JSONObject json=new JSONObject();
        Map<String,Object> param=new HashMap<>();
        //计算偏移量
        int offsert=(pageNo-1)*pageSize;
        System.out.println("++++++++++++++++++++++ "+offsert);
        System.out.println("++++++++++++++++++++++ "+pageNo);
        param.put("keyword",keyword);
        param.put("orderBy",orderby);
        param.put("orderWay",asc);
        param.put("offset",offsert);
        param.put("pageSize",pageSize);
        //查询结果并分页
        List<Map<String, Object>> mapList = studentMapper.queryStudent(param);
        System.out.println(studentMapper.queryStudent(param));
        //查询总条数
        Integer recCount= studentMapper.queryStudentCount(null);
        
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
            pageCount++;
        }
        System.out.println("----------------------------" + pageCount);
        json.set("status",Constants.SUCCESS);
        json.set("studentArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }
    

    //查询学生人数（可以根据条件查询，默认为总人数）
    @Override
    public JSONObject queryStudentCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count = studentMapper.queryStudentCount(param);
        json.set("count",count);
        json.set("status", Constants.SUCCESS);
        return json;
    }

    //添加学生
    @Override
    public JSONObject addStudent(String sNo, String sName, String sSex, String sEnterYear) {
        JSONObject json=new JSONObject();
        Student existStudent = studentMapper.findStudentByNo(sNo);
        if (existStudent!=null){//学号已存在
            json.set("status",Constants.SNO_EXIST);
            return json;
        }else {
            studentMapper.addStudent(sNo,sName,sSex,sEnterYear);
            json.set("status",Constants.SUCCESS);
            return json;
        }
    }

    //根据学号删除学生
    @Transactional
    @Override
    public JSONObject deleteStudentByNo(String[] sNos) {
        JSONObject json=new JSONObject();
        try {
        	for (String sNo : sNos) {
        		Student existStudent = studentMapper.findStudentByNo(sNo);
                if (existStudent==null){//学号不存在
                    json.set("status",Constants.NULL_STU);
                    return json;
                }else {
                    studentMapper.deleteStudentByNo(sNo);
                    gradeMapper.setGradeFk(Constants.G_STUDENT_FK, sNo, null);
                    courseMapper.setCourseStuListFk(Constants.CSL_STUDENT_FK, sNo, null);
                }
        	}
        	json.set("status",Constants.SUCCESS);
            return json;
        }catch(Exception e) {
        	e.printStackTrace();
        	return json.set("status", Constants.DB_ERROR);
        }
    }

    //根据学号修改学生
    @Override
    public JSONObject updateStudentByNo(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        try {
        	studentMapper.updateStudentByNo(param);
        }catch(Exception e) {
        	e.printStackTrace();
        	json.set("status", Constants.DB_ERROR);
        	return json;
        }
        
        json.set("status",Constants.SUCCESS);
        return json;
    }

    //获取学生所在班级
    @Override
    public JSONObject getStudentClazz(String sNo) {
        JSONObject json=new JSONObject();
        Clazz clazz = studentMapper.getStudentClazz(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("clazz",clazz);
        return json;
    }

    //获取学生成绩
    @Override
    public JSONObject getStudentScore(String sNo) {
        JSONObject json=new JSONObject();
        List<Grade> gradeList = studentMapper.getStudentGrade(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("gradeArray", JSONUtil.parseArray(gradeList));
        return json;
    }

    //获取学生已选课程
    @Override
    public JSONObject getStudentCourse(String sNo) {
        JSONObject json=new JSONObject();
        List<Course> courseList = studentMapper.getStudentCourse(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("courseArray", JSONUtil.parseArray(courseList));
        return json;
    }

    //获取学生的学院
    @Override
    public JSONObject getStudentDepart(String sNo) {
        JSONObject json=new JSONObject();
        Depart depart = studentMapper.getStudentDepart(sNo);
        json.set("status",Constants.SUCCESS);
        json.set("depart",depart);
        return json;
    }
    
  //根据学生学号获取学生的详细信息
    @Override
    public JSONObject getStudentInfoByNo(String sNo) {
    	JSONObject result = new JSONObject();
    	JSONObject stuInfo = new JSONObject();
    	try {
    		Student stu = studentMapper.findStudentByNo(sNo);
        	stuInfo.set("sNo", sNo);
        	stuInfo.set("sName", stu.getsName());
        	stuInfo.set("sSex", stu.getsSex());
        	stuInfo.set("sBirthday", stu.getsBrithday());
        	stuInfo.set("sEnterYear", stu.getsEnterYear());
        	Clazz stuClazz = studentMapper.getStudentClazz(sNo);
        	if (stuClazz==null) {
        		stuInfo.set("cName", "暂无");
        	}else {
        		stuInfo.set("cName", stuClazz.getcName());
        	}
        	Depart stuDepart = studentMapper.getStudentDepart(sNo);
        	if (stuDepart==null) {
        		stuInfo.set("dName", "暂无");
        	}else {
        		stuInfo.set("dName", stuDepart.getdName());
        	}
        	
        	result.set("stuInfo", stuInfo);
        	result.set("status", Constants.SUCCESS);
        	return result;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return result.set("status", Constants.DB_ERROR);
    	}
    }
}
