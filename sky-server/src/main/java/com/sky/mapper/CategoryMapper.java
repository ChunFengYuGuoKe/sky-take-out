package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface CategoryMapper {

    void update(Category category);

    /**
     * 菜品分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> page(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     * 新增分类
     * @param category
     * @return
     */
    @Insert("insert into category(type, name, sort, status, create_time, update_time, create_user, update_user)\n" +
                         "values (#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void add(Category category);


    /**
     * 根据id删除分类
     * @return
     */
    @Delete("delete from category where id = #{id}")
    void delete(Long id);

//    @Select("select * from category where type = #{type}")
    ArrayList<Category> getByType(Integer type);
}
