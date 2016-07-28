package com.atguigu.crm.handler;

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

import com.atguigu.crm.entity.Dict;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.DictService;
import com.atguigu.crm.utils.DataUtils;

@RequestMapping("/dict")
@Controller
public class DictHandler {
	
	@Autowired
	private DictService dictService;
	
	@RequestMapping(value="/{dictId}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("dictId") Integer id, RedirectAttributes attributes, 
			@RequestParam(value="pageNo", required=false, defaultValue="1") Integer pageNo) {
		dictService.delete(id);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/dict/list?pageNo=" + pageNo;
	}
	
	@RequestMapping(value="/create", method=RequestMethod.PUT)
	public String update(Dict dict, RedirectAttributes attributes) {
		dictService.update(dict);
		attributes.addFlashAttribute("message", "修改成功");
		return "redirect:/dict/list";
	}
	
	@RequestMapping(value="/create/{id}")
	public String toUpdate(Map<String, Object> map, @PathVariable("id") Integer id) {
		Dict dict = dictService.getById(id);
		dict.setId((long)id);
		map.put("dict", dict);
		return "dict/input";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String save(Dict dict, RedirectAttributes attributes) {
		dictService.save(dict);
		attributes.addFlashAttribute("message", "添加成功");
		return "redirect:/dict/list";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String toAddUI(Map<String, Object> map) {
		Dict dict = new Dict();
		map.put("dict", dict);
		return "dict/input";
	}
	
	@RequestMapping(value="/list")
	public String getPage(@RequestParam(value="pageNo", required=false) String pageNoStr,
			Map<String, Object> map,
			HttpServletRequest request){
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		
		//获取页码
		int pageNo=1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		
		Page<Dict> page = dictService.getPage(pageNo, params);
		map.put("page", page);
		return "dict/list";
	}

}
