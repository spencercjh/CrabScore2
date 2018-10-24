package top.spencer.crabscore.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.presenter.TestPresenter;

/**
 * @author spencercjh
 */
public class TestActivity extends BaseActivity implements BaseView {

    @BindView(R.id.text)
    TextView text;

    private TestPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        //初始化Presenter
        presenter = new TestPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //断开View引用
        presenter.detachView();
    }

    @Override
    public void showData(String data) {
        text.setText(data);
    }

    @OnClick(R.id.btn_success)
    public void getData(View view) {
        presenter.getData("normal");
    }

    @OnClick(R.id.btn_failure)
    public void getDataForFailure(View view) {
        presenter.getData("failure");
    }

    @OnClick(R.id.btn_error)
    public void getDataForError(View view) {
        presenter.getData("error");
    }
}