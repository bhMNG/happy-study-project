package com.happystudy.controller;

import org.json.*;
import cn.hutool.json.JSONObject;
import com.happystudy.service.ClazzService;
import com.happystudy.service.DepartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/happy-study/chart")
public class ChartController {
    @Autowired
    private DepartService departService;
    @Autowired
    private ClazzService clazzService;
    @RequestMapping("/toSchool")
    public String toSchoolChartStatistics(){
        return "chart_school";
    }

    @PostMapping("/getSchoolMsg")
    @ResponseBody
    public JSONObject getSchoolMsg(){
        JSONObject json= new JSONObject();
        StringBuffer line=new StringBuffer();
        //获得学院
        JSONObject departs = this.departService.queryDepart("", "d_no", "asc", 1, 100);
        JSONArray departsArray = new JSONArray(departs.get("departArray").toString());
//      System.out.println(new JSONObject(departsArray.get(0).toString()).get("d_name").toString());//学院名称

        //获得班级
        JSONObject clazzs = this.clazzService.queryClazz("", "c_no", "asc", 1, 100);
        JSONArray clazzsArray = new JSONArray(clazzs.get("clazzArray").toString());

        System.out.println(clazzs);

        //年级map
        Map cEnterMap=new HashMap();

        line.append("{\"name\": \"HappyStudy\",");
        line.append("\"children\": [");
        for (int a=0;a<departsArray.length();a++)//学院
        {
            String departName = new JSONObject(departsArray.get(a).toString()).get("d_name").toString();
            line.append("{\"name\": \""+departName+"\",");
            line.append("\"children\": [");

            for (int b=0;b<clazzsArray.length();b++)//年级
            {

                String grade = new JSONObject(clazzsArray.get(b).toString()).get("c_enter_year").toString();
                if (cEnterMap.get(grade)==null) {
                    cEnterMap.put(grade, 0);
                    line.append("{\"name\": \"" + grade + "级\",");
                    line.append("\"children\": [");

                    for (int c = 0; c < clazzsArray.length(); c++)//班级
                    {
                        String clazzName = new JSONObject(clazzsArray.get(c).toString()).get("c_name").toString();
                        String clazzEnter = new JSONObject(clazzsArray.get(c).toString()).get("c_enter_year").toString();
                        String stu_count = new JSONObject(clazzsArray.get(c).toString()).get("stu_count").toString();
                        if (clazzEnter.equals(grade)){
                            line.append("{\"name\": \"" + clazzName + "\",\"value\":" + stu_count + "},");
                        }
                    }
                    line.append("]},");
                }
            }
            line.append("]},");
        }
        line.append("]}");

        JSONObject obj = new JSONObject(line);
        json.set("data",obj);

        return json;
    }

    @RequestMapping("/toDepart")
    public String toDepartChartStatistics(){
        return "chart_depart";
    }

    @PostMapping("/getDepartMsg")
    @ResponseBody
    public JSONObject getDepartMsg(){
        JSONObject json= new JSONObject();
        JSONObject jsonObject = this.departService.queryDepart("", "d_no", "asc", 1, 100);
        json.set("departs",jsonObject.get("departArray"));
        return json;
    }

    @RequestMapping("/toClazz")
    public String toClazzChartStatistics(){
        return "chart_clazz";
    }



}

