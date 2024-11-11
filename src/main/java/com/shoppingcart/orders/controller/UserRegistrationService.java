package com.shoppingcart.orders.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.orders.bo.CartBO;
import com.shoppingcart.orders.util.ServiceConstants;
import com.shoppingcart.orders.vo.Customer;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_UserRegistrationService)
public class UserRegistrationService {

	final static Logger LOG = LoggerFactory.getLogger(UserRegistrationService.class);
	
	@Autowired
	CartBO cartBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_InsertCart, method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> insertCart(@RequestBody Customer customer,
    												HttpServletRequest request) throws AuthenticationException 
	{
		//Customer Validation
		if(customer==null) {
			
			LOG.info("customer in cart is null");
			return new ResponseEntity<>(0,HttpStatus.BAD_REQUEST);
		}
		
		//Insert Cart
		int insertCartCount = cartBO.insertCart(customer.getCustomerId());
		if(insertCartCount<=0)
		{
			
			LOG.info("insert customer failed");
			return new ResponseEntity<>(0,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(1, HttpStatus.OK);
		
	}
}
