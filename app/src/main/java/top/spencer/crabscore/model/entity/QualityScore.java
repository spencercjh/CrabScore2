package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 种质分数对象模型
 *
 * @author spencercjh
 * rxpb_score_quality
 */
@Data
public class QualityScore implements Serializable {

    /**
     * 唯一标识 种质分数id")
     */
    private Integer scoreId;

    /**
     * 螃蟹Id
     */
    private Integer crabId;

    /**
     * 所属小组id")
     */
    private Integer groupId;

    /**
     * 性别
     *
     * @see top.spencer.crabscore.common.CommonConstant#CRAB_MALE
     * @see top.spencer.crabscore.common.CommonConstant#CRAB_FEMALE
     */
    private Integer crabSex;

    /**
     * 评委用户ID")
     */
    private Integer userId;

    /**
     * 最终给分")
     */
    private Float scoreFin;

    /**
     * 体色(背)")
     */
    private Float scoreBts;

    /**
     * 体色(腹)")
     */
    private Float scoreFts;

    /**
     * 额齿")
     */
    private Float scoreEc;

    /**
     * 第4侧齿")
     */
    private Float scoreDscc;

    /**
     * 背部疣状突")
     */
    private Float scoreBbyzt;

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

    /**
     * 赛事信息（为0时代表永久有效）")
     */
    private Integer competitionId;

}