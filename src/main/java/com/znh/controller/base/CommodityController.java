package com.znh.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znh.model.Commodity;
import com.znh.service.base.CommodityService;
import com.znh.util.JsonData;

/**
 * 商品控制器
 * @author Sean
 *
 */
@RestController
@RequestMapping("/api/v1/commodity")
public class CommodityController {

	@Autowired
	CommodityService commodityService;
	
	/**
	 * 根据查询条件和页码和分页数获取商品信息
	 * @param commodity
	 * @param pageIndex
	 * @param limit
	 * @return
	 */
	@GetMapping("/selectByPageIndexAndOther")
	public JsonData selectByPageIndexAndOther(Commodity commodity,Integer page,Integer limit) {
		return commodityService.selectByPageIndexAndOther(commodity, page, limit);
	}
	
}
