package com.example.demo.service;

import com.example.demo.model.Cart;


import java.util.List;

/**
 * Created by ilcy on 2018/12/24.
 */

public interface CartService {
    /**
     * 添加单个商品到购物车
     * @param cart
     */
    void insert(Cart cart);


    /**
     * 获取所有商品购物车
     * @return
     */
    List<Cart> getAllCarts(String user_id);



    /**
     * 根据cart_id获取单个商品购物车
     * @param cart_id
     * @return
     */
    Cart getSingleCart(String cart_id);



    /**
     * 修改单个商品购物车
     * @param cart
     */
    void updateCart(Cart cart);




    /**
     * 删除单个商品购物车
     * @param cartId
     */
    void deleteCart(String cartId);


}
