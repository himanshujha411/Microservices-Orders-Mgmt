package com.shoppingcart.orders.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.orders.bo.CartBO;
import com.shoppingcart.orders.bo.CartItemBO;
import com.shoppingcart.orders.bo.ProductBO;
import com.shoppingcart.orders.services.AuthService;
import com.shoppingcart.orders.util.ServiceConstants;
import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.CartItem;
import com.shoppingcart.orders.vo.Customer;
import com.shoppingcart.orders.vo.Product;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_CartService)
public class CartServices {
	
	final static Logger LOG = LoggerFactory.getLogger(CartServices.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	
	@Value("${service.auth.auth-base-url}")
    private String authBaseURL;

	@Autowired
	CartBO cartBO;

	@Autowired
	CartItemBO cartItemBO;
	
	@Autowired
	ProductBO productBO;
	
	@Autowired
	AuthService authService;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_URL_addToCart, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> addToCart(@RequestBody Product products, HttpServletRequest request)
	{
		int productId = products.getProductId();
		Map<String,Object> statusMap = new HashMap<>();
		String token = request.getHeader(TOKEN_HEADER);
		System.out.println("The product Id is: "+productId);
		System.out.println("The token is: "+token);
		int customerId = 0;
		int updateCartItem =0;
		int insertCartItem =0;
		int updateCartTotalPrice=0;
		
		if(token == null) {
			LOG.error("Missing Token");
			statusMap.put("error", "Please login again");
        	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
		}
		// fetch customer Id from auth module
		try {
			Customer customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0)
		{
			statusMap.put("error","Customer is NULL");
			LOG.info("Customer is NULL");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Cart cart = cartBO.findCartByCustomerId(customerId);
		double totalPrice = cart.getTotalPrice();
		Product product = productBO.getProductById(productId);
		try
		{
			CartItem cartItem = cartItemBO.getCartItemByCartIdAndProductId(cart.getCartId(), productId);
			double price = cartItem.getPrice() + product.getPrice();
			int quantity = cartItem.getQuality() + 1;
			updateCartItem = cartItemBO.updateCartItem(cartItem, price, quantity);
			
			if(updateCartItem>=0) {
				totalPrice = totalPrice + product.getPrice();
				updateCartTotalPrice = cartBO.updateCartTotalPrice(customerId, totalPrice);
				if(updateCartTotalPrice>=1) {
					LOG.info("Cart price Updated successfully");
				}
				statusMap.put("success","Item successfully added to cart!");
				LOG.info("Item updated successfully");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			}
			else {
				statusMap.put("error","Failed to add item to cart");
				LOG.info("Failed to update item to cart");
				return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}
		catch(Exception e){
		}
		totalPrice = totalPrice + product.getPrice();
		insertCartItem = cartItemBO.insertCartItem(product, cart);
		updateCartTotalPrice = cartBO.updateCartTotalPrice(customerId, totalPrice);
		
		if(insertCartItem<=0) {
			statusMap.put("error","Failed to add item to cart");
			LOG.info("Failed to add item to cart");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		statusMap.put("success","Item successfully added to cart!");
		LOG.info("Item added successfully");
		
		return new ResponseEntity<>(statusMap, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_URL_deleteItemFromCart, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> deleteItemFromCart(@RequestBody Product products,	HttpServletRequest request)
	{
		int productId = products.getProductId();
		Map<String,Object> statusMap = new HashMap<>();
		String token = request.getHeader(TOKEN_HEADER);
		int customerId = 0;
		int updateCartItem =0;
		int deleteCartItem =0;
		int updateCartTotalPrice=0;
		
		if(token == null) {
			LOG.error("Missing Token");
			statusMap.put("error", "Please login again");
        	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
		}
		// fetch customer Id from auth module
		try {
			Customer customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0)
		{
			statusMap.put("error","Customer is NULL");
			LOG.info("Customer in deleteFromCart API is NULL");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Cart cart = cartBO.findCartByCustomerId(customerId);
		double totalPrice = cart.getTotalPrice();
		Product product = productBO.getProductById(productId);
		CartItem cartItem = null;
		try
		{
			cartItem = cartItemBO.getCartItemByCartIdAndProductId(cart.getCartId(), productId);
			double reducedPrice=0;
			int quantity = cartItem.getQuality();
			if(quantity<=1)
			{
				reducedPrice = product.getPrice() * cartItem.getQuality();
				deleteCartItem = cartItemBO.deleteCartItem(product, cart);
				
				if(deleteCartItem>=0) {
					totalPrice = totalPrice - reducedPrice;
					updateCartTotalPrice = cartBO.updateCartTotalPrice(customerId, totalPrice);
					if(updateCartTotalPrice>=1) {
						LOG.info("Cart price Updated successfully");
					}
					statusMap.put("success","Item quantity reduced from cart!");
					LOG.info("Item deleted successfully");
					return new ResponseEntity<>(statusMap, HttpStatus.OK);
				}
				else {
					statusMap.put("error","Failed to delete item from cart");
					LOG.info("Failed to delete item from cart");
					return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
				}	
			}
			double price = cartItem.getPrice() - product.getPrice();
			totalPrice = totalPrice - product.getPrice();
			quantity = quantity - 1;
			updateCartItem = cartItemBO.updateCartItem(cartItem, price, quantity);
	
			if(updateCartItem>=0) {
				updateCartTotalPrice = cartBO.updateCartTotalPrice(customerId, totalPrice);
				if(updateCartTotalPrice>=1) {
					LOG.info("Cart price Updated successfully");
				}
				statusMap.put("success","Item quantity reduced from cart!");
				LOG.info("Item deleted successfully");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			}
			else {
				statusMap.put("error","Failed to delete item from cart");
				LOG.info("Failed to delete item from cart");
				return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}
		catch(Exception e){
			if(cartItem==null) {
				statusMap.put("success","Item does not exist in cart!");
				LOG.info("Item does not exist in cart!");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			}
		}
		
		statusMap.put("error","Failed to delete item from cart");
		LOG.info("Failed to delete item from cart");
		
		return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	@RequestMapping(value = ServiceConstants.REQ_MAP_URL_deleteFromCart, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> deleteFromCart(@RequestBody Product products,
													HttpServletRequest request)
	{
		int productId = products.getProductId();
		Map<String,Object> statusMap = new HashMap<>();
		String token = request.getHeader(TOKEN_HEADER);
		int customerId = 0;
		int deleteCartItem =0;
		int updateCartTotalPrice=0;
		
		if(token == null) {
			LOG.error("Missing Token");
			statusMap.put("error", "Please login again");
        	return new ResponseEntity<>(statusMap,HttpStatus.UNAUTHORIZED);
		}
		// fetch customer Id from auth module
		try {
			Customer customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0)
		{
			statusMap.put("error","Customer is NULL");
			LOG.info("Customer in deleteFromCart API is NULL");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Cart cart = cartBO.findCartByCustomerId(customerId);
		double totalPrice = cart.getTotalPrice();
		Product product = productBO.getProductById(productId);
		CartItem cartItem = null;
		double reducedPrice=0;
		try
		{
			cartItem = cartItemBO.getCartItemByCartIdAndProductId(cart.getCartId(), productId);
			deleteCartItem = cartItemBO.deleteCartItem(product, cart);
			reducedPrice = product.getPrice() * cartItem.getQuality();
			totalPrice = totalPrice - reducedPrice;			
				
			if(deleteCartItem>0) {
				updateCartTotalPrice = cartBO.updateCartTotalPrice(customerId, totalPrice);
				if(updateCartTotalPrice>=1) {
					LOG.info("Cart price Updated successfully");
				}
				statusMap.put("success","Item successfully deleted from cart!");
				LOG.info("Item deleted successfully");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			}
			else {
				statusMap.put("error","Failed to delete item from cart");
				LOG.info("Failed to delete item from cart");
				return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
			}	
		}
		catch(Exception e){			
			if(cartItem==null) {
				statusMap.put("success","Item does not exist in cart!");
				LOG.info("Item does not exist in cart!");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			}
		}
		
		statusMap.put("error","Failed to delete item from cart");
		LOG.info("Failed to delete item from cart");
		
		return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_URL_getCartFromToken, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getCartFromToken(HttpServletRequest request) throws AuthenticationException 
	{
		int customerId =0;
		Map<String,Object> statusMap = new HashMap<>();
		
		try {
			Customer customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0) {
			statusMap.put("error","Customer is NULL");
			LOG.info("Customer in deleteFromCart API is NULL");
			return new ResponseEntity<>(statusMap,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else {
			Cart cart = cartBO.findCartByCustomerId(customerId);
			return new ResponseEntity<>(cart,HttpStatus.OK);
		}
	}
}
