package com.shoppingcart.orders.util;

public class ServiceConstants {
	
	public static final String SERVICE_URL_PREFIX = "/controller";
	public static final String REQ_MAP_URL_UserLoginBeanService = SERVICE_URL_PREFIX +"/UserLoginBeanService";
	public static final String REQ_MAP_URL_UserRegistrationService = SERVICE_URL_PREFIX +"/UserRegistration";
	public static final String REQ_MAP_URL_ProductService = SERVICE_URL_PREFIX +"/ProductService";
	public static final String REQ_MAP_URL_CartService = SERVICE_URL_PREFIX +"/cartService";
	public static final String REQ_MAP_URL_CartItemService = SERVICE_URL_PREFIX +"/cartItemService";
	public static final String REQ_MAP_METHOD_URL_CustomerOrderServices = SERVICE_URL_PREFIX+"/customerOrderServices";
	
	public static final String REQ_MAP_METHOD_URL_InsertCart = "/insertcart";
	public static final String REQ_MAP_METHOD_URL_InsertCartItem = "/insertcartitem";
	
	public static final String REQ_MAP_METHOD_URL_SubmitOrder = "/submitOrder";
	
	public static final String REQ_MAP_METHOD_URL_GetAllProducts = "/getAllProducts";
	public static final String REQ_MAP_METHOD_URL_GetProductById = "/getProductById";

	public static final String REQ_MAP_URL_addToCart = "/addToCart";
	public static final String REQ_MAP_URL_deleteFromCart = "/deleteFromCart";
	public static final String REQ_MAP_URL_deleteItemFromCart = "/deleteItemFromCart";
	public static final String REQ_MAP_URL_getCartById = "/getCartById";
	
	public static final String REQ_MAP_URL_getCartFromToken = "/getCartFromToken";
}
