package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * 参选单位模型
 *
 * @author spencercjh
 * rxpb_company_info
 */
@Data
public class Company {

    /**
     * 唯一标识 参选单位id
     */
    private Integer companyId;

    /**
     * 参选单位名"
     */
    private String companyName;

    /**
     * 参加的大赛id 1为全部大赛
     */
    private Integer competitionId;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 更新用户
     */
    private String updateUser;

}