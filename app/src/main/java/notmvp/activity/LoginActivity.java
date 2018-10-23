package notmvp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import notmvp.view.OwlView;
import top.spencer.crabscore.R;

/**
 * @author spencercjh
 */
public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.owl_view)
    OwlView mOwlView;
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
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mOwlView.open();

                } else {
                    mOwlView.close();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "我要登录了", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
