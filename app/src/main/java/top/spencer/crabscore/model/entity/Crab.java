package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 螃蟹对象模型
 *
 * @author spencercjh
 * rxpb_crab_info
 */
@Data
public class Crab implements Serializable {

    /**
     * 唯一标识 螃蟹id")
     */
    private Integer crabId;

    /**
     * 所属小组id")
     *
     * @see Group#groupId
     * @see top.spencer.crabscore.model.entity.vo.GroupResult#groupId
     */
    private Integer groupId;

    /**
     * 性别，0:雌 1 雄")
     *
     * @see top.spencer.crabscore.common.CommonConstant#CRAB_MALE
     * @see top.spencer.crabscore.common.CommonConstant#CRAB_FEMALE
     */
    private Integer crabSex;

    /**
     * 螃蟹标签 命名规则：
     * groupInDialog.CompetitionId+groupInDialog.CompanyId+groupInDialog.GroupId+SEX
     * i)
     */
    private String crabLabel;

    /**
     * 重量")
     */
    private Float crabWeight;

    /**
     * 长度")
     */
    private Float crabLength;

    /**
     * 肥满度")
     */
    private Float crabFatness;

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
     * 大赛id")
     */
    private Integer competitionId;

}