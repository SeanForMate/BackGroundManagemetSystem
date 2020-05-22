package com.znh.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znh.model.Custom;
import com.znh.service.base.CustomService;
import com.znh.util.JsonData;
/**
 * 客户控制器
 * @author Sean
 *
 */
@RestController
@RequestMapping("/api/v1/custom")
public class CustomController {
	
	@Autowired
	CustomService customService;
	
	/**
	 * 根据查询条件和页码和分页数获取客户信息
	 * @param custom
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/selectByPageIndexAndOther")
	public JsonData selectByPageIndexAndOther(Custom custom,Integer page,Integer limit) {
		return customService.selectByPageIndexAndOther(custom, page, limit);
	}
	
	/**
	 * 获取客户分类数据
	 * @return
	 */
	@GetMapping("/getCustomType")
	public JsonData getCustomType() {
		return customService.getCustomType();
	}
	
	/**
	 * 获取客户性质数据
	 * @return
	 */
	@GetMapping("/getCustomCharacter")
	public JsonData getCustomCharacter() {
		return customService.getCustomCharacter();
	}
	
	/**
	 * 获取客户分区数据
	 * @return
	 */
	@GetMapping("/getCustomZone")
	public JsonData getCustomZone() {
		return customService.getCustomZone();
	}
	
	/**
	 * 添加客户信息数据
	 * @param custom
	 * @return
	 */
	@PostMapping("/insertCustom")
	public JsonData insertCustom(Custom custom) { 
		return customService.insertCustom(custom);
	}
	
	/**
	 * 批量删除删除客户信息
	 * @param customId
	 * @return
	 */
	@DeleteMapping("/deleteCustom")
	public JsonData deleteCustom(String[] customId) {
		return customService.deleteCustom(customId);
	}
	
	/**
	 * 查询客户信息
	 * @param customId
	 * @return
	 */
	@GetMapping("/searchCustom")
	public JsonData searchCustom(String customId) {
		return customService.searchCustom(customId);
	}
	
	/**
	 * 修改客户信息数据
	 * @param custom
	 * @return
	 */
	@PutMapping("/updateCustom")
	public JsonData updateCustom(Custom custom) {
		return customService.updateCustom(custom);
	}
	
}
