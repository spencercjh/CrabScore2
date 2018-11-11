package top.spencer.crabscore.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.base.MyCallback;
import top.spencer.crabscore.model.constant.Token;
import top.spencer.crabscore.model.entity.Company;
import top.spencer.crabscore.model.model.administrator.UpdateCompanyPropertyModel;
import top.spencer.crabscore.model.model.common.ModelFactory;
import top.spencer.crabscore.view.view.CompanyAdminListView;

/**
 * @author spencercjh
 */
public class CompanyAdminPresenter extends BasePresenter<CompanyAdminListView> {
    /**
     * 修改参选单位资料
     *
     * @param company 参选单位对象
     * @param jwt     JWT
     * @see UpdateCompanyPropertyModel
     */
    public void updateCompanyProperty(Company company, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_UPDATE_COMPANY_PROPERTY)
                .params(JSON.toJSON(company).toString(), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showUpdateCompanyPropertyResponse(data);
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
     * 删除参选单位
     *
     * @param companyId 参选单位Id
     * @param jwt       JWT
     * @see top.spencer.crabscore.model.model.administrator.DeleteCompanyModel
     */
    public void deleteCompany(Integer companyId, String jwt) {
        if (isViewAttached()) {
            return;
        }
        getView().showLoading();
        ModelFactory
                .request(Token.API_DELETE_COMPANY)
                .params(String.valueOf(companyId), jwt)
                .execute(new MyCallback<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject data) {
                        getView().showDeleteCompanyResponse(data);
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
