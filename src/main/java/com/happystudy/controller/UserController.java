package com.happystudy.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.happystudy.cache.MyCache;
import com.happystudy.constants.Constants;
import com.happystudy.service.CourseService;
import com.happystudy.service.impl.UserServiceImpl;
import com.happystudy.util.XMLReader;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/happy-study/user")
public class UserController {
	@Autowired
    UserServiceImpl userService;
	@Autowired
	CourseService courseService;
	static {
		XMLReader.initDefaultRoleDutyMap();
	}
	
	@RequestMapping("")
	public String index() {
		return "user_man";
	}
	
	
	@RequestMapping("/propertyMan")
	public String gotoPropMan() {
		return "property_man";
	}
	
	//修改密码
    @PostMapping("/updateUser")
    @ResponseBody
    public JSONObject updateUser(String username, String oldPassword, String newPassword) {
        if (username==null||oldPassword==null||newPassword==null||username.trim().isEmpty()||oldPassword.trim().isEmpty()||newPassword.trim().isEmpty()){
            return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
        }
        else {
            return userService.updateUser(username,oldPassword,newPassword);
        }
    }
    
    //修改密码（Root）
    @PostMapping("/updateUserByRoot")
    @ResponseBody
    public JSONObject updateUserByRoot(String username, String password) {
    	if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (password.equals("reset")) {
    		password="123456";
    	}
    	return userService.updateUserByRoot(username, password);
    }

