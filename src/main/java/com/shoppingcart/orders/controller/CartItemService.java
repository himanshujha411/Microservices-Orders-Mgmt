package com.shoppingcart.orders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shoppingcart.orders.bo.CartBO;
import com.shoppingcart.orders.bo.CartItemBO;
import com.shoppingcart.orders.services.AuthService;
import com.shoppingcart.orders.util.ServiceConstants;
import com.shoppingcart.orders.vo.Cart;
import com.shoppingcart.orders.vo.CartItem;
import com.shoppingcart.orders.vo.Customer;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = ServiceConstants.REQ_MAP_URL_CartItemService, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
public class CartItemService {

	final static Logger LOG = LoggerFactory.getLogger(CartServices.class);
	
	private static final String TOKEN_HEADER = "Authorization";
	
	@Value("${service.auth.auth-base-url}")
    private String authBaseURL;
	
	@Autowired
	CartBO cartBO;

	@Autowired
	CartItemBO cartItemBO;
	
	@Autowired
	AuthService authService;
	
	@RequestMapping(value = ServiceConstants.REQ_MAP_URL_getCartById, method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CartItem>> getCartById(HttpServletRequest request) throws AuthenticationException 
	{
		
		Map<String,Object> statusMap = new HashMap<>();
		int customerId = 0;
		
		try {
			Customer customer = authService.findCustomerFromToken(request);
			customerId = customer.getCustomerId();
		}catch(Exception e) {}
		
		if(customerId<=0)
		{
			LOG.info("Customer is NULL");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		Cart cart = cartBO.findCartByCustomerId(customerId);
		
		List<CartItem> cartItemList = cartItemBO.getCartItemByCartId(cart.getCartId());
		
				
		return new ResponseEntity<>(cartItemList,HttpStatus.OK);
	}
}
