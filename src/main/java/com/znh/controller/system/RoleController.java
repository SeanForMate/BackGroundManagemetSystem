package com.znh.controller.system;

import com.znh.model.Role;
import com.znh.service.system.RoleService;
import com.znh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
	
	@Autowired
	RoleService roleService;
	
	/**
	 * 获取所有用户角色信息并且分页
	 * @return
	 */
	@GetMapping("/selectByPageIndex")
	public JsonData selectByPageIndex(Integer page,Integer limit) {
		return roleService.selectByPageIndex(page,limit);
	}

	/**
	 * 获取角色
	 * @return
	 */
	@GetMapping("/selectRole")
	public JsonData selectRole(){
		return roleService.selectRole();
	}

	/**
	 * 添加角色
	 * @return
	 */
	@PostMapping("/insertRole")
	public JsonData insertRole(Role role){
		return roleService.insertRole(role);
	}

	/**
	 * 删除角色
	 * @param roleId
	 * @return
	 */
	@DeleteMapping("/deleteRole")
	public JsonData deleteRole(@RequestBody String roleId){
		return roleService.deleteRole(roleId);
	}

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@PutMapping("/updateRole")
	public JsonData updateRole(@RequestBody Role role){
		return roleService.updateRole(role);
	}

	/**
	 * 根据角色ID查询角色信息
	 * @param roleId
	 * @return
	 */
	@GetMapping("/searchRole")
	public JsonData searchRole(String roleId){
		return roleService.searchRole(roleId);
	}
}
