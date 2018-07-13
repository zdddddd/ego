package com.bjsxt.ego.rpc.service.impl;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.service.ItemService;

public class ItemServiceImplTest {
	
	private ClassPathXmlApplicationContext ctx;
	private ItemService itemService;
	
	@Before
	public void before() {
		ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		this.itemService = ctx.getBean(ItemService.class);
	}
	
	@After
	public void after() {
		ctx.close();
	}
	
	@Test
	public void testGetItemList() {
		
		EUDataGridResult result = this.itemService.getItemList(2, 5);
		
		System.out.println(result.getTotal());
		List<TbItem> items = (List<TbItem>) result.getRows();
		for (TbItem item : items) {
			System.out.println(item.getTitle());
		}
	}

}
