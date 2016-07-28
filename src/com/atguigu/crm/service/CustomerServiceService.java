package com.atguigu.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.mapper.CustomerServiceMapper;

@Service
public class CustomerServiceService {
	
	@Autowired
	private CustomerServiceMapper customerServiceMappr;

	@Transactional
	public void saveCustomerService(com.atguigu.crm.entity.CustomerService customerService) {
		customerServiceMappr.saveCustomerService(customerService);
	}

}
