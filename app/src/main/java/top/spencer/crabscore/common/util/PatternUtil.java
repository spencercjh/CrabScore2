package top.spencer.crabscore.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户名验证工具类
 *
 * @author Exrickx
 */
@SuppressWarnings("unused")
public class PatternUtil {

    /**
     * 由字母数字下划线组成且开头必须是字母，不能超过16位
     */
    private static final Pattern USERNAME = Pattern.compile("[a-zA-Z0-9_][a-zA-Z0-9_]{1,15}");

    /**
     * 手机号
     */
    private static final Pattern MOBILE = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");

    /**
     * 邮箱
     */
    private static final Pattern EMAIL = Pattern.compile("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$");

    /**
     * 姓名1到10个中文
     */
    private static final Pattern NAME = Pattern.compile("^([a-zA-Z0-9\\u4e00-\\u9fa5\\·]{1,10})$");

    public static boolean isUsername(String v) {

        Matcher m = USERNAME.matcher(v);
        return m.matches();
    }

    public static boolean isMobile(String v) {

        Matcher m = MOBILE.matcher(v);
        return m.matches();
    }

    public static boolean isEmail(String v) {

        Matcher m = EMAIL.matcher(v);
        return m.matches();
    }

    public static boolean isName(String v) {
        Matcher m = NAME.matcher(v);
        return m.matches();
    }
}
