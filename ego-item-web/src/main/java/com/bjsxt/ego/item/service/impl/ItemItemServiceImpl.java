package com.bjsxt.ego.item.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.common.pojo.EgoResult;
import com.bjsxt.ego.common.util.JsonUtils;
import com.bjsxt.ego.item.service.ItemItemService;
import com.bjsxt.ego.rpc.pojo.TbItemDesc;
import com.bjsxt.ego.rpc.pojo.TbItemParamItem;
import com.bjsxt.ego.rpc.service.ItemService;

@Service
public class ItemItemServiceImpl implements ItemItemService {

	@Autowired
	private ItemService itemServiceProxy;

	@Override
	public EgoResult getItemBaseInfo(Long itemId) {
		// 调用远程服务获取商品基本信息
		return this.itemServiceProxy.getItemBaseInfo(itemId);
	}

	@Override
	public String getItemDescById(Long itemId) {
		EgoResult result = this.itemServiceProxy.getItemDesc(itemId);
		TbItemDesc itemDesc = (TbItemDesc) result.getData();
		return itemDesc.getItemDesc();
	}

	@Override
	public String getItemParam(Long itemId) {
		EgoResult result = this.itemServiceProxy.getItemParam(itemId);
		// 获取商品的规格参数信息的json字符串
		//  TbItemParamItem对象的map集合
		//paramData就是数据库中存储的商品规格参数的json串
		String paramData = ((TbItemParamItem)result.getData()).getParamData();
		System.out.println("商品的规格参数信息：" + paramData);

		// 把规格参数json数据转换成java对象
		//每个Map元素就是一个商品规格参数的分组
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for (Map m1 : jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">"
					+ m1.get("group") + "</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for (Map m2 : list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">" + m2.get("k")
						+ "</td>\n");
				sb.append("            <td>" + m2.get("v") + "</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		// 返回html片段
		return sb.toString();
	}

}
