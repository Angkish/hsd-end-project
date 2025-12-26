package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 物联网环境监测数据实体
 * 对应表：t_iot_data
 */
@Data
@Accessors(chain = true)
@TableName("t_iot_data")
public class IotData {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 农产品ID */
    private Long productId;

    /** 温度（℃） */
    private Double temperature;

    /** 湿度（%） */
    private Double humidity;

    /** 光照强度 */
    private Double light;

    /** 土壤湿度 */
    private Double soilMoisture;

    /** 采集时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime collectTime;
}

