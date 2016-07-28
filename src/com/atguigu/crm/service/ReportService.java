package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.mapper.ReportMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class ReportService {

	@Autowired
	private ReportMapper reportMapper;
	
	// pay
	@Transactional(readOnly = true)
	public Page<Map<String, Object>> getPage(int pageNo, Map<String, Object> params, String methodName) {
		Page<Map<String, Object>> page = new Page<>();
		page.setPageNo(pageNo);
		
		if(!params.containsKey("EQS_type") && "consist".equals(methodName)) {
			params.put("EQS_type", "level");
		}

		// 1. 获取总的记录数
		// 1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		// 1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		// 2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		long totalElements = 0 ;
		List<Map<String, Object>> content = null;
		
		if("pay".equals(methodName)) {
			totalElements = reportMapper.getTotalElementsByPay(mybatisParams);
			content = reportMapper.getContentByPay(mybatisParams);
		} else if("consist".equals(methodName)) {
			totalElements = reportMapper.getTotalElementsByConsist(mybatisParams);
			content = reportMapper.getContentByConsist(mybatisParams);
		} else if("service".equals(methodName)) {
			totalElements = reportMapper.getTotalElementsByService(mybatisParams);
			content = reportMapper.getContentByService(mybatisParams);
		} else if("drain".equals(methodName)) {
			totalElements = reportMapper.getTotalElementsByDrain(mybatisParams);
			content = reportMapper.getContentByDrain(mybatisParams);
		}
		
		page.setTotalElements((int) totalElements);
		page.setContent(content);

		// 3. 组装 Page 并返回
		return page;
	}

}
