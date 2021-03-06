package com.atguigu.crm.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.crm.batch.Employee;
import com.atguigu.crm.entity.Customer;
import com.atguigu.crm.entity.SalesChance;
import com.atguigu.crm.entity.User;
import com.atguigu.crm.mapper.ContactMapper;
import com.atguigu.crm.mapper.CustomerDrainMapper;
import com.atguigu.crm.mapper.CustomerMapper;
import com.atguigu.crm.mapper.ReportMapper;
import com.atguigu.crm.mapper.RoleMapper;
import com.atguigu.crm.mapper.SalesChanceMapper;
import com.atguigu.crm.mapper.TestMapper;
import com.atguigu.crm.mapper.UserMapper;
import com.atguigu.crm.orm.Page;
import com.atguigu.crm.service.CustomerDrainService;
import com.atguigu.crm.service.ReportService;
import com.atguigu.crm.service.SalesChanceService;
import com.atguigu.crm.utils.DataUtils;

public class ApplicationContextTest {

	private ApplicationContext ctx = null;
	private UserMapper userMapper = null;
	private SalesChanceMapper salesChanceMapper = null;
	private ContactMapper contactMapper = null;
	private CustomerMapper customerMapper = null;
	private SalesChanceService salesChanceService = null;
	private CustomerDrainService customerDrainService = null;
	private CustomerDrainMapper customerDrainMapper = null;
	private RoleMapper roleMapper = null;
	private TestMapper testMapper = null;
	private ReportService reportService = null;
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		userMapper = ctx.getBean(UserMapper.class);
		salesChanceMapper = ctx.getBean(SalesChanceMapper.class);
		contactMapper = ctx.getBean(ContactMapper.class);
		customerMapper = ctx.getBean(CustomerMapper.class);
		salesChanceService = ctx.getBean(SalesChanceService.class);
		customerDrainService = ctx.getBean(CustomerDrainService.class);
		customerDrainMapper = ctx.getBean(CustomerDrainMapper.class);
		roleMapper = ctx.getBean(RoleMapper.class);
		testMapper = ctx.getBean(TestMapper.class);
		reportService = ctx.getBean(ReportService.class);
	}
	
private DateFormat dateFormat = null;
	
	{
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	

	@Test
	public void test222() throws ParseException {
		
		Map<String, Object> params = new HashMap<>();
		params.put("LIKES_custName", "");
		Date minOrderDate = dateFormat.parse("1900-1-1");
		params.put("GTD_minOrderDate", minOrderDate);
		params.put("LTD_maxOrderDate", new Date());
		
	}
	
	@Test
	public void test112() {
		List<Employee> employees = new ArrayList<>();

		Employee employee = null;

		for (int i = 1000; i < 1100; i++) {

			employee = new Employee(i, "emp" + i, i + "@qqq.com");
			employees.add(employee);
		}
		testMapper.batchInsert(employees);
	}

	@Test
	public void test11() {
		salesChanceService.updateFinishStatus(new SalesChance());
	}

	@Test
	public void testSelectKey() {
		Customer customer = new Customer();
		customer.setNo(DataUtils.getUUID());
		customer.setName("hahahaha");
		customer.setState("正常");

		int i = customerMapper.insertCustomerForFinisChance(customer);
		System.out.println(i);

		/*
		 * Contact contact = new Contact(); contact.setName("11111");
		 * contact.setTel("123123123123"); int i =
		 * contactMapper.insertContactForFinshChance(contact);
		 * System.out.println(i);
		 */
	}

	@Test
	public void testSalesChanceMapperGetTotalElements2() {
		Map<String, Object> params = new HashMap<String, Object>();
		User createBy = new User();
		createBy.setId(24L);
		params.put("createBy", createBy);

		int status = 1;
		params.put("status", status);

		params.put("custName", "%��%");

		long result = salesChanceMapper.getTotalElements2(params);
		System.out.println(result);
	}

	@Test
	public void testSalesChanceMapperGetTotalElements() {
		User createBy = new User();
		createBy.setId(24L);
		int status = 1;

		long result = salesChanceMapper.getTotalElements(createBy, status);
		System.out.println(result);
	}

	@Test
	public void testUserMapper() {
		User user = userMapper.getByName("admin");
		System.out.println(user.getPassword());
		System.out.println(user.getRole().getName());
	}

	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		Connection connection = dataSource.getConnection();

		System.out.println(connection);
	}

}
