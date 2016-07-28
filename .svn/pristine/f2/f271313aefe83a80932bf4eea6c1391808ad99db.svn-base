package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.Storage;

public interface StorageMapper {

	public long getTotalElements(Map<String, Object> myBatisParmas);

	public List<Storage> getContent(Map<String, Object> myBatisParmas);

	public Storage getById(@Param("id") Long id);

	public void updateStock(Storage storage);

	public void save(Storage storage);

	public void delete(@Param("id") Long id);
}
