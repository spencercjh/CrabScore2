package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 大赛信息配置对象模型
 *
 * @author spencercjh
 * rxpb_competition_config
 */
@Data
public class CompetitionConfig implements Serializable {

    /**
     * 唯一标识")
     */
    private Integer id;

    /**
     * 大赛id")
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