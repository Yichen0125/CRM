package com.atguigu.crm.handler;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.CustomerDrain;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerDrainService;
import com.atguigu.crm.service.CustomerService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/drain")
public class CustomerDrainHandler {
	
	@Autowired
	private CustomerDrainService customerDrainService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value="/confirm/{drainId}/{customerId}", method=RequestMethod.POST)
	public String confirm(@PathVariable("drainId") Long drainId,
			@PathVariable("customerId") Long customerId,
			@RequestParam("reason") String reason,
			RedirectAttributes attributes) {
		
		CustomerDrain customerDrain = customerDrainService.getById(drainId);
		
		customerDrain.setStatus("流失");
		customerDrain.setDrainDate(new Date());
		customerDrain.setReason(reason);
		customerDrainService.updateStatus(customerDrain);
		
		customerService.updateState("流失", customerId);
		attributes.addFlashAttribute("message", "成功流失客户");
		
		return "redirect:/drain/list";
	}
	
	@RequestMapping(value="/confirm/{drainId}/{customerId}", method=RequestMethod.GET)
	public String toConfirmUI(@PathVariable("drainId") Long drainId,
			Map<String, Object>	map) {
		CustomerDrain customerDrain = customerDrainService.getById(drainId);
		map.put("customerDrain", customerDrain);
		return "drain/confirm";
	}
	
	@RequestMapping(value="/delay/{drainId}", method=RequestMethod.POST) 
	@ResponseBody
	public String saveDelay(@PathVariable("drainId") Long drainId,
			@RequestParam("delay") String delay) {
		CustomerDrain customerDrain = customerDrainService.getById(drainId);
		if(customerDrain.getDelay() == null) {
			customerDrain.setDelay(delay);
		} else {
			customerDrain.setDelay(customerDrain.getDelay() + "`" + delay);;
		}
		customerDrainService.updateDelay(customerDrain);
		return "1";
	}
	
	@RequestMapping(value="/delay/{drainId}", method=RequestMethod.GET) 
	public String toDelayUI(@PathVariable("drainId") Long drainId,
			Map<String, Object>	map) {
		CustomerDrain customerDrain = customerDrainService.getById(drainId);
		map.put("customerDrain", customerDrain);
		return "drain/delay";
	}

	@RequestMapping("/list")
	public String list(Map<String, Object> map,
			@RequestParam(value="pageNo", required=false) String pageNoStr,
			HttpServletRequest request) {
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {}
		
		Page<CustomerDrain> page = customerDrainService.getPage(pageNo, params);
		
		map.put("page", page);
		
		return "drain/list";
	}
	
}
