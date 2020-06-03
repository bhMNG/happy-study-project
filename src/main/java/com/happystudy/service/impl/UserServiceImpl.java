package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.happystudy.constants.Constants;
import com.happystudy.dao.UserMapper;
import com.happystudy.model.User;
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
        User existUser=userMapper.findUserByName(username);
        if (existUser==null){//根据用户名查询用户不存在
            JSONObject result = loginByPhone(username, password);
            //现在查找用户名不存在的情况下，手机号是否存在
            if (result.getBool("isSuccess")){//手机号验证成功
                json.set("phonenum",username);
                json.set("username",result.getStr("username"));
                json.set("password",password);
                //todo 缓存操作
                json.set("status",Constants.SUCCESS);
                json.remove("password");
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
                    json.set("user",existUser);
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
                    userMapper.updateUser(username,newPassword);
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
        if (exitsUser==null) {//新用户不存在
            json.set("status",Constants.NULL_USER);
            return json;
        }else {
            if (exitsUser.getuPhone()==null){//未绑定
                if(userMapper.findUserByPhone(phonenum)==null){//电话号码唯一
                    User user=userMapper.bindPhone(username,phonenum);
                    json.set("status",Constants.SUCCESS);
                    json.set("user",user);
                    return json;
                }else {
                    json.set("status",Constants.PHONE_EXIST);
                    return json;
                }
            }
            json.set("status",Constants.NULL_PHONE);
            return json;
        }
    }

    //修改用户个人信息
    @Transactional
    @Override
    public JSONObject updateUserInfo(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        try{
            User existUser=userMapper.findUserByName((String)param.get("u_username"));
            if (existUser!=null){//用户存在
                userMapper.updateUserInfo(param);
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
        List<Map<String, Object>> mapList = userMapper.queryUser(param);
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
         User existUser=userMapper.queryUserInfo(username);
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
            User user = userMapper.queryUserRole(username);
            json.set("status",Constants.SUCCESS);
            json.set("userArray",user);
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
        User user = userMapper.queryUserProp(username);
        if (user!=null){
            json.set("status",Constants.SUCCESS);
            json.set("user",user);
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
}
