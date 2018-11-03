package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户对象模型
 *
 * @author spencercjh
 * rxpb_user_info
 */
@Data
public class User {

    /**
     * 唯一标识 用户id")
     */
    private Integer userId;

    /**
     * 用户名")
     */
    private String userName;

    /**
     * 密码")
     */
    private String password;

    /**
     * 姓名")
     */
    private String displayName;

    /**
     * 用户组id")
     */
    private Integer roleId;

    /**
     * 用户状态 1：可用 0：禁用")
     */
    private Integer status;

    /**
     * 邮箱 替换为手机")
     */
    private String email;

    /**
     * 赛事信息（为0时代表永久有效）")
     */
    private Integer competitionId;

    /**
     * 创建时间")
     */
    private Date createDate;

    /**
     * 创建用户")
     */
    private String createUser;

    /**
     * 更新时间")
     */
    private Date updateDate;

    /**
     * 更新用户")
     */
    private String updateUser;
}