package com.znh.controller;

import com.znh.model.User;
import com.znh.service.system.UserService;
import com.znh.util.JsonData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 登录控制器
 * @author Sean
 *
 */
@RestController
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	UserService userService;

	/**
	 * 登录请求
	 * 这里的登录请求交由shiro去处理，这里只是负责对请求进行转发
	 * @return
	 */
	@PostMapping("/loginForm")
	public JsonData login(String username,String password) {
		// 获得当前Subject
		Subject currentUser = SecurityUtils.getSubject();
		JsonData jsonData = null;
		String msg = "";
		// 把用户名和密码封装为 UsernamePasswordToken 对象
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			// 执行登录.
			currentUser.login(token);
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			User userInfo = new User(user.getUserId(),user.getUserName(),user.getRealName(),user.getLastLoginDateTime(),user.getSkinTheme_Id());
			// 更新登录时间
			if(userService.updateLoginDateTime(user.getUserId())>0) {
				 jsonData = new JsonData(0,"登录成功",userInfo);
			}else {
				jsonData = new JsonData(1,"服务器出现故障，请联系管理员","null");
			}
			return jsonData;
			// 登录成功...
		} catch (IncorrectCredentialsException e) {
			msg = "登录密码错误";
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定";
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用";
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期";
		} catch (UnknownAccountException e) {
			msg = "帐号不存在";
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			msg = "系统出错，请联系管理员！";
		}
		log.error(msg);
		 jsonData = new JsonData(1,msg,"null");
		return jsonData;
	}
}
