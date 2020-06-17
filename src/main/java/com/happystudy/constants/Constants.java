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
	public static final String C_TEACHER_FK = "c_teacher_fk"; //班级表里的教师外键
	public static final String C_DEPART_FK = "c_depart_fk"; //班级表里的学院外键
	public static final String G_STUDENT_FK = "g_student_fk"; //成绩表里的学生外键
	public static final String G_COURSE_FK = "g_course_fk"; //成绩表里的课程外键
	@Deprecated
	public static final String P_ROLE_FK = "p_role_fk"; //权限表里的角色外键
	public static final String S_CLAZZ_FK = "s_clazz_fk"; //学生表里班级的外键
	public static final String S_DEPART_FK = "s_depart_fk"; //学生表里的学院外键
	@Deprecated
	public static final String S_ROLE_FK = "s_role_fk"; //学生表里的角色外键
	public static final String S_USER_FK = "s_user_fk"; //学生表里的用户外键 
	public static final String T_DEPART_FK = "t_depart_fk"; //教师表里的学院外键
	public static final String T_COURSE_FK = "t_course_fk"; //教师表里的课程外键
	@Deprecated
	public static final String T_ROLE_FK = "t_role_fk"; //教师表里的角色外键
	public static final String T_USER_FK = "t_user_fk"; //教师表里的用户外键
	public static final String U_ROLE_FK = "u_role_fk"; //用户表里的角色外键
	public static final String I_USERNAME_FK = "i_username_fk"; //用户信息表里的用户外键
	public static final String CSL_COURSE_FK = "csl_course_fk"; //课程学生名单表的（中间表） 课程外键
	public static final String CSL_STUDENT_FK = "csl_student_fk"; //课程名单表（中间表） 学生外键
	public static final String CAL_TEACHER_FK = "cal_teacher_fk"; //选课状态表（为1带批准）的教师外键
	public static final String CAL_COURSE_FK = "cal_course_fk"; //选课状态表（为1带批准）的课程外键
	public static final String RAL_ROLE_FK = "ral_role_fk"; //权限职责清单（role-prop中间表）的role外键，连接r_property
	public static final String RAL_PROP_FK = "ral_prop_fk"; //权限职责清单（role-prop中间表）的prop外键，连接p_no
	
	
	//文件模块
	public static final int NULL_FILE_DIR = 300; //没有该目录或该目录为空
	public static final int IO_EXCEPTION_ERROR = 310; //IO错误
	
	//权限模块
	public static final int PROP_OUT_OF_BOUNDS = 200; //权限越界
	public static final int ROLE_ERROR = 210; //角色错误
	public static final int ROLE_EXIST = 220; //角色已存在
	public static final int ROLE_NOT_EXIST = 225; //角色不存在
	public static final int DEL_BASE_ROLE_ERROR = 230; //删除基础角色引发的错误
	public static final int PROP_ERROR = 310; //职责行为错误
	public static final int PROP_EXIST = 220; //职责行为已经存在
	public static final int PROP_NOT_EXIST = 330; //职责行为不存在
	
	//其他
	public static final int NULL_PARAM_ERROR = 500;	//参数为空错误
	public static final int PARAM_FORMATE_ERROR = 510; //参数格式错误
	public static final int NULL_RESULT_ERROR = 550; //结果为空
	
	
	
}
