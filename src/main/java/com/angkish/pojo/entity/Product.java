package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 农产品基础信息实体
 * 对应表：t_product
 */
@Data
@Accessors(chain = true)
@TableName("t_product")
public class Product {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 生产主体ID */
    private Long producerId;

    /** 农产品名称 */
    private String productName;

    /** 品种 */
    private String variety;

    /** 产地 */
    private String origin;

    /** 种植标准 */
    private String plantingStandard;

    /** 采收周期（天） */
    private Integer harvestCycle;

    /** 质量标准 */
    private String qualityStandard;

    /** 溯源码 */
    private String traceCode;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
