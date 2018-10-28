package top.spencer.crabscore.common;

/**
 * @author spencercjh
 */
public interface CommonConstant {
    String AUTO_LOGIN = "AUTO_LOGIN";

    String REMEMBER_PASSWORD = "REMEMBER_PASSWORD";
    /**
     * 服务器地址
     */
    String URL = "http://localhost:8080/api/";

    /**
     * 成功
     */
    Integer SUCCESS = 200;
    /**
     * JWT中自定义校验值的变量名
     */
    String MYKEY = "MYKEY";
    /**
     * JWT中自定义校验值
     */
    String MYKEY_VALUE = "mykeymCPuT5IHaZ628q5f91Ok5Sv13f1bfh5z";
    /**
     * 全部大赛 0
     */
    Integer USER_COMPETITION_ALL = 0;
    /**
     * JWT过期时间 30分钟
     */
    Long TTLMILLIS = 1800000L;
    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "";

    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 1;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = 0;

    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;

    /**
     * 公共权限
     */
    Integer USER_TYPE_COMMON = 999;
    /**
     * 管理员
     */
    Integer USER_TYPE_ADMIN = 1;

    String ADMINISTRATOR = "ADMINISTRATOR";
    /**
     * 评委
     */
    Integer USER_TYPE_JUDGE = 2;

    String JUDGE = "JUDGE";

    /**
     * 工作人员
     */
    Integer USER_TYPE_STAFF = 3;

    String STAFF = "STAFF";

    /**
     * 参选单位
     */
    Integer USER_TYPE_COMPANY = 4;

    String COMPANY = "COMPANY";
}