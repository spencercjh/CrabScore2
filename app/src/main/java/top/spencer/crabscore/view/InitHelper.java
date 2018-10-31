package top.spencer.crabscore.view;

import android.app.Activity;
import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.ToggleButton;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseActivity;
import top.spencer.crabscore.base.BasePresenter;
import top.spencer.crabscore.common.CommonConstant;

public class InitHelper {
    public static void toggleButtonDisplayPassword(ToggleButton toggleButton, EditText password, boolean isChecked, Context context) {
        if (isChecked) {
            //如果选中，显示密码
            toggleButton.setBackground(context.getDrawable(R.drawable.eye_open));
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            toggleButton.setBackground(context.getDrawable(R.drawable.eye_close));
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
