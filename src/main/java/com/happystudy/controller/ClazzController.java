package com.happystudy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.impl.ClazzServiceImpl;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/clazz")
public class ClazzController {
	@Autowired
    ClazzServiceImpl clazzService;

	@RequestMapping("")
	public String index() {
		return "clazz_man";
	}
	
	//查询班级（5个参数）
	@PostMapping("/queryClazz")
	@ResponseBody
	public JSONObject queryClazz(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize){
			if (keyword==null || keyword.equals("")){
			keyword = "";
	        }
	        if (orderBy==null || orderBy.equals("")){
	            orderBy = "c_no";
	        }

	        if (orderWay==null || orderWay.equals("")){
	            orderWay = "asc";
	        }

	        if (pageNo==null || pageNo.equals("")){
	            pageNo = 1;
	        }

	        if (pageSize==null || pageSize.equals("")){
	            pageSize = 5;
	        }

	        return this.clazzService.queryClazz(keyword, orderBy, orderWay, pageNo, pageSize);
	 }

	//查询班级个数（默认为所有班级个数）
		@PostMapping("/queryClazzCount")
	    @ResponseBody
	    public JSONObject queryClazzCount(String keyword, String keyType){
	    	Map<String, Object> param = new HashMap<>();
	    	if (keyType == null || keyType.equals("")) {
	    		param.put("keyType", "");
	    		param.put("keyword", "");
	    	}else if (keyType.equals("d_no")) {
	    		param.put("keyType", keyType);
	    		param.put("keyword", keyword);
	    	}
	    	//maby TODO
	    	
	        return this.clazzService.queryClazzCount(param);
	    }


		//添加班级
	@PostMapping("/addClazz")
	@ResponseBody
	public JSONObject addClazz(String cNo, String cName,String cEnterYear){
		if (cNo==null||cName==null||cEnterYear==null||cNo.trim().isEmpty()||cName.trim().isEmpty()||cEnterYear.trim().isEmpty())
	    {
			return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
	    }
	        else return this.clazzService.addClazz(cNo,cName,cEnterYear);
	}

	//修改班级信息
    @PostMapping("/updateClazzByNo")
    @ResponseBody
    public JSONObject updateClazzByNo(String cNo,String cName,String cEnterYear){
	    if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_CLAZZ);
        }
        else {
            Map<String,Object> newParam=new HashMap<String,Object>();
            if (cName!=null)
            {
                newParam.put("cName",cName);
            }
            if (cEnterYear!=null)
            {
                newParam.put("cEnterYear",cEnterYear);
            }
            return this.clazzService.updateClazzByNo(cNo,newParam);
        }
    }

  //删除班级
    @PostMapping("/deleteClazzByNo")
    @ResponseBody
    public JSONObject deleteClazzByNo(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else{
            return this.clazzService.deleteClazzByNo(cNo);
        }
    }

    //根据班级号精确匹配班级 TODO 用到再说
    /*@ResponseBody
    public JSONObject findClazzBycNo(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.findClazzByNo(cNo);
    }*/

  //通过老师找班级
    @PostMapping("/findClazzByTeacher")
    @ResponseBody
    public  JSONObject findClazzByTeacher(String tNo){
        if (tNo.trim().isEmpty()||tNo.isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.findClazzByTeacher(tNo);
    }


  //查询该班级的所有学生
    @PostMapping("/queryClazzAllStu")
    @ResponseBody
    public JSONObject queryClazzAllStu(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.clazzService.queryClazzAllStu(cNo);
    }

  //查询该班级的学生人数
    @PostMapping("/queryClazzStuCount")
    @ResponseBody
    public JSONObject queryClazzStuCount(String cNo){
        if (cNo.isEmpty()||cNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        Map<String, Object> param = new HashMap<>();
        param.put("cNo", cNo);
        return this.clazzService.queryClazzStuCount(param);
    }
}
