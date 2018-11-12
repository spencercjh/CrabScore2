package top.spencer.crabscore.model.constant;

import top.spencer.crabscore.model.model.administrator.*;
import top.spencer.crabscore.model.model.common.*;
import top.spencer.crabscore.model.model.common.rank.GetFatnessRankModel;
import top.spencer.crabscore.model.model.common.rank.GetQualityRankModel;
import top.spencer.crabscore.model.model.common.rank.GetTasteRankModel;
import top.spencer.crabscore.model.model.company.GetOneCompanyAllGroupModel;
import top.spencer.crabscore.model.model.company.UserBindCompanyModel;
import top.spencer.crabscore.model.model.judge.GetAllGroupModel;
import top.spencer.crabscore.model.model.staff.AddCrabModel;

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

    String API_GET_FATNESS_RANK = GetFatnessRankModel.class.getName();

    String API_GET_QUALITY_RANK = GetQualityRankModel.class.getName();

    String API_GET_TASTE_RANK = GetTasteRankModel.class.getName();

    String API_GET_PRESENT_COMPETITION_PROPERTY = GetPresentCompetitionPropertyModel.class.getName();

    String API_GET_ALL_USER = GetAllUserModel.class.getName();

    String API_GET_ALL_USER_BY_STATUS = GetAllUserByStatusModel.class.getName();

    String API_GET_ALL_COMPANY_ADMIN = GetAllCompanyModel.class.getName();

    String API_UPDATE_USER_PROPERTY = UpdateUserPropertyModel.class.getName();

    String API_DELETE_USER = DeleteUserModel.class.getName();

    String API_UPDATE_COMPETITION_PROPERTY = UpdateCompetitionPropertyModel.class.getName();

    String API_GET_ALL_COMPETITION = GetAllCompetitionModel.class.getName();

    String API_GENERATE_SCORE = GenerateScoreModel.class.getName();

    String API_UPDATE_PRESENT_COMPETITION_CONFIG = UpdatePresentCompetitionConfigModel.class.getName();

    String API_GET_ALL_GROUP_JUDGE = GetAllGroupModel.class.getName();

    String API_UPDATE_COMPANY_PROPERTY = UpdateCompanyPropertyModel.class.getName();

    String API_DELETE_COMPANY = DeleteCompanyModel.class.getName();

    String API_GET_ONE_COMPANY_ALL_GROUP = GetOneCompanyAllGroupModel.class.getName();

    String API_GET_ALL_COMPANY_USER = top.spencer.crabscore.model.model.company.GetAllCompanyModel.class.getName();

    String API_USER_BIND_COMPANY = UserBindCompanyModel.class.getName();

    String API_GET_ALL_GROUP_STAFF = top.spencer.crabscore.model.model.staff.GetAllGroupModel.class.getName();

    String API_ADD_CRAB = AddCrabModel.class.getName();
}