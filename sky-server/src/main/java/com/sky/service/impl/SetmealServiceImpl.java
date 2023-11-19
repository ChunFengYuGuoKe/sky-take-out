package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.exception.SetmealSaveFailedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 添加套餐并修改对应dish信息
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0){
            Setmeal setmeal = new Setmeal();
            BeanUtils.copyProperties(setmealDTO, setmeal);

            setmealMapper.insert(setmeal);

            Long setmealId = setmeal.getId();

            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
            }

            setmealDishMapper.insertBatch(setmealDishes);
        }else{
            throw new SetmealSaveFailedException(MessageConstant.SETMEAL_NO_DISH);
        }
    }

    /**
     * 根据id查询套餐
     * @param setmealId
     */
    @Override
    public SetmealVO getById(Long setmealId) {
        SetmealVO setmealVO = new SetmealVO();

        //获取套餐信息
        Setmeal setmeal = setmealMapper.getById(setmealId);
        BeanUtils.copyProperties(setmeal, setmealVO);

        //获取套餐的菜品信息
        List<SetmealDish> setmealDishes = setmealDishMapper.getDishWithSetmealId(setmealId);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<Setmeal> setmeals = setmealMapper.page(setmealPageQueryDTO);

        return new PageResult(setmeals.getTotal(), setmeals.getResult());
    }

    /**
     * 更新套餐信息
     * @param setmealDTO
     */
    @Transactional
    @Override
    public void update(SetmealDTO setmealDTO) {
        //删除原有菜品记录
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());

        //查询新的菜品里是否有停售的菜品，不对，如果只是把菜品加到套餐里的话应该不需要判断，在起售的时候才需要判断
        /*List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            Integer status = setmealDishMapper.queryDishStatusByDishId(setmealDish.getDishId());
            if (status == 1){
                throw new SetmealSaveFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
            }
        }*/

        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //更新套餐信息
        //修改时要不要把套餐设置为停售的？yaoyaoyao
        setmeal.setStatus(StatusConstant.DISABLE);
        setmealMapper.update(setmeal);


        //更新套餐菜品对应信息,其实就是插入，我要怎样把一个list的数据都插入进去？似乎要用foreach结合collection来做
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishMapper.insertBatch(setmealDishes);
    }

    @Transactional
    @Override
    public void deleteBatch(List<Integer> ids) {
        List<Integer> statuses = setmealMapper.queryStatusesByIds(ids);
        for (Integer status : statuses) {
            if(status == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //删除套餐信息
        setmealMapper.deleteBatch(ids);

        //删除套餐菜品信息
        setmealDishMapper.deleteBySetmealIds(ids);
    }


    @Override
    public void bp(Integer status, Long setmealId) {
        //先判断是启用还是禁用，如果是禁用就直接改就行了，启用的话要判断一下菜品状态
        Setmeal setmeal = setmealMapper.getById(setmealId);
        setmeal.setStatus(status);
        if(status == StatusConstant.DISABLE){
            //这里用bp不好，因为不能自动填充公共字段了，还是用update更好一点
            setmealMapper.update(setmeal);
        }else{
            List<Integer> statuses = setmealDishMapper.getDishesStatusBySetmealId(setmealId);
            for (Integer sta : statuses) {
                if(sta == StatusConstant.DISABLE){
                    throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                }
            }
            setmealMapper.update(setmeal);
        }
    }
}
