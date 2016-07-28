package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.mapper.StorageMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class StorageService {
	
	@Autowired
	private StorageMapper storageMapper;

	@Transactional
	public Page<Storage> getPage(int pageNo, Map<String, Object> map) {
		Page<Storage> page = new Page<Storage>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(map);
		Map<String, Object> myBatisParmas = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);
		
		long totalElements = storageMapper.getTotalElements(myBatisParmas);
		
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (pageNo - 1) * page.getPageSize() + 1;
		int endIndex = fromIndex  + page.getPageSize();
		
		myBatisParmas.put("fromIndex", fromIndex);
		myBatisParmas.put("endIndex", endIndex);
		
		List<Storage> content = storageMapper.getContent(myBatisParmas);
		
		page.setContent(content);
		
		return page;
	}

	@Transactional(readOnly=true)
	public Storage getById(Long storageId) {
		return storageMapper.getById(storageId);
	}

	@Transactional
	public void updateStock(Storage storage) {
		storageMapper.updateStock(storage);
	}

	public void save(Storage storage) {
		storageMapper.save(storage);
	}

	public void delete(Long storageId) {
		storageMapper.delete(storageId);
	}
	
}
