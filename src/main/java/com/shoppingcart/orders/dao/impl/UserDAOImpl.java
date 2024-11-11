package com.shoppingcart.orders.dao.impl;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.shoppingcart.orders.common.LoadJPAFQueries;
import com.shoppingcart.orders.dao.UserDAO;
import com.shoppingcart.orders.vo.User;

import jakarta.annotation.PostConstruct;

@Repository
public class UserDAOImpl extends JdbcDaoSupport implements UserDAO{

	@Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
	@Override
	public User findUserByUsername(String username) {
		 User user = getJdbcTemplate().queryForObject(LoadJPAFQueries.getQueryById("SELECT_USER_BY_USERNAME"), new Object[] { username },
	                BeanPropertyRowMapper.newInstance(User.class));
		return user;
	}

	@Override
	public boolean isUserEmailExist(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int insertUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
