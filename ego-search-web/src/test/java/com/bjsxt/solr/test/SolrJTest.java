package com.bjsxt.solr.test;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	@Test
	public void addDocument() throws Exception {
		// 创建一连接
		// 使用url连接到solr的服务器
		SolrServer solrServer = new HttpSolrServer(
				"http://192.168.10.13:8080/solr");
		// 创建一个文档对象
		// solr中的每一条记录都是一个文档
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品2");
		document.addField("item_price", 54321);
		// 把文档对象写入索引库
		solrServer.add(document);
		// 提交
		solrServer.commit();
	}

	@Test
	public void deleteDocument() throws Exception {
		// 创建一连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.10.13:8080/solr");
		// 根据ID删除solr文档
		solrServer.deleteById("test001");
		// solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	
	
}
