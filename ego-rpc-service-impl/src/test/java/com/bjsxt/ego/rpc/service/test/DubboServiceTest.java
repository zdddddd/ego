package com.bjsxt.ego.rpc.service.test;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServiceTest {
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext-*.xml");
		ctx.start();
		System.in.read();
		ctx.close();
	}
}
