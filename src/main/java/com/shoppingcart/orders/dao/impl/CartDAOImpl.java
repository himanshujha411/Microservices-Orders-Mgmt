package com.shoppingcart.orders.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.orders.common.LoadJPAFQueries;
import com.shoppingcart.orders.dao.CartDAO;
import com.shoppingcart.orders.dao.SequencesDAO;
import com.shoppingcart.orders.vo.Cart;

import jakarta.annotation.PostConstruct;

@Repository
public class CartDAOImpl extends JdbcDaoSupport implements CartDAO{
	
	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String cartSequence = "cart_seq";
	
    @Override
	public int insertCustomer(int customerId) {
		Integer cartId = sequencesDAO.getNextSequence(cartSequence);	
		
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("INSERT_CART"),
                new Object[] { cartId, customerId });       
	}

    @Override
    public Cart findCartByCustomerId(int customerId)
    {
    	Cart cart = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("SELECT_CART_BY_CUSTOMERID"), new Object[] { customerId },
                BeanPropertyRowMapper.newInstance(Cart.class));
	return cart;
    }
    
    @Override
	public int updateCartTotalPrice(int customerId, double totalPrice) {
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Update_Cart_For_Total_Price"),
                new Object[] { totalPrice, customerId });        
	}
    
    @Override
	public int updateCartPrice(int customerId) {
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Update_Cart_After_Submitting_Order"),
                new Object[] { customerId });        
	}
    
}
