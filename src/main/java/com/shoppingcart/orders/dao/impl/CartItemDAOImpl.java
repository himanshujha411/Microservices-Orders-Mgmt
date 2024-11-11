package com.shoppingcart.orders.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.orders.common.LoadJPAFQueries;
import com.shoppingcart.orders.dao.CartItemDAO;
import com.shoppingcart.orders.dao.SequencesDAO;
import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.CartItem;
import com.shoppingcart.orders.vo.Product;

import jakarta.annotation.PostConstruct;

@Repository
public class CartItemDAOImpl extends JdbcDaoSupport implements CartItemDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String cartItemSequence = "cartitem_seq";
    
    @Override
    public CartItem getCartItemByCartIdAndProductId(int cartId, int productId)
    {
    	CartItem cartItem = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("Select_CartItem_By_CartId_and_productId"), new Object[] { cartId, productId },
                BeanPropertyRowMapper.newInstance(CartItem.class));
    	return cartItem;
    }
    
    @Override
    public List<CartItem> getCartItemByCartId(int cartId)
    {
    	List<CartItem> cartItem = null;
    	cartItem = getJdbcTemplate().query(LoadJPAFQueries.getQueryById("Select_CartItem_By_CartId"), new Object[]{cartId},
				BeanPropertyRowMapper.newInstance(CartItem.class));
    	return cartItem;
    }
    
    @Override
	public int updateCartItem(CartItem cartItem, double price, int quantity) {
		
		return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Update_CartItem"),
                new Object[] { price, quantity, cartItem.getCartItemId() });
                
	}
    
    @Override
    public int insertCartItem(Product product, Cart cart)
    {
    	Integer cartItemId = sequencesDAO.getNextSequence(cartItemSequence);
    	return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Insert_CartItem"),
                new Object[] { cartItemId, product.getPrice(), 
                		cart.getCartId(), product.getProductId(), product.getName()
                });
    }
    
    @Override
    public int deleteCartItem(Product product, Cart cart)
    {
    	return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Delete_CartItem"),
                new Object[] { cart.getCartId(), product.getProductId() 
                });
    }
    
    @Override
    public int deleteCartItemAfterOrderSubmit(int cartId)
    {
    	return getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Delete_CartItem_After_Order_Submit"),
                new Object[] { cartId });
    }
}
