package top.spencer.crabscore.common;

/**
 * @author spencercjh
 */
public interface CommonConstant {
    String MESSAGE = "message";

    String NULL = "null";

    String CODE = "code";

    Integer CRAB_MALE = 1;

    Integer CRAB_FEMALE = 0;

    /**
     * SeekBar的百分百进度
     */
    Integer SUCCESS_VERIFY = 100;

    /**
     * 双击两次返回键后退出程序的时间间隔 单位ms
     */
    Integer EXIT_GAP_TIME = 1000;

    /**
     * 验证码长度
     */
    Integer CODE_LENGTH = 4;

    /**
     * SharedPreferences中auto_login字段
     */
    String AUTO_LOGIN = "AUTO_LOGIN";

    /**
     * SharedPreferences中remember_password字段
     */
    String REMEMBER_PASSWORD = "REMEMBER_PASSWORD";

    /**
     * API后端服务器地址
     */
    String URL = "http://api.crabscore.spencercjh.top:14000/";

    /**
     * 七牛云对象存储服务器地址
     */
    String QINIU_URL = "http://spencercjh.top/";
    /**
     * 成功
     */
    Integer SUCCESS = 200;

    /**
     * 全部大赛 0
     */
    Integer USER_COMPETITION_ALL = 0;

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "http://spencercjh.top/%E4%B8%8A%E6%B5%B7%E6%B5%B7%E6%B4%8B%E5%A4%A7%E5%AD%A6%E6%A0%A1%E5%BE%BD.jpg";

    /**
     * 用户正常状态
     */
    Integer USER_STATUS_NORMAL = 1;

    /**
     * 用户禁用状态
     */
    Integer USER_STATUS_LOCK = 0;

    /**
     * 管理员
     */
    Integer USER_TYPE_ADMIN = 1;

    /**
     * SharedPreferences中administrator字段
     */
    String ADMINISTRATOR = "ADMINISTRATOR";

    /**
     * 评委
     */
    Integer USER_TYPE_JUDGE = 2;

    /**
     * SharedPreferences中judge字段
     */
    String JUDGE = "JUDGE";

    /**
     * 工作人员
     */
    Integer USER_TYPE_STAFF = 3;

    /**
     * SharedPreferences中staff字段
     */
    String STAFF = "STAFF";

    /**
     * 参选单位
     */
    Integer USER_TYPE_COMPANY = 4;

    /**
     * SharedPreferences中company字段
     */
    String COMPANY = "COMPANY";
}
