package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;


public interface ReportMapper{

	long getTotalElementsByPay(Map<String, Object> mybatisParams);

	List<Map<String, Object>> getContentByPay(Map<String, Object> mybatisParams);

	long getTotalElementsByConsist(Map<String, Object> mybatisParams);

	List<Map<String, Object>> getContentByConsist(Map<String, Object> mybatisParams);

	long getTotalElementsByService(Map<String, Object> mybatisParams);

	List<Map<String, Object>> getContentByService(Map<String, Object> mybatisParams);

	long getTotalElementsByDrain(Map<String, Object> mybatisParams);

	List<Map<String, Object>> getContentByDrain(Map<String, Object> mybatisParams);

}
