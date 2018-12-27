package com.example.demo.service.impl;

import com.example.demo.mapper.CategoryMapper;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Category;
import com.example.demo.model.CategoryExample;
import com.example.demo.model.Product;
import com.example.demo.model.ProductExample;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * 商品的实现类
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;
    @Autowired
    public ProductServiceImpl( ProductMapper productMapper) {

        this.productMapper = productMapper;
    }

    /**
     *  获取所有商品信息集合
     * @return
     */
    @Autowired
    public List<Product> getProductList(){
        ProductExample example=new ProductExample();
        List<Product> productList=productMapper.selectByExample(example);
        return productList;

    }

    /**
     * 通过商品id查询商品
     * @param productId
     * @return
     */
    @Override
    public Product findByProductId(Integer productId) {
        ProductExample example=new ProductExample();
        ProductExample.Criteria criteria=example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        List<Product> productList=productMapper.selectByExample(example);
        if(productList.size()!=0){
            Product product=productList.get(0);
            return product;
        }else {
            return null;
        }
    }

    /**
     * 添加商品
     * @param product
     * @return
     */
    @Override
    public Product addProduct(Product product) {
        ProductExample example=new ProductExample();
        productMapper.insertSelective(product);
        return product;
    }

    /**
     * 通过id删除商品
     * @param productId
     */
    @Override
    public void removeProductById(Integer productId) {
        ProductExample example=new ProductExample();
//        ProductExample.Criteria criteria=example.createCriteria();
//        criteria.andProductIdEqualTo(productId);
        productMapper.deleteByPrimaryKey(productId);
    }

    /**
     * 修改商品信息
     * @param product
     */
    @Override
    public void modifiProduct(Product product) {
        ProductExample example=new ProductExample();
        productMapper.updateByPrimaryKeySelective(product);

    }



}
