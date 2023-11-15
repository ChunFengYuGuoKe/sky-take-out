package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.ArrayList;

public interface CategoryService {

    void update(CategoryDTO categoryDTO);

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);



    /**
     * 启用、禁用分类
     * @param status
     * @return
     */
    void bp(Integer status, Long id);


    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    void add(CategoryDTO categoryDTO);

    void delete(Long id);

    ArrayList<Category> getByType(Integer type);
}
