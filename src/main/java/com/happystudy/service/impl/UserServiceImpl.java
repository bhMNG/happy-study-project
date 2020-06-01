package com.happystudy.service.impl;

import cn.hutool.json.JSONObject;
import com.happystudy.constants.Constants;
import com.happystudy.dao.UserMapper;
import com.happystudy.model.User;
import com.happystudy.service.UserService;
import com.happystudy.util.CipherMachine;
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
    private UserMapper userMapper;
    //登录方法
    @Override
    public JSONObject login(String username, String password) {
        JSONObject json=new JSONObject();
        if (userMapper.findUserByName(username)==null){//根据用户名查询用户是否存在
            json.set("status", Constants.NULL_USER);
            return json;
        }else{
            try{
                //加密处理
                String newPass = CipherMachine.encryption(password);
                User user=userMapper.validate(username,newPass);
                json.set("status",Constants.SUCCESS);
                json.set("user",user);
                return json;
            }catch (Exception e){
                e.printStackTrace();
                return null;
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
            //获取用户密码
            String password2=exitsuser.getuUserpass();
            //对密码进行判断
            if (password1.equalsIgnoreCase(password2)){//密码相同
                User user=userMapper.updateUser(username,newPassword);
                json.set("status",Constants.SUCCESS);
                json.set("user",user);
                return json;
            }else {//密码不相同
                json.set("status",Constants.PASSWORD_ERROR);
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
        List<User> userList=userMapper.findUserByName(username);
        if (userList.size()<0){//新用户不存在
            try{
                String pass=CipherMachine.encryption(password);
                User user=userMapper.registUser(username,pass);
                json.set("status",Constants.SUCCESS);
                json.set("user",user);
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
        List<User> userList=userMapper.findUserByName(username);
        if (userList.size()>0) {//新用户不存在
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
    @Override
    public JSONObject updateUserInfo(Map<String,Object> param) {
        JSONObject json=new JSONObject();
        try{
            List<User> userList=userMapper.findUserByName((String) param.get("u_username"));
            if (userList.size()>0){//用户存在
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
    public JSONObject queryUser(String keyword,String orderby,boolean asc,int pageNo,int pageSize) {
        JSONObject json=new JSONObject();
        Map<String,Object> param=new HashMap<>();
        //计算偏移量
        int offsert=(pageNo-1)*pageSize;
        param.put("keyword",keyword);
        param.put("orderby",orderby);
        param.put("asc",asc);
        param.put("pageNo",offsert);
        param.put("pageSize",pageSize);
        //查询结果并分页
        List<User> userList=userMapper.queryUser(param);
        //查询总条数
        int recCount=userMapper.queryUserCount(keyword);
        //总页数
        int pageCount=recCount/pageSize;
        if (recCount%pageSize>0){
            pageCount++;
        }
        json.set("status",Constants.SUCCESS);
        json.set("user",userList);
        json.set("recCount",recCount);
        json.set("pageCount",pageCount);
        return json;
    }

    //查询用户个人信息
    @Override
    public JSONObject queryUserInfo(String username) {
        JSONObject json=new JSONObject();
        List<User> userList=userMapper.findUserByName(username);
        json.set("status",Constants.SUCCESS);
        json.set("userList",userList);
        return json;
    }

    //查询用户角色名称
    @Override
    public JSONObject queryUserRole(String username) {
        JSONObject json=new JSONObject();
        List<User> userList=userMapper.findUserByName(username);
        if (userList.size()>0){//用户存在
            List<User> users=userMapper.queryUserRole(username);
            json.set("status",Constants.SUCCESS);
            json.set("users",users);
            return json;
        }else {
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
            userMapper.delUserByName(name);
        }
        json.set("status",Constants.SUCCESS);
        return json;
    }
}
