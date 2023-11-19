package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 添加套餐并修改对应dish信息
     * @param setmealDTO
     */
    void saveWithDish(SetmealDTO setmealDTO);

    /**
     * 根据id查询套餐及对应菜品
     * @param setmealId
     */
    SetmealVO getById(Long setmealId);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);


    void update(SetmealDTO setmealDTO);


    void deleteBatch(List<Integer> ids);


    void bp(Integer status, Long setmealId);
}