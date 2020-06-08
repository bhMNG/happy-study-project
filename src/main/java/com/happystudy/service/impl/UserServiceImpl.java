package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.CourseMapper;
import com.happystudy.dao.TeacherMapper;
import com.happystudy.dao.UserMapper;
import com.happystudy.model.Course;
import com.happystudy.model.Property;
import com.happystudy.model.Role;
import com.happystudy.model.Teacher;
import com.happystudy.model.User;
import com.happystudy.model.UserInfo;
import com.happystudy.service.UserService;
import com.happystudy.util.CipherMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LJS
 * @data 2020/5/31 14:05
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;

    //手机号码验证登录
    private JSONObject loginByPhone(String phone,String password){
        JSONObject json=new JSONObject();
        try{
            User user=userMapper.findUserByPhone(phone);
            //对密码进行加密处理
            String pass1=CipherMachine.encryption(password);
            if (user==null){//电话号码不存在
                json.set("isSuccess",false);
                return json;
            }else {
                //得到用户名
                String username = user.getuUsername();
                //得到用户密码
                String pass2=user.getuUserpass();
                if (pass1.equalsIgnoreCase(pass2)){
                    json.set("isSuccess",true);
                    json.set("username",username);
                    json.set("userProperty", user.getuRoleFk());
                    return json;
                }else {
                    json.set("isSuccess",false);
                    return json;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            json.set("error",e.getMessage());
            return json;
        }
    }

    //登录方法
    @Override
    public JSONObject login(String username, String password) {
        JSONObject json=new JSONObject();
        JSONObject curuser = new JSONObject(); //放入session的
        User existUser=userMapper.findUserByName(username);
        if (existUser==null){//根据用户名查询用户不存在
            JSONObject result = loginByPhone(username, password);
            //现在查找用户名不存在的情况下，手机号是否存在
            if (result.getBool("isSuccess")){//手机号验证成功
                //json.set("phonenum",username);
                //json.set("username",result.getStr("username"));
                json.set("password",password);
                //todo 缓存操作
                json.set("status",Constants.SUCCESS);
                //json.set("userProperty", result.get("userProperty"));
                //json.set("userPosition", getUserPosition(username));
                json.remove("password");
                curuser.set("username", result.getStr("username"));
                curuser.set("phonenum", username);
                curuser.set("userProperty", result.get("userProperty"));
                curuser.set("userPosition", getUserPosition(username));
              //获取用户头像和idcard
                UserInfo userInfo = userMapper.queryUserInfo(username);
              //JSONObject userJson = (JSONObject)queryUserInfo(username).get("user");
                if (userInfo != null) {
                	String userPhoto = userInfo.getiPhoto();
                    String userIdCard = userInfo.getiIdcard();
                    if (userPhoto != null) {
                    	curuser.set("userPhoto", userPhoto);
                    }
                    if (userIdCard != null) {
                    	curuser.set("userIdCard", userIdCard);
                    }
                }
                json.set("user", curuser);
                return json;
            }else {//手机号验证失败
                json.set("status", Constants.NULL_USER);
                return json;
            }
        }else{//用户名存在，则不是手机号，走用户名登录逻辑
            try{
                //获取该用户的数据库里面的密码
                String pass=existUser.getuUserpass();
                //加密处理
                String newPass = CipherMachine.encryption(password);
                if (pass.equalsIgnoreCase(newPass)){//密码正确
                    json.set("status",Constants.SUCCESS);
                    //json.set("user",existUser);
                    curuser.set("username", existUser.getuUsername());
                    curuser.set("userProperty", existUser.getuRoleFk());
                    curuser.set("userPosition", getUserPosition(username));
                    curuser.set("phonenum", existUser.getuPhone());
                    //获取用户头像和idcard
                    UserInfo userInfo = userMapper.queryUserInfo(username);
                    
                    if (userInfo != null) {
                    	String userPhoto = userInfo.getiPhoto();
                        String userIdCard = userInfo.getiIdcard();
                        if (userPhoto != null) {
                        	curuser.set("userPhoto", userPhoto);
                        }
                        if (userIdCard != null) {
                        	curuser.set("userIdCard", userIdCard);
                        }
                    }
                    json.set("user", curuser);
                    //json.set("username", existUser.getuUsername());
                    //json.set("userProperty", existUser.getuRoleFk());
                    //json.set("userPostion", getUserPosition(username));
                    System.out.println(json);
                    return json;
                }else {
                    json.set("status",Constants.PASSWORD_ERROR);
                    return json;
                }
            }catch (Exception e){
                e.printStackTrace();
                json.set("error",e.getMessage());
                return json;
            }
        }
    }
    //获取职权名
    private String getUserPosition(String username) {
    	String userPosition = (String)userMapper.queryUserPosition(username).get("r_name");
        return userPosition ;
        
    }

    //修改密码
    @Override
    public JSONObject updateUser(String username, String oldPassword, String newPassword) {
        JSONObject json=new JSONObject();
        try{
            //对原始密码加密处理
            String password1=CipherMachine.encryption(oldPassword);
            //根据用户名查询用户
            User exitsuser=userMapper.findUserByName(username);
            if (exitsuser!=null){//用户名存在
                //获取用户密码
                String password2=exitsuser.getuUserpass();
                //对密码进行判断
                if (password1.equalsIgnoreCase(password2)){//密码相同
                    userMapper.updateUser(username,CipherMachine.encryption(newPassword));
                    json.set("status",Constants.SUCCESS);
                    return json;
                }else {//密码不相同
                    json.set("status",Constants.PASSWORD_ERROR);
                    return json;
                }
            }else {//用户名不存在
                json.set("status",Constants.NULL_USER);
                return json;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //注册用户
    @Override
    public JSONObject registUser(String username, String password) {
        JSONObject json=new JSONObject();
        User exitsUser=userMapper.findUserByName(username);
        if (exitsUser==null){//新用户不存在
            try{
                User user=new User();
                String pass=CipherMachine.encryption(password);
                user.setuUsername(username);
                user.setuUserpass(pass);
                userMapper.addUser(user);
                json.set("status",Constants.SUCCESS);
                return json;
            }catch (Exception e){
                e.printStackTrace();
                json.set("error",e.getMessage());
                return json;
            }
        }else {//新用户已存在
            json.set("status",Constants.REGIST_ERROR);
            return json;
        }
    }

    //绑定手机
    @Override
    public JSONObject bindPhone(String username, String phonenum) {
        JSONObject json=new JSONObject();
        User exitsUser=userMapper.findUserByName(username);
        if (exitsUser==null) {//用户不存在
            json.set("status",Constants.NULL_USER);
            return json;
        }else {
            if (exitsUser.getuPhone()==null||exitsUser.getuPhone().trim().isEmpty()){//未绑定
                if(userMapper.findUserByPhone(phonenum)==null){//电话号码唯一
                    userMapper.bindPhone(username,phonenum);
                    json.set("status",Constants.SUCCESS);
                    return json;
                }else {
                    json.set("status",Constants.PHONE_EXIST);
                    return json;
                }
            }
            json.set("status",Constants.ALREADY_BOUND);
            return json;
        }
    }

    //修改用户个人信息
    @Transactional
    @Override
    public JSONObject updateUserInfo(String username,String key, String value) {
        JSONObject json=new JSONObject();
        try{
            User existUser=userMapper.findUserByName(username);
            if (existUser!=null){//用户存在
            	if (userMapper.queryUserInfo(username)==null) {
            		userMapper.insertUserInfo(username, key, value);
            	}
                userMapper.updateUserInfo(username, key, value);
                json.set("status",Constants.SUCCESS);
                return json;
            }else {
                json.set("status",Constants.NULL_USER);
                return json;
            }
        }catch (Exception e){
            e.printStackTrace();
            json.set("status",Constants.DB_ERROR);
            json.set("error",e.getMessage());
            return json;
        }
    }

    //查询已存在用户（5个参数）
    @Override
    public JSONObject queryUser(String keyword,String orderby,String asc,Integer pageNo,Integer pageSize) {
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
        List<Map<String ,Object>> mapList = userMapper.queryUser(param);
        //查询总条数
        int recCount=userMapper.queryUserCount(param);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
            pageCount++;
        }
        json.set("status",Constants.SUCCESS);
        json.set("userArray", JSONUtil.parseArray(mapList));
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询用户个人信息
    @Override
    public JSONObject queryUserInfo(String username) {
        JSONObject json=new JSONObject();
         UserInfo existUser=userMapper.queryUserInfo(username);
         if (existUser!=null){
             json.set("status",Constants.SUCCESS);
             json.set("user",existUser);
             return json;
         }else {
             json.set("status",Constants.NULL_USER);
             return json;
         }
    }

    //查询用户角色
    @Override
    public JSONObject queryUserRole(String username) {
        JSONObject json=new JSONObject();
        User existUser=userMapper.findUserByName(username);
        if (existUser!=null){//用户存在
            Role role = userMapper.queryUserRole(username);
            json.set("status",Constants.SUCCESS);
            json.set("role",role);
            return json;
        }else {
         json.set("status",Constants.NULL_USER);
         return json;
        }
    }

    //查询用户权限职责信息
    @Override
    public JSONObject queryUserProp(String username) {
        JSONObject json=new JSONObject();
        Property userProp = userMapper.queryUserProp(username);
        if (userProp!=null){
            json.set("status",Constants.SUCCESS);
            json.set("userProp",userProp);
            return json;
        }else{
            json.set("status",Constants.NULL_USER);
            return json;
        }
    }

    //删除用户
    @Transactional
    @Override
    public JSONObject delUserByName(String username) {
        JSONObject json=new JSONObject();
        String[] userNameArrray=username.split(",");
        for (String name:userNameArrray
             ) {
            if (userMapper.findUserByName(name)!=null){
                userMapper.delUserByName(name);
                json.set("status",Constants.SUCCESS);
            }else {
                json.set("status",Constants.NULL_USER);
            }
        }
        return json;
    }

    @Override
    public JSONObject validateTeacherCourse(String tNo) {
        JSONObject json=new JSONObject();
        Teacher teacher = teacherMapper.findTeacherByNo(tNo);
        if (teacher==null)
        {
            return json.set("status",Constants.NULL_TEACHER);
        }

        if (teacher.gettCourseFk()==null){
            return json.set("status",Constants.SUCCESS);
        }
        else {
            return json.set("status",Constants.ALREADY_PICK);
        }
    }

    @Override
    public JSONObject validateCourseStatus(String coNo) {
        JSONObject json=new JSONObject();
        Course course = courseMapper.findCourseByNo(coNo);
        if (course==null)
        {
            return json.set("status",Constants.NULL_COURSE);
        }
        else {
            if (course.getCoStatus()==2||course.getCoStatus()==0)
            {
                return json.set("status", Constants.ALREADY_PICK);
            }
            else return json.set("status",Constants.SUCCESS);
        }
    }

    //通过老师申请
    @Override
    public JSONObject doTeacherChooseCourse(String tNo, String coNo) {
        JSONObject json=new JSONObject();
        userMapper.setCourseStatus(coNo,"2");
        userMapper.setTeacherCourseFk(tNo,coNo);
        return json.set("status",Constants.SUCCESS);
    }
    

    @Override
    public JSONObject queryUserCourseGrade(String username,String orderby,String asc,Integer pageNo,Integer pageSize) {
    	JSONObject json=new JSONObject();
        Map<String,Object> param=new HashMap<>();
        int offset=(pageNo-1)*pageSize;
        param.put("username",username);
        param.put("orderBy",orderby);
        param.put("orderWay",asc);
        param.put("offset",offset);
        param.put("pageSize",pageSize);
        //查询结果并分页
        List<Map<String, Object>> mapList = userMapper.queryUserCourseGrade(param);
        //查询总条数
        Integer recCount = userMapper.queryUserCourseCount(username);
        
        //总页数
        int pageCount = recCount/pageSize;
        if (recCount % pageSize>0) {
        	pageCount++;
        }
        System.out.println("----------------------------" + pageCount);
        json.set("status", Constants.SUCCESS);
        json.set("studentArray", JSONUtil.parseArray(mapList));
        json.set("recCount", recCount);
        json.set("pageCount", pageCount);
        return json;
        
    }
    
    @Override
    public JSONObject queryUserCourse(String username, String orderBy, String orderWay){
    	JSONObject result = new JSONObject();
    	List<Map<String, Object>> mapList;
    	Integer property = userMapper.queryUserRole(username).getrProperty();
    	if (property.equals(1)) {
    		mapList = userMapper.queryTeacherUserCourse(username, orderBy, orderWay);
    	}else if(property.equals(2)) {
    		mapList = userMapper.queryStudentUserCourse(username, orderBy, orderWay);
    	}else if(property.equals(0)){
    		//按照老师的来
    		mapList = userMapper.queryTeacherUserCourse(username, orderBy, orderWay);
    		
    	}else {
    		return result.set("status", Constants.DB_ERROR);
    	}
    	
    	result.set("courseArray", JSONUtil.parseArray(mapList));
    	result.set("status", Constants.SUCCESS);
    	return result;
    	
    }
}
