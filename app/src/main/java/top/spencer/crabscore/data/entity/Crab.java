package top.spencer.crabscore.data.entity;

import lombok.Data;

import java.util.Date;

/**
 * 螃蟹对象模型
 *
 * @author spencercjh
 * rxpb_crab_info
 */
@Data
public class Crab {

    /**
     * 唯一标识 螃蟹id")
     */
    private Integer crabId;

    /**
     * 所属小组id")
     */
    private Integer groupId;

    /**
     * 性别，1:雄 2：雌")
     */
    private Integer crabSex;

    /**
     * 螃蟹标签")
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