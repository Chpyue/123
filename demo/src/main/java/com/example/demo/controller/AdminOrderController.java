package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.AdminOrderService;
import com.example.demo.utils.Msg;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminOrderController {

    @Autowired
    private AdminOrderService orderService;

    /**
     * 获得订单list
     * @param type
     * @param pn
     * @return 返回订单列表json数据
     */
    @ResponseBody
    @GetMapping("/adminGetOrders/{type}")
    public Msg getOrder(@PathVariable("type") Integer type, @RequestParam(value="pn",defaultValue = "1")Integer pn) {
        PageHelper.startPage(pn,5);
        List<Order> orders=orderService.getOrderAndOrderItem(type);
        System.out.println("订单个数："+orders.size());
        PageInfo page = new PageInfo(orders,5);
        return Msg.success().add("pageInfo",page);
    }

    /**
     * 查找订单
     * @param seekContent 查找内容
     * @param seekType 查找类型
     * @return 返回json数据
     */
    @PostMapping("/adminSeekOrders")
    public String getOrder(Model model,@RequestParam("seekContent")String seekContent
            ,@RequestParam("seekType")Integer seekType) {
        List<Order> orderList= orderService.seekOrder(seekContent,seekType);
        model.addAttribute("orderList",orderList);
        return "admin/order/seekOrder";
    }

    /**
     * 订单发货、同意退货、拒绝退货   编辑订单状态
     * @return
     */
    @ResponseBody
    @PostMapping("/adminOrderShip")
    public Msg orderShip(Order order) {
        orderService.orderShip(order);
        return Msg.success();
    }

    /**
     * 路径跳转
     * @param path
     * @return
     */
    @GetMapping("/adminOrder/{path}")
    public String toPath(@PathVariable String path) {
        return "admin/order/"+path;
    }


}
