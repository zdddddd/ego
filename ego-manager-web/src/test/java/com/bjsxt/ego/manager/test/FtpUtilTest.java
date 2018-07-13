package com.bjsxt.ego.manager.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.joda.time.DateTime;
import org.junit.Test;

import com.bjsxt.ego.common.util.FtpUtil;

public class FtpUtilTest {
	
	@Test
	public void testFtpUtil() throws FileNotFoundException {
		
		InputStream input = new FileInputStream("C:\\Users\\Public\\Pictures\\Sample Pictures\\Koala.jpg");
		
		//joda time时间操作组件
//		DateTime time = new DateTime();
//		System.out.println(time.toString("/yyyy/MM/dd"));
		
//		SimpleDateFormat 
		
		boolean flag = FtpUtil.uploadFile(
				"192.168.10.15", 21, "ftpuser", "ftp123",
				"/home/ftpuser/www/images",
				new DateTime().toString("/yyyy/MM/dd"), "1111.jpg", input);
		System.out.println(flag ? "上传成功" : "失败");
	}
	
}
