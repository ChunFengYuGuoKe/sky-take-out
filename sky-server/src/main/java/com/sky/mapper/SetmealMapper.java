package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 添加套餐
     *
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @Select("select * from setmeal where id = #{setmealId}")
    Setmeal getById(Long setmealId);


    Page<Setmeal> page(SetmealPageQueryDTO setmealPageQueryDTO);


    @AutoFill(value = OperationType.UPDATE)
    void update(Setmeal setmeal);


    void deleteBatch(List<Integer> ids);


    List<Integer> queryStatusesByIds(List<Integer> ids);

//    @Update("update setmeal set status = #{status} where id = #{setmealId}")
//    void bp(Integer status, Long setmealId);
}