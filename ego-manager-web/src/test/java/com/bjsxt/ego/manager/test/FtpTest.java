package com.bjsxt.ego.manager.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class FtpTest {
	
	@Test
	public void testFtp() throws SocketException, IOException {
		//创建客户端对象
		FTPClient client = new FTPClient();
		//使用客户端连接服务器
		client.connect("192.168.10.15", 21);
		//使用客户端登陆服务器
		client.login("ftpuser", "ftp123");
		
		//获取本地文件的输入流
		InputStream is = new FileInputStream("E:\\尚学堂桌面.jpg");
		
		//指定将该文件上传到服务器的什么目录  cd /home/ftpuser/www/images/
		//由于该命令是cd命令，所以需要该文件夹存在，所以提前创建好
		client.changeWorkingDirectory("/home/ftpuser/www/images");
		//指定要上传的文件类型
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		//上传文件
		//第一个参数：远程文件的名称，第二个参数：本地文件输入流
		client.storeFile("sxt.jpg", is);
		//退出登录
		client.logout();
	}
	
}
