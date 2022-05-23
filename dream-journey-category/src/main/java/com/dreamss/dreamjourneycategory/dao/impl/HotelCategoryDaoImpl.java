package com.dreamss.dreamjourneycategory.dao.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dreamss.dreamjourneycategory.dao.HotelCategoryDao;
import com.dreamss.dreamjourneycategory.entities.HotelCategory;
import com.dreamss.dreamjourneycategory.mapper.HotelCategoryMapper;
import org.springframework.stereotype.Service;

/**
 * @author Created by DrEAmSs on 2022-05-23 16:54
 */
@Service
public class HotelCategoryDaoImpl extends ServiceImpl<HotelCategoryMapper, HotelCategory> implements HotelCategoryDao {
}
