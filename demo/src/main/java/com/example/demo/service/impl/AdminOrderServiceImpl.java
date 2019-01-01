package com.example.demo.service.impl;

import com.example.demo.mapper.OrderItemMapper;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import com.example.demo.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * DJS
 * 2018/12/31 12:00
 * 管理员订单接口实现类
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private OrderMapper orderMapper;
    private OrderItemMapper orderItemMapper;
    private ProductMapper productMapper;
    private UserMapper userMapper;

    @Autowired
    public AdminOrderServiceImpl(OrderMapper orderMapper, OrderItemMapper orderItemMapper
            , ProductMapper productMapper, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.orderItemMapper = orderItemMapper;
        this.productMapper = productMapper;
        this.userMapper=userMapper;
    }

    /**
     * 获得不同状态的订单
     * @return
     */
    @Override
    public List<Order> getOrderAndOrderItem(Integer status) {
        List<Order> orderList=new ArrayList<Order>();
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria criteria=orderExample.createCriteria();
        criteria.andStatusEqualTo(status);
        orderExample.setOrderByClause("order_time desc");
        orderList=orderMapper.selectByExample(orderExample);
//        System.out.println("orderId"+orderList.get(0).getOrderItemList().size());
//        System.out.println("商品价格："+orderList.get(0).getOrderItemList().get(0).getProductOrderItem().getPrice());
        return orderList;
    }


    /**
     * 查找订单
     * @return
     */
    @Override
    public List<Order> seekOrder(String seekContent, Integer seekType) {
        List<Order> orderList=new ArrayList<Order>();
        OrderExample orderExample=new OrderExample();
        orderExample.setOrderByClause("order_time desc");
        OrderExample.Criteria criteria=orderExample.createCriteria();
        if(seekType==1) {
            criteria.andOrderIdEqualTo(seekContent);
            orderList=orderMapper.selectByExample(orderExample);
            return orderList;
        }else if(seekType==2){
            List<String> orderIds=new ArrayList<>();
            List<OrderItem> orderItems=getOrderIdByProductId(seekContent);
            for (OrderItem orderItem:orderItems) {
                orderIds.add(orderItem.getOrderId());
                System.out.println(orderIds.size());
            }
            criteria.andOrderIdIn(orderIds);
            orderList=orderMapper.selectByExample(orderExample);
            return orderList;
        }else if(seekType==3){
            String userId=getUserIdByUserName(seekContent);
            criteria.andUserIdEqualTo(userId);
            orderList=orderMapper.selectByExample(orderExample);
            return orderList;
        }else return null;
    }

    /**
     * 更改订单status
     * @param order 订单信息   只包含orderId和status
     */
    @Override
    public void orderShip(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Integer countsCompleteOrder() {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria criteria=orderExample.createCriteria();
        criteria.andStatusEqualTo(3);
        return orderMapper.countByExample(orderExample);
    }

    @Override
    public Integer countsReturnedOrder() {
        OrderExample orderExample=new OrderExample();
        OrderExample.Criteria criteria=orderExample.createCriteria();
        criteria.andStatusEqualTo(5);
        return orderMapper.countByExample(orderExample);
    }

    /**
     * 通过商品名找productId
     * @param name
     * @return
     */
    public String getProductIdByName(String name) {
        List<Product> products=new ArrayList<>();
        ProductExample example=new ProductExample();
        ProductExample.Criteria criteria1=example.createCriteria();
        criteria1.andProductNameEqualTo(name);
        System.out.println("找商品id----------------");
        products=productMapper.selectByExample(example);
        if(products.size()>0) {
            System.out.println("找到商品id");
            System.out.println(products.get(0).getProductId().toString());
            return products.get(0).getProductId().toString();
        }
        else {
            System.out.println("没找到商品id");
            return "";
        }
    }

    /**
     * 通过productId找orderId
     * @param name
     * @return
     */
    public List<OrderItem> getOrderIdByProductId(String name) {
        List<OrderItem> orderItems=new ArrayList<>();
        OrderItemExample orderItemExample=new OrderItemExample();
        OrderItemExample.Criteria criteria1=orderItemExample.createCriteria();
        criteria1.andProductIdEqualTo(getProductIdByName(name));
        orderItems=orderItemMapper.selectByExample(orderItemExample);
        if(orderItems.size()>0) {
            System.out.println("找到订单项目id");
            return orderItems;
        }
        else {
            System.out.println("没找到订单项目id");
            return orderItems;
        }
    }

    /**
     * 通过用户名查找用户Id
     * @param userName 用户名
     * @return
     */
    public String getUserIdByUserName(String userName) {
        List<User> users=new ArrayList<>();
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria=userExample.createCriteria();
        criteria.andUsernameEqualTo(userName);
        users=userMapper.selectByExample(userExample);
        if(users.size()>0) {
            System.out.println("根据用户名查找到用户id");
            return users.get(0).getUserId();
        }else {
            System.out.println("根据用户名找不到用户id");
            return "";
        }
    }
}
