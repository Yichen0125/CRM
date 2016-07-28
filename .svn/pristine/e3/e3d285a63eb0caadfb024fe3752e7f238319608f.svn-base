package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.atguigu.crm.entity.CustomerDrain;

public interface CustomerDrainMapper {

	List<CustomerDrain> getAllCustomerDrain(Map<String, Object> map);

	long getTotalElements(Map<String, Object> map);
	
	CustomerDrain getById(@Param("id") Long id);

	void updateDelay(CustomerDrain customerDrain);

	void updateStatus(CustomerDrain customerDrain);

	@Update("{call check_drain()}")
	void callCheckDrainProcedure();

}
