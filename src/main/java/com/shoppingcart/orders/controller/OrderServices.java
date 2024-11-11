package com.shoppingcart.orders.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.orders.bo.CartBO;
import com.shoppingcart.orders.bo.CartItemBO;
import com.shoppingcart.orders.bo.CustomerOrderBO;
import com.shoppingcart.orders.services.AuthService;
import com.shoppingcart.orders.util.ServiceConstants;
import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.Customer;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_CustomerOrderServices)
public class OrderServices {

	final static Logger LOG = LoggerFactory.getLogger(CartServices.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	
	@Autowired
	AuthService authService;
	
	@Autowired
	CartBO cartBO;
	
	@Autowired
	CartItemBO cartItemBO;
	
	@Autowired
	CustomerOrderBO custOrderBO;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_METHOD_URL_SubmitOrder , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> submitOrder(HttpServletRequest request) throws AuthenticationException 
	{
		Map<String, Object> statusMap = new HashMap<>();
		int customerId=0;
		Customer customer =null;
		int UpdateCartItems = 0;
		int UpdateCartPrice = 0;
		int CustomerOrderId=0;
		
		try {
			customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0) {
			statusMap.put("error", "Unable to fetch Customer");
			return new ResponseEntity<>(statusMap, HttpStatus.UNAUTHORIZED);
		}
		
		Cart cart = cartBO.findCartByCustomerId(customerId);
		
		try {
			CustomerOrderId = custOrderBO.insertCustomerOrder(customer, cart.getCartId());
			if(CustomerOrderId>=1) {
				statusMap.put("orderId", CustomerOrderId);
				UpdateCartPrice = cartBO.updateCartPrice(customerId);
				UpdateCartItems = cartItemBO.deleteCartItemAfterOrderSubmit(cart.getCartId());
			}
			else {
				statusMap.put("error", "Insert into CustomerOrder failed");
				return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(UpdateCartItems<1 || UpdateCartPrice<1) {
			statusMap.put("error", "Update into Cart and Cart Item is failed after successful order submit");
			return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		statusMap.put("success", "Order submitted successfully");
		return new ResponseEntity<>(statusMap, HttpStatus.OK);
	}
	
}
