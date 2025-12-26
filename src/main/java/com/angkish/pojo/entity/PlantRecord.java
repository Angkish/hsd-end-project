package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 种植过程记录实体
 * 对应表：t_plant_record
 */
@Data
@Accessors(chain = true)
@TableName("t_plant_record")
public class PlantRecord {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 种植计划ID */
    private Long planId;

    /** 操作类型：1播种 2施肥 3打药 4灌溉 */
    private Integer operationType;

    /** 使用物料名称 */
    private String materialName;

    /** 使用剂量 */
    private String dosage;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operationTime;

    /** 安全间隔期（天） */
    private Integer safetyInterval;

    /** 现场照片URL */
    private String photoUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}