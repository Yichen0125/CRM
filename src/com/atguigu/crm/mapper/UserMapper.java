package com.atguigu.crm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.crm.entity.User;

public interface UserMapper {

	User getByName(@Param("name") String name);

	long getTotalElements(Map<String, Object> myBatisParmas);

	List<User> getContent(Map<String, Object> myBatisParmas);

	void saveUser(User user);

	User getUserById(@Param("id")Integer id);

	void updateUser(User user);

	void deleteUser(@Param("id")Integer id);
	
}
