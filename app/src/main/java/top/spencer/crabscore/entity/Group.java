package top.spencer.crabscore.entity;

import lombok.Data;

import java.util.Date;

/**
 * 小组对象模型
 *
 * @author spencercjh
 * rxpb_group_info
 */
@Data
public class Group {

    /**
     * 唯一标识 小组id")
     */
    private Integer groupId;

    /**
     * 所属参选单位id")
     */
    private Integer companyId;

    /**
     * 所属大赛id")
     */
    private Integer competitionId;

    /**
     * 雄蟹肥满度评分")
     */
    private Float fatnessScoreM;

    /**
     * 雄蟹种质评分")
     */
    private Float qualityScoreM;

    /**
     * 雄蟹口感评分")
     */
    private Float tasteScoreM;

    /**
     * 雌蟹口感评分")
     */
    private Float fatnessScoreF;

    /**
     * 雌蟹种质评分")
     */
    private Float qualityScoreF;

    /**
     * 雌蟹口感评分")
     */
    private Float tasteScoreF;

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