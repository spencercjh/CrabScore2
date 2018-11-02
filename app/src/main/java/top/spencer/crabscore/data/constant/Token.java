package top.spencer.crabscore.data.constant;

import top.spencer.crabscore.data.model.*;

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

    String API_LOGIN_OR_REGIST = PhoneLoginModel.class.getName();

    String API_FORGET_PASSWORD = ForgetPasswordModel.class.getName();

    String API_FATNESS_RANK = FatnessRankModel.class.getName();

    String API_QUALITY_RANK = QualityRankModel.class.getName();

    String API_TASTE_RANK = TasteRankModel.class.getName();

    String API_PRESENT_COMPETITION = PresentCompetitionModel.class.getName();
}