package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;


@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     * @param orders
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     * @param orders
     */
    void update(Orders orders);

    /**
     * 查询历史订单
     * @param userId
     * @return
     */
    @Select("select * from orders where orders.user_id = #{userId}")
    Page<OrderVO> historyOrders(Long userId);

    /**
     * 动态查询
     * @param orders
     * @return
     */
    List<Orders> list(Orders orders);

    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 取消订单
     *
     * @param id
     * @param now
     */
    @Update("update orders set status = 6, cancel_time = #{now} where id = #{id}")
    void cancelOrder(Long id, LocalDateTime now);

    /**
     * 商户取消订单
     *
     * @param ordersCancelDTO
     * @param now
     */
    @Update("update orders set status = 6, cancel_reason = #{ordersCancelDTO.cancelReason}, cancel_time = #{now} where id = #{ordersCancelDTO.id}")
    void adminCancelOrder(OrdersCancelDTO ordersCancelDTO, LocalDateTime now);

    /**
     * 各个状态的订单数量统计
     * @param status
     * @return
     */
    @Select("select count(*) from orders where status = #{status}")
    Integer getOrderStatistics(Integer status);

    /**
     * 完成订单
     * @param id
     */
    @Update("update orders set status = 5 where id = #{id}")
    void completeOrder(Long id);

    /**
     * 条件查询订单
     * @param ordersPageQueryDTO
     * @return
     */
    Page<Orders> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据订单状态和下单时间查询订单
     * @param status
     * @param orderTime
     * @return
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime}")
    List<Orders> getByStatusAndOrderTime(Integer status, LocalDateTime orderTime);
}