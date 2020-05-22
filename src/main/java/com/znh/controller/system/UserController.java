package com.znh.controller.system;

import com.znh.model.User;
import com.znh.service.system.UserService;
import com.znh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * 用户控制器
 * @author Sean
 *
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 根据条件并分页查询用户信息
	 * @param user
	 * @param page
	 * @param limit
	 * @return
	 */
	@GetMapping("/selectByPageIndexAndOther")
	public JsonData selectByPageIndexAndOther(User user,@RequestParam(name="roleId",required = false) String roleId,Integer page,Integer limit){
		return userService.selectByPageIndexAndOther(user,roleId, page, limit);
	}
	
	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	@PostMapping("/insertUser")
	public JsonData insertUser(User user) {
		return userService.insertUser(user);
	}
	
	/**
	 * 根据userId删除用户信息
	 * @param userId
	 * @return
	 */
	@PostMapping("/deleteUser")
	public JsonData deleteUser(String[] userId) {
		return userService.deleteUser(userId);
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	@PutMapping("/updateUser")
	public JsonData updateUser(@RequestBody User user) {
		return userService.updateUser(user);
	}
	
	/**
	 * 根据用户ID查询具体用户信息
	 * @param userId
	 * @return
	 */
	@GetMapping("/searchUser")
	public JsonData searchUser(String userId) {
		return userService.searchUser(userId);
	}
	
	/**
	 * 验证用户名是否重复
	 * @param userName
	 * @return
	 */
	@GetMapping("/userNameVerify")
	public JsonData userNameVerify(String userName) {
		return userService.userNameVerify(userName);
	}
	
	/**
	 * 获取所有的用户除了当前用户
	 * @param userId
	 * @return
	 */
	@GetMapping("/selectUserList")
	public JsonData selectFriendList(String userId) {
		return userService.selectUserList(userId);
	}

	/**
	 * 锁屏密码验证
	 * @param password 密码
	 * @return
	 */
	@PostMapping("/lockScreenPasswordVerify")
	public JsonData lockScreenPasswordVerify(String password){
		return userService.lockScreenPasswordVerify(password);
	}
}
