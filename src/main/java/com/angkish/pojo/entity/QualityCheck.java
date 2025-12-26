package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 农产品质量检测实体
 * 对应表：t_quality_check
 */
@Data
@Accessors(chain = true)
@TableName("t_quality_check")
public class QualityCheck {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 农产品ID */
    private Long productId;

    /** 检测机构 */
    private String checkOrg;

    /** 检测时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime checkTime;

    /** 检测项目 */
    private String checkItems;

    /** 检测结果：0不合格 1合格 */
    private Integer checkResult;

    /** 检测报告URL */
    private String reportUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}