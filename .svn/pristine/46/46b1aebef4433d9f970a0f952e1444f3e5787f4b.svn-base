package com.atguigu.crm.handler;

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
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.ProductService;
import com.atguigu.crm.utils.DataUtils;

@Controller
@RequestMapping("/product")
public class ProductHandler {
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			RedirectAttributes attributes,
			@RequestParam(value="pageNo", required=false, defaultValue="1") Integer pageNo){
		productService.delete(id);
		attributes.addFlashAttribute("message","操作成功!");
		
		return "redirect:/product/list?pageNo=" + pageNo;
	}
	
	@RequestMapping(value="/create")
	public String toSave(Map<String,Object> map){
		Product product=new Product();
		map.put("product",product);
		return "product/input";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String save(Product product,RedirectAttributes attributes){
		productService.save(product);
		attributes.addFlashAttribute("message", "操作成功!");
		return "redirect:/product/list";
	}
	
	@RequestMapping(value="/update/{product.id}",method=RequestMethod.PUT)
	public String update(@PathVariable(value="product.id")Long id,Product product,RedirectAttributes attributes){
		product.setId(id);
		productService.update(product);
		attributes.addFlashAttribute("message", "更新操作成功!");
		return "redirect:/product/list";
	}
	
	@RequestMapping(value="/create/{product.id}")
	public String toInput(@PathVariable(value="product.id")Integer id, Map<String, Object> map){
		map.put("product",productService.getProductById((long)id));
		return "product/input";
	}
	
	@RequestMapping("/list")
	public String list(@RequestParam(value="pageNo", required=false) String pageNoStr, 
			Map<String, Object> map, 
			HttpServletRequest request) {
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);
		map.put("queryString", queryString);
		int pageNo = 1;
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (NumberFormatException e) {}
		/*params.put("EQS_name");
		params.put("EQS_type");
		params.put("EQS_batch");*/
		Page<Product> page = productService.getPage(pageNo, params);
		map.put("page", page);
		return "product/list";
	}
}
