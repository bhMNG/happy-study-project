package com.happystudy.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happystudy.constants.Constants;
import com.happystudy.dao.ClazzMapper;
import com.happystudy.dao.StudentMapper;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.model.Clazz;
import com.happystudy.model.Depart;
import com.happystudy.model.Student;
import com.happystudy.model.Teacher;
import com.happystudy.service.ClazzService;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @author LJS
 * @data 2020/6/1 21:07
 */
@Service
public class ClazzServiceImpl implements ClazzService {
	@Autowired
    private ClazzMapper clazzMapper;
	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private StudentMapper studentMapper;
    //查询班级
    @Override
    public JSONObject queryClazz(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
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
        List<Map<String, Object>> mapList = clazzMapper.queryClazz(param);
        //查询总条数
        Integer recCount = clazzMapper.queryClazzCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
            pageCount++;
        }
        for (Map<String, Object> map : mapList) {
        	Teacher teacher = teacherMapper.findTeacherByClazz((String)map.get("c_no"));
        	if (teacher != null && teacher.gettName() != null) {
        		map.put("t_name", teacher.gettName());
        	}
        	Depart depart = clazzMapper.queryClazzDepart((String)map.get("c_no"));
        	if (depart != null && depart.getdName() != null) {
        		map.put("d_name", depart.getdName());
        	}
        	Map<String, Object> p = new HashMap<>();
        	p.put("cNo", (String)map.get("c_no"));
        	Integer stuCount = clazzMapper.queryClazzStuCount(p);
        	if (stuCount != null) {
        		map.put("stu_count", stuCount);
        	}
        }
        
        json.set("status", Constants.SUCCESS);
        json.set("clazzArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询班级个数（默认班级人数）
    @Override
    public JSONObject queryClazzCount(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        Integer count = clazzMapper.queryClazzCount(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }

    //添加班级
    @Override
    public JSONObject addClazz(String cNo, String cName,String cEnterYear, String cDepartFk) {
        JSONObject json=new JSONObject();
        Clazz existClazz = clazzMapper.findClazzByNo(cNo);
        try {
        	if (existClazz!=null){//班级号已存在
                json.set("status",Constants.CNO_EXIST);
                return json;
            }else {
                Clazz clazz=new Clazz();
                clazz.setcNo(cNo);
                clazz.setcName(cName);
                clazz.setcEnterYear(cEnterYear);
                clazz.setcDepartFk(cDepartFk);
                clazzMapper.addClazz(clazz);
                json.set("status",Constants.SUCCESS);
                return json;
            }
        }catch(Exception e) {
        	e.printStackTrace();
        	json.set("status", Constants.DB_ERROR);
        	return json;
        }
    }

    //修改班级信息
    @Override
    public JSONObject updateClazzByNo(String cNo, Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Clazz existClazz = clazzMapper.findClazzByNo(cNo);
        System.out.println(param);
        if (existClazz!=null){//课程已存在
            param.put("cNo",cNo);
            clazzMapper.updateClazzByNo(param);
            json.set("status",Constants.SUCCESS);
            return json;
        }else {
            json.set("status",Constants.NULL_CLAZZ);
            return json;
        }
    }

    //删除班级
    @Transactional
    @Override
    public JSONObject deleteClazzByNo(String[] cNos) {
        JSONObject json=new JSONObject();
        try {
        	for (String cNo : cNos) {
        		Clazz existClazz = clazzMapper.findClazzByNo(cNo);
                if (existClazz!=null){
                    clazzMapper.deleteClazzByNo(cNo);
                    //studentMapper.setStudentClazzFk(cNo, null);
                    studentMapper.setStudentFk(Constants.S_CLAZZ_FK, cNo, null);
                    
                }else {
                    json.set("status",Constants.NULL_CLAZZ);
                    return json;
                }
        	}
        	json.set("status",Constants.SUCCESS);
            return json;
        }catch(Exception e) {
        	e.printStackTrace();
        	return json.set("status", Constants.DB_ERROR);
        }
    }

    //通过老师找班级
    @Override
    public JSONObject findClazzByTeacher(String tNo) {
        JSONObject json=new JSONObject();
        Clazz clazz = clazzMapper.findClazzByTeacher(tNo);
        json.set("status",Constants.SUCCESS);
        json.set("clazz",clazz);
        return json;
    }

    //查询该班级的所有学生
    @Override
    public JSONObject queryClazzAllStu(String cNo) {
        JSONObject json=new JSONObject();
        List<Student> studentList = clazzMapper.queryClazzAllStu(cNo);
        json.set("status",Constants.SUCCESS);
        json.set("studentList",JSONUtil.parseArray(studentList));
        return json;
    }

    //查询该班级的学生人数
    @Override
    public JSONObject queryClazzStuCount(Map<String, Object> param) {
        JSONObject json=new JSONObject();
        Integer count = clazzMapper.queryClazzStuCount(param);
        json.set("status",Constants.SUCCESS);
        json.set("count",count);
        return json;
    }
}
