package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询对应套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐菜品对应关系数据
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getDishWithSetmealId(Long setmealId);


    @Select("select status from setmeal_dish s left outer join dish d on s.dish_id = d.id where s.dish_id = #{dishId}")
    Integer queryDishStatusByDishId(Long dishId);

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);


    void deleteBySetmealIds(List<Integer> setmealIds);

    @Select("select d.status from setmeal_dish s left outer join dish d on s.dish_id = d.id where s.setmeal_id = #{setmealId}")
    List<Integer> getDishesStatusBySetmealId(Long setmealId);

    @Select("select s.status from setmeal_dish d left outer join setmeal s on d.setmeal_id = s.id where d.dish_id = #{dishId}")
    List<Integer> querySetmealStatusByDishId(Long dishId);
}
