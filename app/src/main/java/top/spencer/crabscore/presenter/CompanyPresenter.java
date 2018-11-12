package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.view.view.CompanyView;

/**
 * @author spencercjh
 */
public class CompanyPresenter extends BasePresenter<CompanyView> {
    /**
     * 查看一个参选单位的所有组
     *
     * @param competitionId 大赛Id
     * @param companyId     参选单位Id
     * @param pageNum       页数
     * @param pageSize      页面大小
     * @param jwt           JWT
     * @see top.spencer.crabscore.model.model.company.GetOneCompanyAllGroupModel
     */
    public void getOneCompanyAllGroup(Integer competitionId, Integer companyId, Integer pageNum, Integer pageSize, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_ONE_COMPANY_ALL_GROUP)
                .params(String.valueOf(competitionId), String.valueOf(companyId), String.valueOf(pageNum),
                        String.valueOf(pageSize), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showData(data);
                    }

                    @Override
                    public void onFailure(JSONObject data) {
                        getView().showFailure(data);
                    }

                    @Override
                    public void onError() {
                        getView().showErr();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }

    /**
     * 根据用户绑定的companyId查找参选单位
     *
     * @param companyId 参选单位Id
     * @param jwt       JWT
     * @see top.spencer.crabscore.model.model.company.GetCompanyModel
     */
    public void getCompany(Integer companyId, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_GET_COMPANY)
                .params(String.valueOf(companyId), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showGetCompanyResponse(data);
                    }

                    @Override
                    public void onFailure(JSONObject data) {
                        getView().showFailure(data);
                    }

                    @Override
                    public void onError() {
                        getView().showErr();
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoading();
                    }
                });
    }
}
