package top.spencer.crabscore.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.base.BaseView;
import top.spencer.crabscore.presenter.LoginPresenter;

/**
 * @author spencercjh
 */
public class LoginActivity extends BaseActivity implements BaseView {

    private LoginPresenter loginPresenter;

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.btn_login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //绑定初始化ButterKnife
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenter();
        loginPresenter.attachView(this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(email.getText().toString().trim(),
                        password.getText().toString().trim());
            }
        });
    }

    @Override
    public void showData(String data) {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
    }
}
