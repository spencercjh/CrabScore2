package top.spencer.crabscore.data.constant;

import top.spencer.crabscore.data.model.LoginModel;
import top.spencer.crabscore.data.model.RegistModel;
import top.spencer.crabscore.data.model.SendCodeModel;
import top.spencer.crabscore.data.model.VerifyCodeModel;

/**
 * 具体Model类，常量用于反射
 *
 * @author spencercjh
 */
public interface Token {
    String API_LOGIN = LoginModel.class.getName();

    String API_REGIST = RegistModel.class.getName();

    String API_SEND_CODE = SendCodeModel.class.getName();

    String API_VERIFY_CODE = VerifyCodeModel.class.getName();
}