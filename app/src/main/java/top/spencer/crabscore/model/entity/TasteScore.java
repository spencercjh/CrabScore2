package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 口感分数对象模型
 *
 * @author spencercjh
 * rxpb_score_taste
 */
@Data
public class TasteScore implements Serializable {

    /**
     * 唯一标识 口感分数id")
     */
    private Integer scoreId;

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
     * 蟹盖颜色")
     */
    private Float scoreYgys;

    /**
     * 鳃颜色")
     */
    private Float scoreSys;

    /**
     * 膏、黄颜色")
     */
    private Float scoreGhys;

    /**
     * 腥味、香味")
     */
    private Float scoreXwxw;

    /**
     * 膏、黄")
     */
    private Float scoreGh;

    /**
     * 腹部肌肉")
     */
    private Float scoreFbjr;

    /**
     * 第二、三步足肌肉")
     */
    private Float scoreBzjr;

    /**
     * 创建用户")
     */
    private Date createDate;

    /**
     * 最终给分")
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