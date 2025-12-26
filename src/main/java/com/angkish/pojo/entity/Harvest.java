package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 采收记录实体
 * 对应表：t_harvest
 */
@Data
@Accessors(chain = true)
@TableName("t_harvest")
public class Harvest {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 农产品ID */
    private Long productId;

    /** 采收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime harvestTime;

    /** 采收数量 */
    private Double harvestQuantity;

    /** 采收人员 */
    private String harvestPerson;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
