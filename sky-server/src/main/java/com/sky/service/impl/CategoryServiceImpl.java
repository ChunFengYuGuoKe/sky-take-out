package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());

        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.update(category);
    }


    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        Page<Category> records = categoryMapper.page(categoryPageQueryDTO);
        PageResult pageResult = new PageResult(records.getTotal(), records.getResult());
        return pageResult;
    }


    /**
     * 启用、禁用分类
     * @param status
     * @return
     */
    @Override
    public void bp(Integer status, Long id) {
        Category category = Category.builder()
                        .id(id)
                        .status(status)
//                        .updateUser(BaseContext.getCurrentId())
//                        .updateTime(LocalDateTime.now())
                        .build();
        categoryMapper.update(category);
    }


    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @Override
    public void add(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

//        Long createUser = BaseContext.getCurrentId();
//        category.setCreateUser(createUser);
//        category.setUpdateUser(createUser);
//
//        LocalDateTime now = LocalDateTime.now();
//        category.setCreateTime(now);
//        category.setUpdateTime(now);

        category.setStatus(StatusConstant.DISABLE);

        categoryMapper.add(category);
    }

    /**
     * 根据id删除分类
     * @return
     */
    @Override
    public void delete(Long id) {
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        count = setmealMapper.countByCategoryId(id);
        if(count > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }
        categoryMapper.delete(id);
    }

    @Override
    public ArrayList<Category> getByType(Integer type) {
        ArrayList<Category> array = categoryMapper.getByType(type);
        return array;
    }
}
