package com.sky.controller.admin;

import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/order")
@Api(tags = "订单管理接口")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 商家取消订单
     * @param ordersCancelDTO
     * @return
     */
    @PutMapping("/cancel")
    @ApiOperation("商家取消订单")
    public Result cancelOrder(@RequestBody OrdersCancelDTO ordersCancelDTO) {
        orderService.adminCancelOrder(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 各个状态的订单数量统计
     * @return
     */
    @GetMapping("/statistics")
    @ApiOperation("各个状态的订单数量统计")
    public Result<OrderStatisticsVO> getOrderStatistics(){
        OrderStatisticsVO orderStatisticsVO = orderService.getOrderStatistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 完成订单
     * @param id
     * @return
     */
    @PutMapping("/complete/{id}")
    @ApiOperation("完成订单")
    public Result completeOrder(@PathVariable Long id){
        orderService.completeOrder(id);
        return Result.success();
    }

    /**
     * 拒单
     * @param ordersRejectionDTO
     * @return
     */
    @PutMapping("/rejection")
    @ApiOperation("拒单")
    public Result rejectOrder(@RequestBody OrdersRejectionDTO ordersRejectionDTO){
        orderService.rejectOrder(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 接单
     * @param id
     * @return
     */
    @PutMapping("/confirm")
    @ApiOperation("接单")
    public Result confirmOrder(@RequestBody Long id) {
        orderService.confirmOrder(id);
        return Result.success();
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    @GetMapping("/details/{id}")
    @ApiOperation("查询订单详情")
    public Result<OrderVO> getOrderDetail(@PathVariable Long id) {
        OrderVO orderVO = orderService.orderDetail(id);
        return Result.success(orderVO);
    }

    /**
     * 派送订单
     * @param id
     * @return
     */
    @PutMapping("/delivery/{id}")
    @ApiOperation("派送订单")
    public Result deliveryOrder(@PathVariable Long id) {
        orderService.deliveryOrder(id);
        return Result.success();
    }


    /**
     * 条件搜索订单
     * @return
     */
    @GetMapping("/conditionSearch")
    @ApiOperation("条件搜索订单")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("条件搜索订单:{}", ordersPageQueryDTO);
        PageResult searchedOrders = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(searchedOrders);
    }
}