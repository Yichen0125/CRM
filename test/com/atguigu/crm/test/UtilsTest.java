package com.atguigu.crm.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import com.atguigu.crm.utils.DataUtils;

public class UtilsTest {

	@Test
	public void test() {
		String uuid = DataUtils.getUUID();
		System.out.println(uuid);
	}

	@Test
	public void test01 () {
		
	}
	
}
