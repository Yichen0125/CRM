package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	
	public long getTotalElements(Map<String, Object> map);
	
	public List<T> getContent(Map<String, Object> mybatisParams);

}
