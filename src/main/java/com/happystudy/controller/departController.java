package com.happystudy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.happystudy.constants.Constants;
import com.happystudy.service.DepartService;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/depart")
public class departController {
	@Autowired
    private DepartService departService;
	
	@RequestMapping("")
	public String index() {
		return "depart_man";
	}
	
	@PostMapping("/queryDepart")
    @ResponseBody
    public JSONObject queryDepart(String keyword, String orderBy, String orderWay, Integer pageNo, Integer pageSize)
    {
        if (keyword==null || keyword.equals("")){
            keyword = "";
        }
        if (orderBy==null || orderBy.equals("")){
            orderBy = "d_no";
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
        return this.departService.queryDepart(keyword, orderBy, orderWay, pageNo, pageSize);
    }

//    //查询学院个数（默认为总个数）
//    @PostMapping("/queryDepartCount")
//    @ResponseBody
//    public JSONObject queryDepartCount(String keyword, String keyType){
//        Map<String, Object> param = new HashMap<>();
//        if (keyType == null || keyType.equals("")) {
//            param.put("keyType", "");
//            param.put("keyword", "");
//        }else if(keyword.equals("d_no")) {
//            param.put(keyType, "d_no");
//            param.put("keyword", keyword);
//        }
//
//        return this.departService.queryDepartCount(param);
//    }




    //添加学院
    @PostMapping("/addDepart")
    @ResponseBody
    public JSONObject addDepart(String dNo, String dName){
        if (dNo==null||dName==null||dNo.trim().isEmpty()||dName.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.addDepart(dNo,dName);
    }

    //修改学院名字
    @PostMapping("/updateDepart")
    @ResponseBody
    public JSONObject updateDepartByNo(String dNo, String dName){
        if (dNo==null||dName==null||dName.trim().isEmpty()||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }

        return this.departService.updateDepartByNo(dNo,dName);
    }


    //根据学院号删除学院
    @PostMapping("/deleteDepart")
    @ResponseBody
    public JSONObject deleteDepartByNo(String dNo){
    	String[] dNos = dNo.split("-");
        if (dNo==null||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.deleteDepartByNo(dNos);
    }


    //根据学院号精准匹配学院
    @PostMapping("/findDepartByNo")
    @ResponseBody
    public JSONObject findDepartByNo(String dNo){
        if (dNo==null||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.findDepartByNo(dNo);
    }

    //查找某个学院的课程
    @PostMapping("/getDepartCourse")
    @ResponseBody
    public JSONObject getDepartCourse(String dNo){
        if (dNo==null||dNo.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.getDepartCourse(dNo);
    }

    //统计该学院的学生人数
    @PostMapping("/queryDepartAllStu")
    @ResponseBody
    public JSONObject queryDepartAllStu(String dNo)
    {
        if (dNo==null||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllStu(dNo);
    }
    //统计该学院的课程总数
    @PostMapping("/queryDepartAllCourse")
    @ResponseBody
    public JSONObject queryDepartAllCourse(String dNo){
        if (dNo==null||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllCourse(dNo);
    }
    //统计该学院的老师人数
    @PostMapping("/queryDepartAllTeaCount")
    @ResponseBody
    public JSONObject queryDepartAllTeaCount(String dNo){
        if (dNo==null||dNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.departService.queryDepartAllTeaCount(dNo);
    }

}
