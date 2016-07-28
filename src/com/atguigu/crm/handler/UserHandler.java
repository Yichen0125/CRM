package com.atguigu.crm.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.WebUtils;

import com.atguigu.crm.entity.Authority;
import com.atguigu.crm.entity.Role;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.orm.Navigation;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.RoleService;
import com.atguigu.crm.service.UserService;
import com.atguigu.crm.utils.DataUtils;

@RequestMapping("/user")
@Controller
public class UserHandler {

	private static Map<Integer, String> allStatus = new HashMap<>();
	
	static{
		allStatus.put(1, "有效");
		allStatus.put(0, "无效");
	}
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@ResponseBody
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public String deleteUser(@PathVariable("id") Integer id){
		
		userService.deleteUser(id);
		
		return "1";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.PUT)
	public String updateUser(User user){
		
		userService.updateUser(user);
		
		return "redirect:/user/list";
	}

	@RequestMapping("/toEditUI/{id}")
	public String toEditUI(@PathVariable("id") Integer id, Map<String, Object> map) {
		
		List<Role> roles = roleService.getAllList();
		map.put("roles", roles);
		
		User user = userService.getUserById(id);
		map.put("user", user);
		map.put("allStatus", allStatus);
		
		return "user/input";
	}

	@RequestMapping(value = "/save", method=RequestMethod.POST)
	public String saveUser(User user) {

		userService.saveUser(user);

		return "redirect:/user/list";
	}

	@RequestMapping("/toAddUI")
	public String toAddUI(Map<String, Object> map) {

		List<Role> roles = roleService.getAllList();
		map.put("roles", roles);

		User user = new User();
		map.put("user", user);

		map.put("allStatus", allStatus);
		return "user/input";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String showList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			HttpServletRequest request) {

		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);

		request.setAttribute("queryString", queryString);

		Page<User> page = userService.getPage(pageNo, params);

		request.setAttribute("page", page);

		return "user/list";
	}
	
	/**
	 * 生成权限菜单
	 */
	@ResponseBody
	@RequestMapping("/navigation")
	public List<Navigation> navigation(HttpSession session) {
		// 需要返回的右侧菜单集合
		List<Navigation> navigations = new ArrayList<>();
		
		// 获取项目的名称,用于设置Url地址值
		String contextPath = session.getServletContext().getContextPath();
		
		// 获取当前用户的权限集合
		User user = (User) session.getAttribute("user");
		List<Authority> authorities = user.getRole().getAuthorities();
		
		// 设置最顶菜单
		Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
		navigations.add(top);
		
		// 用于封装父权限
		Map<Long, Navigation> parentAuthorites = new HashMap<>();
		
		
		// 遍历获取权限Id和权限名称(用于显示)
		for (Authority authority : authorities) {
			// 将一个权限封装成一个对象
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			// 获取父权限
			Authority parentAuthority = authority.getParentAuthority();
			
			// 从Map中获取对应id的父权限
			Navigation parentNavigation = parentAuthorites.get(parentAuthority.getId());
			
			// 如果父权限等于null,则证明本身为父权限
			if(parentNavigation == null) {
				// 将父权限封装为Navigation
				parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
				// 默认将父目录设置成关闭状态
				parentNavigation.setState("closed");
				// 将该父权限放入Map中
				parentAuthorites.put(parentAuthority.getId(), parentNavigation);
				// 将父权限放入项目根菜单下
				top.getChildren().add(parentNavigation);
			}
			// 设置Url地址
			navigation.setUrl(contextPath + authority.getUrl());
			
			parentNavigation.getChildren().add(navigation);
			
		}
		
		System.out.println(navigations);
		
		return navigations;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public List<Navigation> navigation(HttpSession session) {
		List<Navigation> navigations = new ArrayList<Navigation>();
		// 登录的用户
		User user = (User) session.getAttribute("user");
		// 当前用户拥有的权限
		List<Authority> authorities = user.getRole().getAuthorities();
		
		String contextPath = session.getServletContext().getContextPath();
		
		Navigation top = new Navigation(Long.MAX_VALUE, "客户关系管理系统");
		navigations.add(top);
		
		Map<Long, Navigation> parentNavigations = new HashMap<Long, Navigation>();
		
		for (Authority authority : authorities) {
			Navigation navigation = new Navigation(authority.getId(), authority.getDisplayName());
			authority.setUrl(contextPath + authority.getUrl());
			
			Authority parentAuthority = authority.getParentAuthority();
			Navigation parentNavigation = parentNavigations.get(parentAuthority.getId());
			if(parentNavigation == null) {
				parentNavigation = new Navigation(parentAuthority.getId(), parentAuthority.getDisplayName());
				parentNavigation.setState("closed");
				
				parentNavigations.put(parentAuthority.getId(), parentNavigation);
				top.getChildren().add(parentNavigation);
			}
			
			parentNavigation.getChildren().add(navigation);
			
		}
		
		return navigations;
	}*/

	@RequestMapping(value = "/shiro-login", method = RequestMethod.POST)
	public String loginForShiro(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password, HttpSession session,
			RedirectAttributes attributes, Locale locale) {
		
		Subject currentUser = SecurityUtils.getSubject();
		
		if(!currentUser.isAuthenticated()) {
			
			UsernamePasswordToken token = new UsernamePasswordToken(name, password);
			token.setRememberMe(true);
			
			try {
				currentUser.login(token);
			} catch (AuthenticationException ae) {
				String code = "error.user.login";
				String message = messageSource.getMessage(code, null, locale);
				attributes.addFlashAttribute("message", message);
				return "redirect:/index";
			}
			
		}
		
		session.setAttribute("user", currentUser.getPrincipals().getPrimaryPrincipal());
		return "home/success";
	}
	
	/**
	 * 1. 使用 SpringMVC 提供的 RedirectAttributes API 可以把 key-value 对重定向的情况下,
	 * 在页面上给予显示. 1). 在目标方法的参数中添加 RedirectAttributes 参数. 2). 具体调用
	 * RedirectAttributes 的 addFlashAttribute(key, val) 来添加键值对. 3). 重定向到目标资源.
	 * 但不能直接重定向到其物理页面. 而需要经过 SpringMVC 处理一下.
	 * <mvc:view-controller path="/index" view-name="index"/> 4). 页面上使用
	 * javascript 和 JSTL 标签结合来显示错误消息.
	 * 
	 * 2. 错误消息如何放在国际化资源文件中. 1). 在 SpringMVC 中配置国际化资源文件 配置
	 * org.springframework.context.support.ResourceBundleMessageSource bean. 且
	 * id 必须为 messageSource 2). 在类路径下新建国际化资源文件, 加入 key-value 对. 3). 在 Handler
	 * 中自动装配 ResourceBundleMessageSource 属性 4). 调用 getMessage(String code,
	 * Object[] args, Locale locale) 方法来获取国际化资源文件中的 value 值. 5). Locale
	 * 可以直接在目标方法中传入.
	 */
	@Deprecated
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "password", required = false) String password, HttpSession session,
			RedirectAttributes attributes, Locale locale) {
		User user = userService.login(name, password);
		if (user == null) {
			String code = "error.user.login";
			String message = messageSource.getMessage(code, null, locale);
			attributes.addFlashAttribute("message", message);
			return "redirect:/index";
		}

		session.setAttribute("user", user);
		return "home/success";
	}

}
