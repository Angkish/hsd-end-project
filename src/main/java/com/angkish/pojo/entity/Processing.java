package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 加工记录实体
 * 对应表：t_processing
 */
@Data
@Accessors(chain = true)
@TableName("t_processing")
public class Processing {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 农产品ID */
    private Long productId;

    /** 加工工艺 */
    private String processType;

    /** 加工时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime processTime;

    /** 加工人员 */
    private String processor;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}