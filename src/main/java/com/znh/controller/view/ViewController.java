package com.znh.controller.view;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
	
	/**
	 * 跳转到登录页
	 * 
	 * @return
	 */
	@GetMapping("/")
	public String loginPage() {
		// 获得当前Subject
		Subject currentUser = SecurityUtils.getSubject();
		// 验证用户是否验证，即是否登录
		if (currentUser.isAuthenticated()) {
			return "index";
		}
		return "login";
	}
	
	/**
	 * 跳转到首页
	 * 
	 * @return
	 */
	@GetMapping("/index")
	public String indexPage() {
		return "index";
	}
	
	/**
	 * 跳转到首页的主页
	 * 
	 * @return
	 */
	@GetMapping("/main")
	public String mainPage() {
		return "main";
	}
																														/*用户个人信息*/
	/**
	 * 个人信息页面
	 * @return
	 */
	@GetMapping("/personalData")
	public String personalDataPage() {
		return "user/personalData";
	}
	
	/**
	 * 修改个人密码
	 * @return
	 */
	@GetMapping("/updatePassword")
	public String updatePasswordPage() {
		return "user/updatePassword";
	}
																															/* 客户 */
	/**
	 * 跳转到客户信息页
	 * 
	 * @return
	 */
	@GetMapping("/customInfo")
	public String customerPage() {
		return "custom/customInfo";
	}
	
	/**
	 * 跳转到添加客户信息页面
	 * @return
	 */
	@GetMapping("/addCustomInfo")
	public String addCustomInfoPage() {
		return "custom/addCustomInfo";
	}
	
	/**
	 * 跳转到修改客户信息页面
	 * @return
	 */
	@GetMapping("/updateCustomInfo")
	public String updateCustomInfoPage() {
		return "custom/updateCustomInfo";
	}
	
	/**
	 * 跳转到查询客户信息页面
	 * @return
	 */
	@GetMapping("/searchCustomInfo")
	public String searchCustomInfoPage() {
		return "custom/searchCustomInfo";
	}
																													/* 供应商 */
	/**
	 * 跳转到供应商信息页面
	 * @return
	 */
	@GetMapping("/vendorInfo")
	public String vendorInfoPage() {
		return "vendor/vendorInfo";
	}
	
	/**
	 * 跳转到添加供应商信息页面
	 * @return
	 */
	@GetMapping("/addVendorInfo")
	public String addVendorInfoPage() {
		return "vendor/addVendorInfo";
	}
	
	/**
	 * 跳转到修改供应商信息页面
	 * @return
	 */
	@GetMapping("/updateVendorInfo")
	public String updateVendorInfoPage() {
		return "vendor/updateVendorInfo";
	}
	
	/**
	 * 跳转到查询供应商信息页面
	 * @return
	 */
	@GetMapping("/searchVendorInfo")
	public String searchVendorInfoPage() {
		return "vendor/searchVendorInfo";
	}
																														/* 商品 */
	@GetMapping("/commodityInfo")
	public String commodityInfoPage() {
		return "commodity/commodityInfo";
	}
	
}
