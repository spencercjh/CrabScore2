package top.spencer.crabscore.view;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ToggleButton;
import top.spencer.crabscore.R;

/**
 * @author spencercjh
 */
public class InitHelper {
    /**
     * 对显示/隐藏密码按钮的初始化封装
     *
     * @param toggleButton ToggleButton
     * @param password     EditText
     * @param isChecked    isChecked
     * @param context      activity context
     */
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
