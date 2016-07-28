package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.mapper.CustomerDrainMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class CustomerDrainService {
	
	@Autowired
	private CustomerDrainMapper customerDrainMapper;
	
	// 石英调度解决流失用户状态问题(6个月没有业务往来则更新为流失预警)
	@Transactional
	public void callCheckDrainProcedure(){
		customerDrainMapper.callCheckDrainProcedure();
		System.out.println("--------石英调度----------");
	}
	
	@Transactional(readOnly=true)
	public CustomerDrain getById(Long id) {
		return customerDrainMapper.getById(id);
	}

	@Transactional
	public Page<CustomerDrain> getPage(int pageNo, Map<String, Object> params) {
		Page<CustomerDrain> page = new Page<CustomerDrain>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> map = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = customerDrainMapper.getTotalElements(map);
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex + page.getPageSize();

		map.put("fromIndex", fromIndex);
		map.put("endIndex", endIndex);
		
		List<CustomerDrain> content = customerDrainMapper.getAllCustomerDrain(map);
		page.setContent(content);
		
		return page;
	}

	public void updateDelay(CustomerDrain customerDrain) {
		customerDrainMapper.updateDelay(customerDrain);
	}

	public void updateStatus(CustomerDrain customerDrain) {
		customerDrainMapper.updateStatus(customerDrain);
	}

}
