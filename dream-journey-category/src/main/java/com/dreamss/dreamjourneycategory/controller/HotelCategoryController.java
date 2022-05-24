package com.dreamss.dreamjourneycategory.controller;

import com.dreamss.dreamjourneycategory.service.HotelCategoryService;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryCreateVO;
import com.dreamss.dreamjourneycategory.vo.HotelCategoryQueryListVO;
import com.dreamss.dreamjourneycommon.enums.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Created by DrEAmSs on 2022-05-23 16:39
 */
@Api(tags = "酒店分类")
@RestController
@RequestMapping("hotel/category")
public class HotelCategoryController {

    @Resource
    private HotelCategoryService hotelCategoryService;

    @ApiOperation("创建或修改分类")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<?> createOrUpdate(@RequestBody HotelCategoryCreateVO hotelCategoryCreateVO) {
        hotelCategoryService.createOrUpdate(hotelCategoryCreateVO);
        return ResponseEntity.ok(ResultEnum.SUCCESS.getLabel());
    }

    @ApiOperation("查询酒店分类列表")
    @PostMapping("/queryList")
    public ResponseEntity<?> queryHotelCategoryList(@RequestBody HotelCategoryQueryListVO hotelCategoryQueryListVO) {
        return ResponseEntity.ok(hotelCategoryService.queryHotelCategoryList(hotelCategoryQueryListVO));
    }

    @ApiOperation("查询酒店1层级分类列表（不分页）")
    @GetMapping("/queryLevel1List")
    public ResponseEntity<?> queryLevel1HotelCategoryList() {
        return ResponseEntity.ok(hotelCategoryService.queryLevel1HotelCategoryList());
    }

    @ApiOperation("根据id查询单条")
    @GetMapping("/queryById/{id}")
    public ResponseEntity<?> queryById(@PathVariable String id) {
        return ResponseEntity.ok(hotelCategoryService.queryById(id));
    }

    @ApiOperation("级联删除")
    @PostMapping("/deleteByIds")
    public ResponseEntity<?> deleteByIds(@RequestBody List<String> ids) {
        hotelCategoryService.deleteByIds(ids);
        return ResponseEntity.ok(ResultEnum.SUCCESS.getLabel());
    }
}
