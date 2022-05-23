package com.dreamss.dreamjourneycategory.service.impl;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dreamss.dreamjourneycategory.dao.HotelCategoryDao;
import com.dreamss.dreamjourneycategory.entities.HotelCategory;
import com.dreamss.dreamjourneycategory.service.HotelCategoryService;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryCreateVO;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryQueryListVO;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Created by DrEAmSs on 2022-05-23 16:42
 */
@Service
public class HotelCategoryServiceImpl implements HotelCategoryService {

    @Resource
    private HotelCategoryDao hotelCategoryDao;

    @Override
    public void createOrUpdate(HotelCategoryCreateVO hotelCategoryCreateVO) {
        HotelCategory hotelCategory = new HotelCategory();
        BeanUtils.copyProperties(hotelCategoryCreateVO, hotelCategory);
        if (StringUtils.isNotBlank(hotelCategoryCreateVO.getId())) {
            hotelCategory.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        } else {
            hotelCategory.setId(UUID.randomUUID().toString().replace("-", ""));
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            hotelCategory.setUpdateTime(timestamp);
            hotelCategory.setCreateTime(timestamp);
        }
        hotelCategoryDao.saveOrUpdate(hotelCategory);
    }

    @Override
    public Page<HotelCategoryVO> queryHotelCategoryList(HotelCategoryQueryListVO hotelCategoryQueryListVO) {
        Page<HotelCategory> pageParam = new Page<>(hotelCategoryQueryListVO.getPageIndex() - 1
                , hotelCategoryQueryListVO.getPageSize());
        Page<HotelCategory> hotelCategoryPage =
                new LambdaQueryChainWrapper<>(hotelCategoryDao.getBaseMapper())
                        .eq(HotelCategory::getLevel, 1)
                        .orderBy(true, true, HotelCategory::getSort)
                        .orderBy(true, false, HotelCategory::getUpdateTime)
                        .page(pageParam);
        List<HotelCategory> children = hotelCategoryDao.lambdaQuery().ne(HotelCategory::getLevel, 1)
                .orderBy(true, true, HotelCategory::getSort)
                .orderBy(true, false, HotelCategory::getUpdateTime).list();
        List<HotelCategoryVO> hotelCategoryVOS = Lists.newArrayList();
        hotelCategoryPage.getRecords().forEach(temp -> {
            HotelCategoryVO hotelCategoryVO = new HotelCategoryVO();
            BeanUtils.copyProperties(temp, hotelCategoryVO);
            List<HotelCategory> tempChildren = children.stream()
                    .filter(tempChild -> temp.getId().equals(tempChild.getParentId()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(tempChildren)) {
                List<HotelCategoryVO> childrenCategory = Lists.newArrayList();
                tempChildren.forEach(tempChild -> {
                    HotelCategoryVO tempHotelCategoryVO = new HotelCategoryVO();
                    BeanUtils.copyProperties(tempChild, tempHotelCategoryVO);
                    childrenCategory.add(tempHotelCategoryVO);
                });
                hotelCategoryVO.setChildren(childrenCategory);
            }
            hotelCategoryVOS.add(hotelCategoryVO);
        });
        Page<HotelCategoryVO> hotelCategoryVOPage = new Page<>();
        BeanUtils.copyProperties(hotelCategoryPage, hotelCategoryVOPage);
        hotelCategoryVOPage.setRecords(hotelCategoryVOS);
        return hotelCategoryVOPage;
    }

    @Override
    public List<HotelCategoryVO> queryLevel1HotelCategoryList() {
        List<HotelCategoryVO> hotelCategoryVOS = Lists.newArrayList();
        hotelCategoryDao.lambdaQuery().eq(HotelCategory::getLevel, 1)
                .orderBy(true, true, HotelCategory::getSort)
                .orderBy(true, false, HotelCategory::getUpdateTime)
                .list().forEach(temp -> {
                    HotelCategoryVO hotelCategoryVO = new HotelCategoryVO();
                    BeanUtils.copyProperties(temp, hotelCategoryVO);
                    hotelCategoryVOS.add(hotelCategoryVO);
                });
        return hotelCategoryVOS;
    }
}
