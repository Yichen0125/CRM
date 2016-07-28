package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.mapper.DictMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class DictService {
	
	@Autowired
	private DictMapper dictMapper;

	@Transactional(readOnly = true)
	public List<String> getDict(String type) {
		return dictMapper.getDict(type);
	}

	@Transactional(readOnly = true)
	public Page<Dict> getPage(int pageNo, Map<String, Object> params) {
		Page<Dict> page = new Page<>();
		page.setPageNo(pageNo);
		
		//获取总记录数
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = dictMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		//获取当前页面的list
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);

		List<Dict> content = dictMapper.getContent(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional
	public void save(Dict dict) {
		dictMapper.save(dict);
	}

	@Transactional(readOnly = true)
	public Dict getById(Integer id) {
		return dictMapper.getById(id);
	}

	@Transactional
	public void update(Dict dict) {
		dictMapper.update(dict);
	}
	
	@Transactional
	public void delete(Integer id) {
		dictMapper.delete(id);
	}
	
}
