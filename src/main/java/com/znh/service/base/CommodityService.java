package com.znh.service.base;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.CommodityMapper;
import com.znh.model.Commodity;
import com.znh.util.JsonData;

@Service
public class CommodityService {
	
	private static final Logger log = LoggerFactory.getLogger(CommodityService.class);

	@Autowired
	CommodityMapper commodityMapper;
	
	public JsonData selectByPageIndexAndOther(Commodity commodity,Integer pageIndex,Integer limit) {
		JsonData jsonData = null;
		if(pageIndex<=0) {
			return  jsonData = new JsonData("1","失败","页码不能小于0！");
		}
		if(limit<=0) {
			return  jsonData = new JsonData("1","失败","分页数不能小于0！");
		}
		try {
			PageHelper.startPage(pageIndex, limit);
			List<Commodity> commodityList = commodityMapper.selectByPageIndexAndOther(commodity);
			PageInfo<Commodity> result = new PageInfo<>(commodityList);
			if(result.getList().size()>0) {
				jsonData = new JsonData(0,"成功",result.getList(),result.getTotal());
			}else {
				jsonData = new JsonData(1,"没有数据","",0);
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
}
