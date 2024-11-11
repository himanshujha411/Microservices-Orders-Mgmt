package com.shoppingcart.orders.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.orders.bo.CartBO;
import com.shoppingcart.orders.dao.CartDAO;
import com.shoppingcart.orders.vo.Cart;

@Service
public class CartBOImpl implements CartBO{
	
	@Autowired
	CartDAO cartDAO;
	
	@Override
	public int insertCart(int customerId)
	{
		return cartDAO.insertCustomer(customerId);
	}
	
	@Override
	public Cart findCartByCustomerId(int customerId)
	{
		return cartDAO.findCartByCustomerId(customerId);
	}
	
	@Override
	public int updateCartTotalPrice(int customerId, double totalPrice)
	{
		return cartDAO.updateCartTotalPrice(customerId, totalPrice);
	}

	@Override
	public int updateCartPrice(int customerId)
	{
		return cartDAO.updateCartPrice(customerId);
	}
}
