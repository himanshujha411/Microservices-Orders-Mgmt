package com.shoppingcart.orders.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.orders.common.LoadJPAFQueries;
import com.shoppingcart.orders.dao.ProductDAO;
import com.shoppingcart.orders.vo.Product;

import jakarta.annotation.PostConstruct;

@Repository
public class ProductDAOImpl extends JdbcDaoSupport implements ProductDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Override
    public List<Product> getAllProduct()
    {
    	return getJdbcTemplate().query(LoadJPAFQueries.getQueryById("GET_ALL_PRODUCTS"), new Object[] {},
                BeanPropertyRowMapper.newInstance(Product.class));
    	//return products;
    }
    
    @Override
    public Product getProductById(int productId)
    {
    	Product product = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("GET_PRODUCT_BY_PRODUCT_ID"), new Object[] { productId },
                BeanPropertyRowMapper.newInstance(Product.class));
    	return product;
    }
}
