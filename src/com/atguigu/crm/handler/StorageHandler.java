package com.atguigu.crm.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Product;
import com.atguigu.crm.entity.Storage;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ProductService;
import com.atguigu.crm.service.StorageService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/storage")
public class StorageHandler {
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/delete/{storageId}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("storageId") Long storageId,
			RedirectAttributes attributes) {
		storageService.delete(storageId);
		attributes.addFlashAttribute("message", "删除成功");
		return "redirect:/storage/list";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	public String create(Map<String, Object> map, Storage storage,
			RedirectAttributes rAttributes) {
		storageService.save(storage);
		rAttributes.addFlashAttribute("message", "添加库存成功");
		return "redirect:/storage/list";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String toCreateUI(Map<String, Object> map) {
		List<Product> products = productService.getAllProduct();
		map.put("products", products);
		map.put("storage", new Storage());
		return "storage/input";
	}
	
	@RequestMapping(value="/create", method=RequestMethod.PUT)
	public String updateStorage(Map<String, Object> map, 
			Storage storage,
			@RequestParam("incrementCount") int incrementCount) {
		storage.setStockCount((storage.getStockCount() + incrementCount));
		storageService.updateStock(storage);
		return "redirect:/storage/list";
	}
	
	@RequestMapping(value="/create/{storageId}", method=RequestMethod.GET)
	public String toUpdateStorageUI(@PathVariable("storageId") Long storageId,
			Map<String, Object> map) {
		Storage storage = storageService.getById(storageId);
		map.put("storage", storage);
		return "storage/input";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo", required=false) String pageNoStr,
			HttpServletRequest request, Map<String, Object> map) {
		
		int pageNo = 1;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
				
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		
		Page<Storage> page = storageService.getPage(pageNo, params);
		
		map.put("queryString", queryString);
		map.put("page", page);
		
		return "storage/list";
	}

}
