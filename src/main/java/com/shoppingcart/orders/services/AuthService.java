package com.shoppingcart.orders.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.orders.vo.Customer;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthService {
	
private static final String TOKEN_HEADER = "Authorization";
	
	@Value("${service.auth.findCustomerIdByUsername}")
	private String findCustomerIdByUsernameURL;
	
	public Customer findCustomerFromToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(TOKEN_HEADER, token);
		
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity requestEntity = new HttpEntity(null, headers);
		ResponseEntity<Customer> responseEntity = restTemplate.exchange(findCustomerIdByUsernameURL, HttpMethod.POST, requestEntity,
				Customer.class);
		return responseEntity.getBody();
	}

}
