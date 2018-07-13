package com.bjsxt.ego.manager.service.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.service.ItemService;

public class ConsumerItemServiceTest {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
		
		ctx.start();
		ItemService service = (ItemService) ctx.getBean("itemServiceImpl");
		TbItem item = service.getItem(968097L);
		System.out.println(item.getTitle());
		
		ctx.close();
	}
}
