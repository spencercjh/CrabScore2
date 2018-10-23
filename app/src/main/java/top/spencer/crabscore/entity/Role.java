package top.spencer.crabscore.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户组对象模型
 *
 * @author spencercjh
 * rxpb_role_info
 */
@Data
public class Role {

    /**
     * 唯一标识 用户组id")
     */
    private Integer roleId;

    /**
     * 用户组名称")
     */
    private String roleName;

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