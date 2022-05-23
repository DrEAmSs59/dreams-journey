package com.dreamss.dreamjourneycategory.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryCreateVO;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryQueryListVO;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryVO;

import java.util.List;

/**
 * @author Created by DrEAmSs on 2022-05-23 16:42
 */
public interface HotelCategoryService {

    /**
     * 新增或更新酒店分类
     */
    void createOrUpdate(HotelCategoryCreateVO hotelCategoryCreateVO);

    /**
     * 查询酒店分类列表
     */
    Page<HotelCategoryVO> queryHotelCategoryList(HotelCategoryQueryListVO hotelCategoryQueryListVO);

    /**
     * 查询层级为1的酒店分类
     */
    List<HotelCategoryVO> queryLevel1HotelCategoryList();
}
