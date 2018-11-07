package top.spencer.crabscore.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 大赛信息对象模型
 *
 * @author spencercjh
 * rxpb_competition_info
 */
@Data
public class Competition implements Serializable {

    /**
     * 唯一标识 大赛id")
     */
    private Integer competitionId;
    /**
     * 大赛年份")
     */
    private String competitionYear;
    /**
     * 雄蟹肥满度参数")
     */
    private Float varFatnessM;
    /**
     * 雌蟹肥满度参数")
     */
    private Float varFatnessF;
    /**
     * 雄蟹体重参数")
     */
    private Float varWeightM;
    /**
     * 雌蟹体重参数")
     */
    private Float varWeightF;
    /**
     * 雄蟹肥满度标准差参数")
     */
    private Float varMfatnessSd;
    /**
     * 雄蟹体重标准差参数")
     */
    private Float varMweightSd;
    /**
     * 雌蟹肥满度标准差参数")
     */
    private Float varFfatnessSd;
    /**
     * 雌蟹体重标准差参数")
     */
    private Float varFweightSd;
    /**
     * 肥满度排名 1:允许查看排名,0不允许查看排名")
     */
    private Integer resultFatness;
    /**
     * 种质分排名 1:允许查看排名,0不允许查看排名")
     */
    private Integer resultQuality;
    /**
     * 口感分排名 1:允许查看排名,0不允许查看排名")
     */
    private Integer resultTaste;
    /**
     * 备注")
     */
    private String note;
    /**
     * 大赛状态 1：可用 0：禁用")
     */
    private Integer status;
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