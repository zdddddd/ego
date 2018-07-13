package com.bjsxt.ego.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjsxt.ego.portal.service.PortalContentService;
import com.bjsxt.ego.rpc.pojo.TbContent;
import com.bjsxt.ego.rpc.service.ContentService;

@Service
public class PortalContentServiceImpl implements PortalContentService {
	@Autowired
	private ContentService contentServiceProxy;
	
	@Override
	public String getContentListByCategoryId(long categoryId) {
		List<TbContent> contents = this.contentServiceProxy.getContentList(categoryId);
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < contents.size(); i++) {
			if (i == 0) {
				sb.append("{");
			} else {
				sb.append(",{");
			}
			sb.append("\"srcB\":").append("\"").append(contents.get(i).getPic2()).append("\",");
			sb.append("\"height\":240,");
			sb.append("\"alt\":\"\",");
			sb.append("\"width\": 670,");
			sb.append("\"src\":").append("\"").append(contents.get(i).getPic()).append("\",");
			sb.append("\"widthB\":550,");
			sb.append("\"href\":\"").append(contents.get(i).getUrl()).append("\",");
			sb.append("\"heightB\":240");
			sb.append("}");
		}
		sb.append("]");
		
		return sb.toString();
	}

}
