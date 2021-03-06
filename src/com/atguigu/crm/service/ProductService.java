package com.atguigu.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.crm.entity.Contact;
import com.atguigu.crm.entity.Product;
import com.atguigu.crm.mapper.ProductMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.orm.PropertyFilter;
import com.atguigu.crm.utils.DataUtils;

@Service
public class ProductService {
	
	@Autowired
	private ProductMapper productMapper;

	@Transactional(readOnly=true)
	public Page<Product> getPage(int pageNo, Map<String, Object> params) {
		Page<Product> page = new Page<Product>();
		page.setPageNo(pageNo);
		
		List<PropertyFilter> filters = DataUtils.parseHandlerParamsToPropertyFilters(params);
		Map<String, Object> mybatisParams = DataUtils.parsePropertyFiltersToMyBatisParmas(filters);

		long totalElements = productMapper.getTotalElements(mybatisParams);
		page.setTotalElements((int)totalElements);
		
		int fromIndex = (page.getPageNo() - 1) * page.getPageSize() + 1;
		int endIndex = page.getPageSize() + fromIndex;
		mybatisParams.put("fromIndex", fromIndex);
		mybatisParams.put("endIndex", endIndex);
		
		List<Product> content = productMapper.getContect(mybatisParams);
		page.setContent(content);
		
		return page;
	}

	@Transactional(readOnly=true)
	public Product getProductById(Long id) {
		
		Product product = productMapper.getProductById(id);
		System.out.println(product);
		return product;
	}

	@Transactional
	public void save(Product product) {
		productMapper.save(product);
		
	}

	@Transactional
	public void delete(Long id) {
		productMapper.delete(id);
		
	}

	@Transactional
	public void update(Product product) {
		productMapper.update(product);
	}

	public List<Product> getAllProduct() {
		return productMapper.getAllProduct();
	}
}
