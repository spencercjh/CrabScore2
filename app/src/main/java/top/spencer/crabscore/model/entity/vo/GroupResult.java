package top.spencer.crabscore.model.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author spencercjh
 * rxpb_group_info
 */
@Data
public class GroupResult implements Serializable {

    private Integer groupId;

    private Integer companyId;

    private String companyName;

    private String avatarUrl;

    private Integer competitionId;

    private Float fatnessScoreM;

    private Float qualityScoreM;

    private Float tasteScoreM;

    private Float fatnessScoreF;

    private Float qualityScoreF;

    private Float tasteScoreF;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
}