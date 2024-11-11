package com.shoppingcart.orders.bo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.orders.bo.CartItemBO;
import com.shoppingcart.orders.dao.CartItemDAO;
import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.CartItem;
import com.shoppingcart.orders.vo.Product;

@Service
public class CartItemBOImpl implements CartItemBO{
	
	@Autowired
	CartItemDAO cartItemDAO;
	
	@Override
	public CartItem getCartItemByCartIdAndProductId(int cartId, int productId)
	{
		return cartItemDAO.getCartItemByCartIdAndProductId(cartId, productId);
	}

	@Override
	public int updateCartItem(CartItem cartItem, double price, int quantity) 
	{
		return cartItemDAO.updateCartItem(cartItem, price, quantity);
	}
	
	@Override
	public int insertCartItem(Product product, Cart cart)
	{
		return cartItemDAO.insertCartItem(product, cart);
	}
	
	@Override
	public int deleteCartItem(Product product, Cart cart)
	{
		return cartItemDAO.deleteCartItem(product, cart);
	}
	
	@Override
	public List<CartItem> getCartItemByCartId(int cartId)
	{
		return cartItemDAO.getCartItemByCartId(cartId);
	}
	
	@Override
	public int deleteCartItemAfterOrderSubmit(int cartId)
	{
		return cartItemDAO.deleteCartItemAfterOrderSubmit(cartId);
	}
}
