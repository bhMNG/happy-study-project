package com.happystudy.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.happystudy.constants.Constants;
import com.happystudy.service.impl.StudentServiceImpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Controller
@RequestMapping("/happy-study/student")
public class StudentController {
	@Autowired
    StudentServiceImpl studentService;


	@RequestMapping("")
	public String index() {
		return "student_man";
	}
	
	@PostMapping("/importStudent")
	@ResponseBody
	public String importStudent(MultipartFile stu_excel, HttpServletRequest request) {
		try {
			if (stu_excel.isEmpty()) {
				return "error: 文件为空";
			}
			String fileName = stu_excel.getOriginalFilename();
			String filePath = "G:/Java_learning/fileupload/student/";
			String uuid = IdUtil.fastSimpleUUID();
			File dir = new File(filePath + uuid + "/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File dest = new File(filePath + uuid + "/" + fileName);
			stu_excel.transferTo(dest);
			ExcelReader reader = ExcelUtil.getReader(dest);
			List<Map<String, Object>> readAll = reader.readAll();
			List<JSONObject> stuList = new ArrayList<>();
			for (Map<String, Object> data : readAll) {
				JSONObject studentJson = stuXlsMapToJson(data);
				stuList.add(studentJson);
			}
			//System.out.println(readAll);
			//System.out.println(stuList);
			reader.close();
			FileUtil.del(dest);
			
			String error = studentService.addStudentBatch(stuList);
			if (error != null) {
				return "error: " +error; 
			}else {
				return "success";
			}
		}catch(Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	/**
	 * 将学生的map转为json
	 * @param stuXlsMap
	 * @return
	 */
	private static final Map<String, String> TITLE_MAP = new HashMap<>();
	static {
		TITLE_MAP.put("学号", "s_no");
		TITLE_MAP.put("姓名", "s_name");
		TITLE_MAP.put("性别", "s_sex");
		TITLE_MAP.put("出生日期", "s_birthday");
		TITLE_MAP.put("入学年份", "s_enter_year");
		TITLE_MAP.put("所属班级号", "s_clazz_fk");
		TITLE_MAP.put("所属学院号", "s_depart_fk");
	}
	private JSONObject stuXlsMapToJson(Map<String, Object> stuXlsMap) {
		JSONObject studentJson = new JSONObject();
		Set<String> keySet = stuXlsMap.keySet();
		for (String key : keySet) {
			String jsonKey = TITLE_MAP.get(key);
			Object value = stuXlsMap.get(key);
			if (value instanceof Date) {
				value = DateUtil.format((Date)value, "yyyy-MM-dd");
			}
			studentJson.set(jsonKey, value);
		}
		return studentJson;
	}
	
	@PostMapping("/queryStudent")
	@ResponseBody
    public JSONObject queryStudent(String keyword,String orderBy,String orderWay,Integer pageNo,Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "";
        }

        if (orderBy==null){
            orderBy = "s_no";
        }

        if (orderWay==null){
        	orderWay = "asc";
        }

        if (pageNo==null){
            pageNo=1;
        }

        if (pageSize==null){
            pageSize = 5;
        }

        return this.studentService.queryStudent(keyword, orderBy, orderWay, pageNo, pageSize);
    }
	

	 //查询学生人数（可以根据条件查询，默认为总人数）
    @PostMapping("/queryStudentCount")
    @ResponseBody
    public JSONObject queryStudentCount(Map<String,Object> param){
        if (param.get("keyword")==null)
        {
            param.put("keyword","");
        }
        Map<String,Object> param2 =new HashMap<>();
        param2.put("s_sex","1");
        return this.studentService.queryStudentCount(param2);
    }

    //添加学生
    @PostMapping("/addStudent")
    @ResponseBody
    public JSONObject addStudent(String sNo, String sName, String sSex, String sEnterYear){
    	if (sNo == null || sNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (sName == null || sName.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (sSex == null || sSex.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (sEnterYear == null || sEnterYear.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}

        else return this.studentService.addStudent(sNo,sName,sSex,sEnterYear);
    }

    //根据学号删除学生
    @PostMapping("/deleteStudent")
    @ResponseBody
    public JSONObject deleteStudentByNo(String sNo){
        if ((sNo == null)||sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return studentService.deleteStudentByNo(sNo);
    }

    //根据学号修改学生
    @PostMapping("/updateStudent")
    @ResponseBody
    public JSONObject updateStudentByNo(String sNo, String sName, String sSex, String sEnterYear){
    	Map<String, Object> param = new HashMap<>();
    	if (sNo == null || sNo == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
    	if (sName == null || sName == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
    	if (sSex == null || sSex == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
    	if (sEnterYear == null || sEnterYear == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
    	
    	param.put("sNo", sNo);
    	param.put("sName", sName);
    	param.put("sSex", sSex);
    	param.put("sEnterYear", sEnterYear);
    	System.out.println(param);
        return this.studentService.updateStudentByNo(param);
    }

  //获取学生所在班级
    @PostMapping("/getStudentClazz")
    @ResponseBody
    public JSONObject getStudentClazz(String sNo){
        if (sNo.trim().isEmpty()||sNo.isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.studentService.getStudentClazz(sNo);
        }
    }

  //获取学生成绩
    @PostMapping("/getStudentScore")
    @ResponseBody
    public JSONObject getStudentScore(String sNo){
        if (sNo.trim().isEmpty()||sNo.isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else {
            return this.studentService.getStudentScore(sNo);
        }
    }

    //学生选课 //TODO 暂时注释 service还未实现该方法
    /*@ResponseBody
    public JSONObject courseSelective(String sNo, String cNo){
    	if (sNo == null || sNo == "") {
    		return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
    	}
        
      
        return this.studentService.courseSelective(sNo,cNo);
  
    }*/

    //获取学生已选课程
    @PostMapping("/getStudentCourse")
    @ResponseBody
    public JSONObject getStudentCourse(String sNo){
        if (sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else
        {
            return this.studentService.getStudentCourse(sNo);
        }
    }

    //获取学生的学院
    @PostMapping("/getStudentDepart")
    @ResponseBody
    public JSONObject getStudentDepart(String sNo){
        if (sNo.isEmpty()||sNo.trim().isEmpty())
        {
            return new JSONObject().set("status",Constants.NULL_PARAM_ERROR);
        }
        else return this.studentService.getStudentDepart(sNo);
    }
}
