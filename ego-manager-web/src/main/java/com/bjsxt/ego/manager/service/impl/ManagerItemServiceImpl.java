package com.bjsxt.ego.manager.service.impl;

import java.io.IOException;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bjsxt.ego.common.pojo.EUDataGridResult;
import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.pojo.PictureResult;
import com.bjsxt.ego.common.util.FtpUtil;
import com.bjsxt.ego.common.util.IDUtils;
import com.bjsxt.ego.manager.service.ManagerItemService;
import com.bjsxt.ego.rpc.pojo.TbItem;
import com.bjsxt.ego.rpc.pojo.TbItemDesc;
import com.bjsxt.ego.rpc.pojo.TbItemParamItem;
import com.bjsxt.ego.rpc.service.ItemService;

@Service
public class ManagerItemServiceImpl implements ManagerItemService {
	
	//远程的服务代理对象
	@Autowired
	private ItemService itemServiceProxy;
	
	//SpEL表达式
	@Value("${FTP_HOST}")
	private String FTP_HOST;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_HTTP_PATH}")
	private String IMAGE_HTTP_PATH;

	@Override
	public TbItem getItemById(Long itemId) {
		return this.itemServiceProxy.getItem(itemId);
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		return this.itemServiceProxy.getItemList(page, rows);
	}

	@Override
	public PictureResult uploadItemImage(MultipartFile file) {
		boolean flag = false;
		String fileUrlName = null;
		try {
			
			String filePath = new DateTime().toString("/yyyy/MM/dd");
			
			//随机生成的图面名称，不包括扩展名
			String fileName = IDUtils.genImageName();
			String fileOldName = file.getOriginalFilename();
			String extName = fileOldName.substring(fileOldName.lastIndexOf("."));
			
			String newFileName = fileName + extName;
			
			flag = FtpUtil.uploadFile(FTP_HOST, FTP_PORT, FTP_USERNAME,
					FTP_PASSWORD, FTP_BASE_PATH, filePath,
					newFileName, file.getInputStream());
			fileUrlName = filePath + "/" + newFileName;
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
		
		if (flag) {
			//  http://192.168.10.15/images/2017/10/14/45865.png
			return new PictureResult(0, this.IMAGE_HTTP_PATH + fileUrlName);
		} else {
			return new PictureResult(1, "", "文件上传失败");
		}
	}

	@Override
	public EgoResult saveItem(TbItem item, String desc, String itemParams) {
		
		Date date = new Date();
		Long itemId = IDUtils.genItemId();
		//补全item数据
//		item.setBarcode(barcode);
//		item.setCid(cid);
		item.setId(itemId);
		item.setStatus((byte)1);
		item.setUpdated(date);
		item.setCreated(date);
//		item.setImage(image);
//		item.setNum(num);
//		item.setPrice(price);
//		item.setSellPoint(sellPoint);
//		item.setTitle(title);
		
		//补全商品描述信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(itemId);
		
		//商品的规格参数信息封装对象
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
//		itemParamItem.setId(id);
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		
		return this.itemServiceProxy.saveItem(item, itemDesc, itemParamItem);
	}

}
