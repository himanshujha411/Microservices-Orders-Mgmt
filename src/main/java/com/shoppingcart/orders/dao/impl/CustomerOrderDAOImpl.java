package com.shoppingcart.orders.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.orders.common.LoadJPAFQueries;
import com.shoppingcart.orders.dao.CustomerOrderDAO;
import com.shoppingcart.orders.dao.SequencesDAO;
import com.shoppingcart.orders.vo.Customer;

import jakarta.annotation.PostConstruct;

@Repository
public class CustomerOrderDAOImpl extends JdbcDaoSupport implements CustomerOrderDAO{
	
	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Autowired
    SequencesDAO sequencesDAO;
    
    private static final String customerorderSequence = "customerorder_seq";
    
    @Override
    public int insertCustomerOrder(Customer customer, int cartId)
    {
    	int customerOrderId = sequencesDAO.getNextSequence(customerorderSequence);
    	
    	int status = getJdbcTemplate().update(LoadJPAFQueries.getQueryById("Insert_CustomerOrder"),
                new Object[] { customerOrderId, customer.getBillingAddressId(), 
                		customer.getCustomerId(), customer.getShippingAddressId(), cartId });
    	if(status==1) {
    		return customerOrderId;
    	}
    	else {
    		return 0;
    	}
    }

}
