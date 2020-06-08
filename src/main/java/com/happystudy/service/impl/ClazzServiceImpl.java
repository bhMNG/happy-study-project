package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.ClazzMapper;
import com.happystudy.model.Clazz;
import com.happystudy.model.Student;
import com.happystudy.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/6/1 21:07
 */
@Service
public class ClazzServiceImpl implements ClazzService {
	@Autowired
    private ClazzMapper clazzMapper;
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
        List<Clazz> mapList = clazzMapper.queryClazz(param);
        //查询总条数
        Integer recCount = clazzMapper.queryClazzCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
            pageCount++;
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
    public JSONObject addClazz(String cNo, String cName,String cEnterYear) {
        JSONObject json=new JSONObject();
        Clazz existClazz = clazzMapper.findClazzByNo(cNo);
        if (existClazz!=null){//班级号已存在
            json.set("status",Constants.CNO_EXIST);
            return json;
        }else {
            Clazz clazz=new Clazz();
            clazz.setcNo(cNo);
            clazz.setcName(cName);
            clazz.setcEnterYear(cEnterYear);
            clazzMapper.addClazz(clazz);
            json.set("status",Constants.SUCCESS);
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
    @Override
    public JSONObject deleteClazzByNo(String cNo) {
        JSONObject json=new JSONObject();
        Clazz existClazz = clazzMapper.findClazzByNo(cNo);
        if (existClazz!=null){
            clazzMapper.deleteClazzByNo(cNo);
            json.set("status",Constants.SUCCESS);
            return json;
        }else {
            json.set("status",Constants.NULL_CLAZZ);
            return json;
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
