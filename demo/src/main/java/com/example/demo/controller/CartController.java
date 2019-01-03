package com.example.demo.controller;




import com.example.demo.model.Cart;
import com.example.demo.model.Product;
import com.example.demo.model.custom.CartCustom;

import com.example.demo.service.CartService;

import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.web.servlet.ModelAndView;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by ilcy on 2018/12/24.
 */


@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping(value = "/cart")
public class CartController {


    @Autowired
    private CartService cartService;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;

    /**
     * 插入单个商品购物车
     * @param productId
     */
    @GetMapping("/insert")
    public void insert(String productId){
        List<Cart> list=cartService.getAllCarts(userService.getUser().getUserId());
        //遍历集合
        for( int i=0;i<=list.size();i++){
            //遍历完集合没有符合商品，执行添加新的购物车条目
            if (i>=list.size()){
                Cart cart=new Cart();
                cart.setCount(1.0);
                cart.setCartId(UUIDUtil.getUUID());
                cartService.insert(cart);
                break;
            }
            //判断该用户所拥有的购物车是否已有该商品，若有即更新数量
            Cart c=list.get(i);
            if (Integer.parseInt(productId)==c.getProductId()){
                c.setCount(c.getCount()+1.0);
                cartService.updateCart(c);
                break;
            }

        }
    }



    /**
     * 显示我的购物车
     * @param model
     * @return
     */
    @GetMapping(value = "/list")
    public ModelAndView getCartList( Model model){
        Double sum=0.0;
        List<Cart> list=cartService.getAllCarts(userService.getUser().getUserId());
        System.out.println("正在打印购物车表"+list);
        ArrayList<CartCustom> cartCustomList=new ArrayList<>();
        //遍历购物车，根据商品id获取商品对象
        for (Cart cart:list) {
            CartCustom cartCustom=new CartCustom();
            Product product= productService.findProductById(cart.getProductId());
            cartCustom.setProductName(product.getProductName());
            cartCustom.setProductPrice(product.getPrice());
            cartCustom.setProductCount(cart.getCount());
            sum=sum+(product.getPrice())*(cart.getCount());
            cartCustom.setCartId(cart.getCartId());
            cartCustomList.add(cartCustom);
        }
        System.out.println("sum================="+sum);
        System.out.println("数据已装车"+cartCustomList);
        model.addAttribute("cartCustomList",cartCustomList);
        model.addAttribute("summary",sum);
        return  new ModelAndView("cart/ct","cartModel",model);
    }




    /**
     * 删除单个商品购物车条目
     * @param cartId
     */
    @GetMapping(value = "/delete/{cartId}")
   public ModelAndView deleteSingleCart(@PathVariable("cartId")String cartId,ModelAndView mv){
        cartService.deleteCart(cartId);
        String userId=userService.getUser().getUserId();
        mv.addObject("userId",userId);
        mv.setViewName("redirect:/cart/list");
        return mv;
    }



    /**
     * 批量删除商品条目
     * @param ids
     * @return
     */
    @GetMapping(value = "/removeCarts")
    public ModelAndView deleteAllCarts(String ids,ModelAndView mv){
        System.out.println("ids============="+ids);
        String[] arr=ids.split(",");
        for (String id:arr
        ) {
            cartService.deleteCart(id);
        }
        String userId=userService.getUser().getUserId();
        mv.addObject("userId",userId);
        mv.setViewName("redirect:/cart/list");
        return mv;
   }




    /**
     * 修改购物车数量
     * @param productCount
     * @param cartId
     */
   @PostMapping(value = "/update/count")
   @ResponseBody
   public Double updateCount(String productCount,String cartId,String userId){
       Double s2=0.0;
       Double num= Double.parseDouble(productCount);
       Cart cart=cartService.getSingleCart(cartId);
       cart.setCount(num);
       cartService.updateCart(cart);
       List<Cart> list=cartService.getAllCarts(userService.getUser().getUserId());
       for (Cart c:list
       ) {
           Product product= productService.findProductById(c.getProductId());
           s2=s2+(c.getCount())*(product.getPrice());
       }
       return s2;
   }

}
