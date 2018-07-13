package com.bjsxt.ego.manager.service;

import org.springframework.web.multipart.MultipartFile;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.pojo.PictureResult;
import com.bjsxt.ego.rpc.pojo.TbItem;

public interface ManagerItemService {
	/**
	 * 根据商品id查询商品信息
	 * @param itemId
	 * @return
	 */
	TbItem getItemById(Long itemId);
	
	/**
	 * 分页查询商品信息，调用远程服务来完成操作
	 * @param page
	 * @param rows
	 * @return
	 */
	EUDataGridResult getItemList(int page, int rows);
	
	/**
	 * 通过FTP服务将商品图片上传到图片服务器
	 * @param file
	 * @return
	 */
	PictureResult uploadItemImage(MultipartFile file);
	/**
	 * 调用远程dubbo服务保存商品信息的方法
	 * @param item 封装了商品的基本信息
	 * @param desc 商品的描述信息
	 * @param itemParams 
	 * @return
	 */
	EgoResult saveItem(TbItem item, String desc, String itemParams);
}
