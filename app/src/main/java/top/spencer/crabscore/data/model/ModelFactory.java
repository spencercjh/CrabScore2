package top.spencer.crabscore.data.model;

import android.util.Log;
import top.spencer.crabscore.base.BaseModel;

import static android.content.ContentValues.TAG;

/**
 * @author spencercjh
 */
public class ModelFactory {
    public static BaseModel request(String token) {
        // 声明一个空的BaseModel
        BaseModel model = null;
        try {
            //利用反射机制获得对应Model对象的引用
            model = (BaseModel) Class.forName(token).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            Log.e(TAG, "Model反射错误");
        }
        return model;
    }
}