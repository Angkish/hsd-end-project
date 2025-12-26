package com.angkish.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 农产品生产主体实体
 * 对应表：t_producer
 */
@Data
@Accessors(chain = true)
@TableName("t_producer")
public class Producer {

    /** 生产主体ID（雪花算法生成） */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 关联用户ID（t_user.id） */
    private Long userId;

    /** 生产主体名称（农户名 / 合作社名 / 企业名） */
    private String name;

    /**
     * 主体类型：
     * 1 - 农户
     * 2 - 合作社
     * 3 - 企业
     */
    private Integer type;

    /** 生产地址 */
    private String address;

    /** 资质证书信息（绿色食品、有机认证等） */
    private String certInfo;

    /** 种植规模说明（如：50亩大棚） */
    private String scaleInfo;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
