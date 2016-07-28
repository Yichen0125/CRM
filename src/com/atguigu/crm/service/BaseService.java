package com.atguigu.crm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.mapper.BaseMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.orm.PropertyFilter.MatchType;
import com.atguigu.crm.utils.DataUtils;

public class BaseService<T> {

	@Autowired
	private BaseMapper<T> mapper;
	
	@Transactional(readOnly=true)
	public Page<T> getPage(int pageNo, Map<String, Object> params){
		Page<T> page = new Page<>();
		page.setPageNo(pageNo);
		
		//1. 获取总的记录数
		//1.1 把传入的 params 转为 PropertyFilter 的集合
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		//1.2 把 RropertyFilter 的集合转为 mybatis 可用的 params
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = mapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//2. 获取当前页面的 List
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<T> content = mapper.getContent(mybatisParams);
		page.setContent(content);
		
		//3. 组装 Page 并返回
		return page;
	}
	
}
