package com.atguigu.crm.handler;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ReportService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/report")
public class ReportHanler {

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/{methodName}")
	public String pay(HttpServletRequest request, @RequestParam(value = "pageNo", required = false) String pageNoStr,
			Map<String, Object> map,
			@PathVariable("methodName") String methodName) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");

		String queryString = DataUtils.encodeParamsToQueryString(params);

		int pageNo = 1;

		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {
		}

		Page<Map<String, Object>> page = reportService.getPage(pageNo, params, methodName);

		map.put("queryString", queryString);
		map.put("page", page);
		
		return "report/" + methodName;
	}

}
