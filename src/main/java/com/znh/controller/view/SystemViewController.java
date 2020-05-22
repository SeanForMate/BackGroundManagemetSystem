package com.znh.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 系统管理页面控制器
 * @author Sean
 *
 */
@Controller
public class SystemViewController {
											/*用户管理页面*/
	@GetMapping("/userInfo")
	public String userInfo() {
		return "user/userInfo";
	}
	
	@GetMapping("/addUserInfo")
	public String addUserInfo() {
		return "user/addUserInfo";
	}
	
	@GetMapping("/updateUserInfo")
	public String updateUserInfo() {
		return "user/updateUserInfo";
	}
	
	@GetMapping("/searchUserInfo")
	public String searchUserInfo() {
		return "user/searchUserInfo";
	}
								            /*角色管理页面*/
	@GetMapping("/roleInfo")
	public String roleInfo(){
		return "role/roleInfo";
	}

	@GetMapping("/addRoleInfo")
	public String addRoleInfo(){
		return "role/addRoleInfo";
	}

	@GetMapping("/updateRoleInfo")
	public String updateRoleInfo(){
		return "role/updateRoleInfo";
	}
									 		/*权限管理页面*/
	@GetMapping("/permissionsInfo")
	public String permissionsInfo() {
		return "permissions/permissionsInfo";
	}

	@GetMapping("/addPermissionsInfo")
	public String addPermissionsInfo() {
		return "permissions/addPermissionsInfo";
	}

	@GetMapping("/updatePermissionsInfo")
	public String updatePermissionsInfo() {
		return "permissions/updatePermissionsInfo";
	}
}
