package top.spencer.crabscore.view.helper;

import android.content.Context;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ToggleButton;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.data.entity.Group;

import java.util.List;

/**
 * UI Helper
 *
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

    /**
     * 对排行榜页面中处理返回的group对象的封装
     *
     * @param groups    jsonResult
     * @param groupList list
     * @return 是否有重复
     */
    public static boolean dealGroupListJSON(JSONArray groups, List<Group> groupList) {
        boolean repeat = false;
        for (Object object : groups) {
            JSONObject jsonObject = (JSONObject) object;
            String jsonString = jsonObject.toJSONString();
            Group group = JSONObject.parseObject(jsonString, Group.class);
            if (!groupList.contains(group)) {
                groupList.add(group);
            } else {
                repeat = true;
            }
        }
        return repeat;
    }
}
