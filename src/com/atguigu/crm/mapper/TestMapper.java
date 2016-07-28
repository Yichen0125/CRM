package com.atguigu.crm.mapper;

import java.util.List;

import com.atguigu.crm.batch.Employee;

public interface TestMapper {

	int batchInsert(List<Employee> datas);
	
}
