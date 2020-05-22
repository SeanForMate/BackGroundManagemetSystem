package com.znh.controller.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znh.model.Vendor;
import com.znh.service.base.VendorService;
import com.znh.util.JsonData;
/**
 * 供应商控制器
 * @author Sean
 *
 */
@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {
	
	@Autowired
	VendorService vendorService;

	/**
	 * 根据条件分页查询供应商数据
	 * @param page 页码
	 * @param limit 分页数
	 * @param vendor 供应商信息条件
	 * @return
	 */
	@GetMapping("/selectByPageIndexAndOther")
	public JsonData selectByPageIndexAndOther(Integer page,Integer limit,Vendor vendor) {
		return vendorService.selectVendorByPageIndexAndOther(page, limit, vendor);
	}
	
	@GetMapping("/selectAll")
	public JsonData selectAll() {
		return vendorService.selectAll();
	}
	
	/**
	 * 添加供应商信息
	 * @param vendor 供应商对象
	 * @return
	 */
	@PostMapping("/insertVendor")
	public JsonData insertVendor(Vendor vendor) {
		return vendorService.insertVendor(vendor);
	}
	
	/**
	 * 删除供应商信息
	 * @param vendorId 供应商ID
	 * @return
	 */
	@DeleteMapping("/deleteVendor")
	public JsonData deleteVendor(String[] vendorId) {
		return vendorService.deleteVendor(vendorId);
	}
	
	/**
	 * 修改供应商信息
	 * @param vendor 供应商对象
	 * @return
	 */
	@PutMapping("/updateVendor")
	public JsonData updateVendor(Vendor vendor) {
		return vendorService.updateVendor(vendor);
	}
	
	/**
	 * 根据ID查询供应商信息
	 * @param vendorId 供应商ID
	 * @return
	 */
	@GetMapping("/searchVendor")
	public JsonData searchVendor(String vendorId) {
		return vendorService.searchVendor(vendorId);
	}
	
}
