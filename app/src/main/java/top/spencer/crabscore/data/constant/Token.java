package top.spencer.crabscore.data.constant;

import top.spencer.crabscore.data.model.LoginModel;
import top.spencer.crabscore.data.model.RegistModel;

/**
 * 具体Model类，常量用于反射
 *
 * @author spencercjh
 */
public class Token {
    public static final String API_LOGIN = LoginModel.class.getName();

    public static final String API_REGIST = RegistModel.class.getName();
}