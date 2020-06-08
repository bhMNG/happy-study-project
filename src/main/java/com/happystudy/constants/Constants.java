package com.happystudy.constants;

/**
 * 常量定义
 * @author 杨伟豪
 *
 */
public interface Constants {
	public static final int SUCCESS = 0; //成功
	//用户模块常量
	public static final int NULL_USER = 1; //用户名不存在
	public static final int PASSWORD_ERROR = 2; //密码错误
	public static final String CURRENT_USER_KEY = "currentUser";
	public static final int REGIST_ERROR = 3; //用户已经被注册
	public static final int USER_FORMAT_ERROR = 4; //账号或密码格式错误
	public static final int NULL_PHONE = 5; //没有该手机号
	public static final int PHONE_EXIST=6;//电话号码已存在
	public static final int ALREADY_BOUND=7;//用户已绑定手机

	
	//学生模块
	public static final int SNO_EXIST = 20; //学号已经存在
	public static final int NULL_STU = 21; //没有该学生
	public static final int STU_EDIT_FAILD = 22; //学生编辑失败
	
	//教师模块
	public static final int TNO_EXIST = 30; //教师号已经存在
	public static final int NULL_TEACHER = 31; //该教师不存在
	public static final int TEACHER_EDIT_FAILD = 32; //教师编辑失败
	public static final int ALREADY_PICK= 33; //已经选课
	
	//班级模块
	public static final int CNO_EXIST = 40; // 班级号已经存在
	public static final int NULL_CLAZZ = 41; //班级不存在
	public static final int CLAZZ_EDIT_FAILD = 42; //班级编辑操作失败
	
	//课程模块
	public static final int CONO_EXIST = 50; //课程号已经存在
	public static final int NULL_COURSE = 51; //该课程不存在
	public static final int COURSE_EDIT_FAILD = 52; //课程编辑操作失败
	public static final int COURSE_ALREADY_PICK = 53; //课程编辑操作失败
	
	//学院模块
	public static final int DNO_EXIST = 60; //学院号已经存在
	public static final int NULL_DEPART = 61; //该学院不存在
	public static final int DEPART_EDIT_FAILD = 62; //学院编辑操作失败
	
	
	//db模块常量
	public static final int DB_ERROR = 100;	//后台数据库错误
	
	//权限模块
	public static final int PROP_OUT_OF_BOUNDS = 200; //权限越界
	
	//其他
	public static final int NULL_PARAM_ERROR = 500;	//参数为空错误
	
}
