package top.spencer.crabscore.data.model;

import top.spencer.crabscore.base.BaseModel;

/**
 * @author spencercjh
 */
public class DataModel {
    public static BaseModel request(String token) {
        // 声明一个空的BaseModel
        BaseModel model = null;
        try {
            //利用反射机制获得对应Model对象的引用
            model = (BaseModel) Class.forName(token).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            //TODO execute exception
        }
        return model;
    }
}