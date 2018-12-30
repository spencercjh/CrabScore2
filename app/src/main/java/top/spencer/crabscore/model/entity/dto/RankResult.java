package top.spencer.crabscore.model.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author spencercjh
 */
@Data
public class RankResult {
    private Integer groupId;

    private Integer companyId;

    private String companyName;

    private String avatarUrl;

    private Integer competitionId;

    private Float score;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;
}
