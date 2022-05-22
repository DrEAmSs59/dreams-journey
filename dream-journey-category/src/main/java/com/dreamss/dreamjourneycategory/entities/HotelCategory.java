package com.dreamss.dreamjourneycategory.entities;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("t_hotel_category")
public class HotelCategory {

    /**
     * 主键id
     */
    private String id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类描述
     */
    private String Description;

    /**
     * 分类层级1：第一层级 2：第二层级
     */
    private Integer level;

    /**
     * 父层级id
     */
    private String parentId;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 逻辑删除字段
     */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