    //注册用户
    @PostMapping("/registUser")
    @ResponseBody
    public JSONObject registUser(String username, String password, String no) {
        if (username.isEmpty()||username.trim().isEmpty()||password.isEmpty()||password.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else {
            return this.userService.registUser(username,password,no);
        }
    }

    //绑定手机
    @PostMapping("/bindPhone")
    @ResponseBody
    public JSONObject bindPhone(String username, String phonenum) {
        if (username==null||phonenum==null||phonenum.trim().isEmpty()||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else
        {
            return this.userService.bindPhone(username,phonenum);
        }
    }

    //注销
    @PostMapping("/loginOut")
    @ResponseBody
    public void loginOut(HttpServletRequest httpServletRequest)
    {
        httpServletRequest.getSession().removeAttribute(Constants.CURRENT_USER_KEY);
        httpServletRequest.getSession().invalidate();
    }

    //修改用户个人信息
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public JSONObject updateUserInfo(String username, String key, String value)
    {
        if (username==null || username.equals("")) return new JSONObject().set("status",Constants.NULL_USER);
        if (key == null || key.equals("")) return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
        else {
            return this.userService.updateUserInfo(username, key, value);
        }
    }

    //查询已存在用户(5个参数)
    @PostMapping("/queryUser")
    @ResponseBody
    public JSONObject queryUser(String keyword, String orderBy, String orderWay, Integer pageNo, Integer pageSize){
        Map<String,Object> newParam=new HashMap<String,Object>();
        if (keyword==null){
            keyword = "";
        }

        if (orderBy==null){
            newParam.put("orderBy","u_username");
            orderBy = "u_username";
        }

        if (orderWay==null){
            orderWay="asc";
        }

        if (pageNo==null){
            pageNo = 1;
        }

        if (pageSize==null){
            pageSize = 5;
        }

        return this.userService.queryUser(keyword, orderBy, orderWay, pageNo, pageSize);
    }
    //查询用户个人信息
    @PostMapping("/queryUserInfo")
    @ResponseBody
    public JSONObject queryUserInfo(String username){
        if (username==null||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserInfo(username);
    }

    //查询用户角色名称
    @PostMapping("/queryUserRole")
    @ResponseBody
    public JSONObject queryUserRole(String username){
        if (username==null||username.trim().isEmpty()){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.queryUserRole(username);
    }

    //删除用户
    @PostMapping("/delUserByName")
    @ResponseBody
    public JSONObject delUserByName(String username){
        if (username==null||username.trim().isEmpty() ){
            return new JSONObject().set("status",Constants.NULL_USER);
        }
        else return this.userService.delUserByName(username);
    }
  //获取教师选课
//  @PostMapping("/changeTeacherClass")
//  @ResponseBody
//  public JSONObject changeTeacherClass(String tNo, String coNo){
//      if (this.userService.validateTeacherCourse(tNo).get("status")==0) {
//          if (this.userService.validateCourseStatus(coNo).get("status")==0){
//              return  this.userService.doTeacherChooseCourse(tNo,coNo);
//          }
//          else return this.userService.validateCourseStatus(coNo).get("status");
//      }
//      else return this.userService.validateTeacherCourse(tNo).get("status");
//  }
    
    //查找用户的课程
    @PostMapping("/queryUserCourse")
    @ResponseBody
    public JSONObject queryUserCourse(String username, String orderBy, String orderWay) {
    	if (username==null || username==""){
            return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
        }

        if (orderBy==null || orderBy==""){
            orderBy = "co_no";
        }

        if (orderWay==null || orderWay==""){
            orderWay="asc";
        }

        
        return this.userService.queryUserCourse(username, orderBy, orderWay);
    }
    
    @PostMapping("/getUserClazz")
    @ResponseBody
    public JSONObject queryUserClazz(String username) {
    	if (username == null || username == "") {
    		return new JSONObject ().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	return userService.queryUserClazz(username);
    }
    
    //根据用户名查询我的的成绩
    //	代码添加或修改
    @RequestMapping("/queryMyGrade")
    @ResponseBody
    public JSONObject queryMyGrade(String username, String orderBy, String orderWay){
    	if (username == null || username.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (orderBy == null || orderBy.equals("")) {
    		orderBy="co_no";
    	}
    	if (orderWay == null || orderWay.equals("")) {
    		orderWay="asc";
    	}
	    return userService.queryMyGrade(username, orderBy, orderWay);
    }
    
    //上传用户头像
    @RequestMapping("/uploadUserHeadImg")
    @ResponseBody
    public JSONObject uploadUserHeadImg(MultipartFile file, HttpServletRequest request) {
    	JSONObject result = new JSONObject();
    	if (file.isEmpty()) {
    		result.set("status", Constants.NULL_FILE_DIR);
			return result.set("error", "error: 文件为空");
		}
		String fileName = file.getOriginalFilename();
		String filePath = "G:/Java_learning/fileupload/happy-study/user_file/";
		String uuid = IdUtil.fastSimpleUUID();
		File dir = new File(filePath + uuid + "/");
		if (!dir.exists()) {
			dir.mkdir();
		}
		File dest = new File(filePath + uuid + "/" + fileName);
		try {
			file.transferTo(dest);
			result.set("fileUrl", "/fileupload/user_file/" + uuid + "/" + fileName);
			result.set("status", Constants.SUCCESS);
			return result;
		}catch (IOException e) {
			e.printStackTrace();
			result.set("status", Constants.IO_EXCEPTION_ERROR);
			return result;
		}
    }
    
    //学生用户选课
    @PostMapping("/studentChooseCourseByUsername")
    @ResponseBody
    public JSONObject studentChooseCourseByUsername(String username, String coNo) {
    	JSONObject result = new JSONObject();
    	JSONObject roleJson = userService.queryUserRole(username);
    	if (roleJson.getInt("status")!=Constants.SUCCESS) {
    		return result.set("status", Constants.DB_ERROR);
    	}
    	Integer role = ((JSONObject)roleJson.get("role")).getInt("rProperty");
    	if (role!=2) {
    		return result.set("status", Constants.ROLE_ERROR);
    	}
    	JSONObject stuJson = userService.queryUserSNo(username);
    	if (stuJson.getInt("status")!=Constants.SUCCESS) {
    		return result.set("status", Constants.DB_ERROR);
    	}
    	String sNo = stuJson.getStr("sNo");
    	return courseService.chooseCourseBySNo(sNo, coNo);
    }
    
    
    //   权限模块！！
    //创建一个自定义角色
    @PostMapping("/addRole")
    @ResponseBody
    public JSONObject addRole(Integer rProperty, String rName) {
    	if (rProperty==null || rName==null || rName.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (rProperty==0 || rProperty==1 || rProperty==2) {
    		return new JSONObject().set("status", Constants.ROLE_EXIST);
    	}
    	return userService.addRole(rProperty, rName);
    }
    //删除一个自定义角色
    @PostMapping("/deleteRole")
    @ResponseBody
    public JSONObject deleteRole(Integer rProperty) {
    	if (rProperty==null) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (rProperty == 0 || rProperty == 1 || rProperty == 2) {
    		return new JSONObject().set("status", Constants.DEL_BASE_ROLE_ERROR);
    	}
    	return userService.deleteRole(rProperty);
    }
    //查询所有存在于系统中的角色
    @PostMapping("/queryAllRole")
    @ResponseBody
    public JSONObject queryAllRole() {
    	return userService.queryAllRole();
    }
    //查询系统中所有的职责行为
    @PostMapping("/queryAllDuty")
    @ResponseBody
    public JSONObject queryAllDuty() {
    	return userService.queryAllDuty();
    }
    //查询某个角色的职责行为(有的+没有的)
    @PostMapping("/queryRoleDuty")
    @ResponseBody
    public JSONObject queryRoleDuty(Integer rProperty) {
    	return userService.queryRoleDuty(rProperty);
    }
    //给某个角色添加一条职责行为
    @PostMapping("/addRoleDuty")
    @ResponseBody
    public JSONObject addRoleDuty(Integer rProperty, String pNo) {
    	if (rProperty == null || pNo == null || pNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	String[] pNos = pNo.split("-");
    	return userService.addRoleDuty(rProperty, pNos);
    }
    //给某个角色减少一条职责行为
    @PostMapping("/deleteRoleDuty")
    @ResponseBody
    public JSONObject deleteRoleDuty(Integer rProperty, String pNo) {
    	if (rProperty == null || pNo == null || pNo.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	String[] pNos = pNo.split("-");
    	return userService.deleteRoleDuty(rProperty, pNos);
    }
    //修改某个角色的角色名
    @PostMapping("/updateRoleName")
    @ResponseBody
    public JSONObject updateRoleName(Integer rProperty, String rName) {
    	if (rProperty == null || rName == null || rName.equals("")) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	return userService.updateRoleName(rProperty, rName);
    }
    /**
     * 重置角色权限， 基础角色：root无法重置， 教师、学生按照role_prop.xml来重置，若要修改默认的权限，请修改xml文件
     * DIV角色： 直接清空所有duty
     * @param rProperty 角色权限值
     * @return
     */
    @PostMapping("/resetRoleDuty")
    @ResponseBody
    public JSONObject resetRoleDuty(Integer rProperty) {
    	if (rProperty == null) {
    		return new JSONObject().set("status", Constants.NULL_PARAM_ERROR);
    	}
    	if (rProperty == 0 || rProperty == 1 || rProperty == 2) {
    		if (rProperty == 0) {
    			return new JSONObject().set("status", Constants.SUCCESS);
    		}else {
    			//读取xml，进行插入
    			//先清空，再插入
    			JSONObject delJson = userService.deleteRoleAllDuty(rProperty);
    			if (delJson.getInt("status")!=Constants.SUCCESS) {
    				return delJson;
    			}
    			List<String> dutyList = MyCache.DEFAULT_PROP.get(rProperty);
    			String[] pNos = new String[dutyList.size()];
    			dutyList.toArray(pNos);
    			return userService.addRoleDuty(rProperty, pNos);
    		}
    	}else {
    		//DIV角色重置直接清空
    		return userService.deleteRoleAllDuty(rProperty);
    	}
    }
}
