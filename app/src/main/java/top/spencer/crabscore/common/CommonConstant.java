package top.spencer.crabscore.common;

/**
 * @author spencercjh
 */
public interface CommonConstant {
    Integer CRAB_MALE = 1;

    Integer CRAB_FEMALE = 0;
    /**
     * login请求的body加密秘钥
     */
    byte[] AES_KEY = {34, 70, 37, -19, -41, -44, -114, -103, 91, -13, -115, -57, 94, 17, 67, 3};

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
     * 服务器地址
     */
    String URL = "https://www.spencercjh.top/crabscore/api/";

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
