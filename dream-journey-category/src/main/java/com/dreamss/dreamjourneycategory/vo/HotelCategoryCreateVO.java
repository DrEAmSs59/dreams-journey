package com.dreamss.dreamjourneycategory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by DrEAmSs on 2022-05-23 16:46
 */
@Data
@ApiModel("酒店分类创建或更新")
public class HotelCategoryCreateVO {

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类描述")
    private String description;

    @ApiModelProperty("分类层级")
    private Integer level;

    @ApiModelProperty("分类排序")
    private Integer sort;

    @ApiModelProperty("父分类id")
    private String parentId;
}
