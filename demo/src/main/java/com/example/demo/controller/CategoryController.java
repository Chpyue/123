package com.example.demo.controller;



import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/Category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    /**
     * 查询商品种类信息
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView getCategoryList(Model model){
        System.out.println("---");
        List<Category> categoryList=categoryService.getCategoryList();
        System.out.println(categoryList.size());
        model.addAttribute("categoryList",categoryList);
        return new ModelAndView("category/Category");

    }

    /**
     * 添加新的种类
     * @param flag 1.跳转添加页面 2.执行添加操作
     * @param mv
     * @param category 要添加的对象
     * @return
     */
    @RequestMapping("/addCategory")
    public ModelAndView addCategory(String flag,ModelAndView mv, @ModelAttribute Category category){

        if (flag.equals("1")){
            mv.setViewName("category/showAddCategory");
        }else {
            categoryService.addCategory(category);
            mv.setViewName("redirect:/category/list");
        }
        return mv;
    }

    /**
     * 删除种类
     * @param id 通过种类id删除
     * @param mv
     * @return
     */
    @RequestMapping("/deleteCategory")
    public ModelAndView removeCategory(Integer id,ModelAndView mv){
        categoryService.removeCategoryById(id);
        mv.setViewName("redirect:/category/list");
        return mv;
    }

    /**
     * 修改种类信息
     * @param flag
     * @param mv
     * @param category
     * @return
     */
    @RequestMapping("/modifiCategory")
    public ModelAndView modifiCategroy(String flag,ModelAndView mv,@ModelAttribute Category category){
        if (flag.equals("1")){
            //获取目标对象
            Category category1=categoryService.findByCategoryId(category.getCategoryId());
            mv.addObject("category",category1);
            mv.setViewName("Category/showModifiCategory");
        }else {
            categoryService.modifiCategory(category);
            mv.setViewName("redirect:/Category/list");
        }
        return mv;
    }
}
