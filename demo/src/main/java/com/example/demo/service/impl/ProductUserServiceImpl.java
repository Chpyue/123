package com.example.demo.service.impl;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryExample;
import com.example.demo.model.Product;
import com.example.demo.model.ProductExample;
import com.example.demo.service.ProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductUserServiceImpl implements ProductUserService {


    private ProductMapper productMapper;
    private CategoryMapper categoryMapper;

    @Autowired
    public ProductUserServiceImpl(ProductMapper productMapper, CategoryMapper categoryMapper) {
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }


    /**
     * 输出全部商品类型
     *
     * @return
     */
    @Override
    public List<Category> categoryList() {
        CategoryExample categoryExample = new CategoryExample();                                //显示所有商品种类
        List<Category> categoryName = categoryMapper.selectByExample(categoryExample);
        return categoryName;
    }

    /**
     * 根据商品种类查询
     * 并按照对应字段排序
     * @param kind
     * @param orderbyname
     * @return
     */
    @Override
    public List<Product> findByKind(int kind, String orderbyname) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIsEffectiveEqualTo(1);                                                   //判断是否下架
        criteria.andCategoryIdEqualTo(kind);                                                        //判断商品类别
        productExample.setOrderByClause(orderbyname + " desc");                                     //按照对应字段查询
        List<Product> productList = productMapper.selectByExample(productExample);                  //输出全部商品
        return productList;
    }

    /**
     * 根据名称模糊查询
     * 并按照对应字段排序排序
     *
     * @param name
     * @return
     */
    @Override
    public List<Product> findByName(String name, String orderbyname) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIsEffectiveEqualTo(1);                                                   //判断商品是否下架
        criteria.andProductNameLike(name);                                                          //模糊查询
        productExample.setOrderByClause(orderbyname + "desc");                                      //按照对应字段排序排序
        return productMapper.selectByExample(productExample);
    }

    /**
     * 显示所有商品
     * 并按照对应字段排序
     * @return
     */
    @Override
    public List<Product> findAll(String orderbyname) {
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andIsEffectiveEqualTo(1);                                                      //判断是否下架
        productExample.setOrderByClause(orderbyname + "desc");                                         //按照对应字段排序
        return productMapper.selectByExample(productExample);                                          //输出商品
    }

    /**
     * 单件商品的信息
     * @param productId
     * @return
     */
    @Override
    public List<Product> productInfo(int productId){
        ProductExample productExample = new ProductExample();
        ProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andProductIdEqualTo(productId);
        return productMapper.selectByExample(productExample);
    }
}
