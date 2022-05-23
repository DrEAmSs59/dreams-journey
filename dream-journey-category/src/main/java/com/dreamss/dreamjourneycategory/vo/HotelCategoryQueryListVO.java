package com.dreamss.dreamjourneycategory.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Created by DrEAmSs on 2022-05-23 17:30
 */
@Data
@ApiModel
public class HotelCategoryQueryListVO {

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("分类层级")
    private Integer level;

    @ApiModelProperty("分类排序")
    private Integer sort;

    @ApiModelProperty("当前页")
    private Integer pageIndex;

    @ApiModelProperty("当前页容量")
    private Integer pageSize;
}
