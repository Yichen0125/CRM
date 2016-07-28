package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.CustomerService;
import com.atguigu.crm.service.CustomerServiceService;
import com.atguigu.crm.service.DictService;

@Controller
@RequestMapping("/service")
public class ServiceHandler {
	
	@Autowired
	private com.atguigu.crm.service.CustomerService customerService;
	
	@Autowired
	private DictService dirctService;
	
	@Autowired
	private CustomerServiceService customerServiceService;
	
	@RequestMapping("/allot/list")
	public String list() {
		
		return "service/allot/list";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public String create(CustomerService customerService) {
		customerServiceService.saveCustomerService(customerService);
		return "service/input";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String toCreateUI(Map<String,Object> map) {
		List<Customer> customers = customerService.getAllCustomer();
		List<String> dicts = dirctService.getDict("服务类型");
		map.put("customers", customers);
		map.put("dicts", dicts);
		return "service/input";
	}

}
