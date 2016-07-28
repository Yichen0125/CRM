package com.atguigu.crm.batch;

import java.util.List;

public interface TestMapper {

	int batchInsert(List<Employee> datas);
	
}
