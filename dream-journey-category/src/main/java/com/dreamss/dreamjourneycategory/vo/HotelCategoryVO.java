package com.dreamss.dreamjourneycategory.vo;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Created by DrEAmSs on 2022-05-23 18:51
 */
@Data
@ApiModel("酒店分类列表")
public class HotelCategoryVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类描述")
    private String Description;

    @ApiModelProperty("分类层级1：第一层级 2：第二层级")
    private Integer level;

    @ApiModelProperty("父层级id")
    private String parentId;

    @ApiModelProperty("排序字段")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @ApiModelProperty("更新时间")
    private Timestamp updateTime;

    @ApiModelProperty("子分类")
    private List<HotelCategoryVO> children = Lists.newArrayList();
}
