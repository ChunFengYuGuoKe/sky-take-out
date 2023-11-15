package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Api(tags = "分类相关接口")
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 更新菜品分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation("更新菜品分类")
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("更新菜品分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }


    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("菜品分类分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 启用、禁用分类
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用分类")
    public Result bp(@PathVariable Integer status, Long id){
        categoryService.bp(status, id);
        return Result.success();
    }

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result add(@RequestBody CategoryDTO categoryDTO){
        log.info("adding category:{}", categoryDTO);
        categoryService.add(categoryDTO);
        return Result.success();
    }


    /**
     * 根据id删除分类
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result kill(Long id){
        log.info("根据id删除分类:{}", id);
        categoryService.delete(id);
        return Result.success();
    }


    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @ApiOperation("根据类型查询分类")
    @GetMapping("/list")
    public Result getByType(Integer type){
        ArrayList<Category> array = categoryService.getByType(type);
        return Result.success(array);
    }
}
