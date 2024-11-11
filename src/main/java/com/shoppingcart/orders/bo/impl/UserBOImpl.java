package com.shoppingcart.orders.bo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.orders.bo.UserBO;
import com.shoppingcart.orders.dao.UserDAO;
import com.shoppingcart.orders.vo.User;


@Service
public class UserBOImpl implements UserBO{

	@Autowired
	UserDAO userDAO;
	
	@Override
	public User findUserByUsername(String username) {
		return userDAO.findUserByUsername(username);
	}
	
}
