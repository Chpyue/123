package com.example.demo.service;

import com.example.demo.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DJS
 * 2018/12/31 12：00
 * 管理员订单管理接口
 */
@Component

public interface AdminOrderService {
    /**
     * 获得订单列表
     * @param status
     * @return
     */
    List<Order> getOrderAndOrderItem(Integer status);

    /**
     * 查找订单
     * @param seekContent
     * @param seekType
     * @return
     */
    List<Order> seekOrder(String seekContent, Integer seekType);

    /**
     * 更改订单status
     * @param order 订单信息   只包含orderId和status
     */
    void orderShip(Order order);

    /**
     * 确认收货的订单数量
     * @return
     */
    Integer countsCompleteOrder();

    /**
     * 确认收货的订单数量 完成退货的订单数
     * @return
     */
    Integer countsReturnedOrder();
}
