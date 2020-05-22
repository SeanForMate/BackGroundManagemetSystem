package com.znh.service.system;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.znh.service.base.CustomService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.UserMapper;
import com.znh.model.User;
import com.znh.util.JsonData;
import com.znh.util.ShiroUtil;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserMapper userDao;
	
	/**
	 * 根据用户名查询用户全部信息
	 * @param userName 
	 * @return
	 */
	public User selectUserByUserName(String userName) {
		try {
			if(userName.trim().equals("")) {
				return null;
			}
			return userDao.selectUserByUserName(userName);
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 根据条件并分页查询用户信息
	 * @param user
	 * @param page
	 * @param limit
	 * @return
	 */
	public JsonData selectByPageIndexAndOther(User user,String roleId,Integer pageIndex,Integer limit){
		if(pageIndex<=0) {
			return new JsonData(1,"失败","页数不能小于1");
		}
		if(limit<=0) {
			return new JsonData(1,"失败","页条数不能小于0");
		}
		JsonData jsonData = null;
		try {
			PageHelper.startPage(pageIndex, limit);
			List<User> userList = userDao.selectByPageIndexAndOther(user,roleId);
			PageInfo<User> result = new PageInfo<>(userList);
			if(result.getList().size()>0) {
				jsonData = new JsonData(0,"成功",result.getList(),result.getTotal());
			}else {
				jsonData = new JsonData(1,"暂无数据",null,0);
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	public JsonData searchUser(String userId) {
		JsonData jsonData = null;
		if(userId.trim()=="") {
			return new JsonData(1,"用户不存在",null);
		}
		try {
			if(userDao.searchUser(userId)!=null) {
				jsonData = new JsonData(0,"成功",userDao.searchUser(userId));
			}else {
				jsonData = new JsonData(1,"没有任何数据",null);
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 验证用户名是否重复
	 * @param userName
	 * @return
	 */
	public JsonData userNameVerify(String userName) {
		JsonData jsonData = null;
		if(userName.trim().equals("")) {
			return null;
		}
		try {
			if(userDao.userNameVerify(userName)>0) {
				jsonData = new JsonData(1,"用户名已存在！",null);
			}else {
				jsonData = new JsonData(0,"可以使用！",null);
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 获取所有的用户除了当前用户
	 * @param userId
	 * @return
	 */
	public JsonData selectUserList(String userId) {
		JsonData jsonData = null;
		if(userId.trim().equals("")) {
			return new JsonData();
		}
		try {
			if(userDao.selectUserList(userId).size()>0) {
				jsonData = new JsonData(0,"成功",userDao.selectUserList(userId));
			}else {
				jsonData = new JsonData(1,"失败","没有用户数据");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 更新用户登录的时间
	 * @param userId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateLoginDateTime(String userId) {
		try {
			if(userId.trim().equals("")) {
				return 0;
			}
			return userDao.updateLoginDateTime(userId);
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return 0;
	}
	
	/**
	 * 添加用户信息
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData insertUser(User user) {
		JsonData jsonData = null;
		if(user==null) {
			return new JsonData(1,"用户信息不能为空","");
		}
		try {
			user.setUserId(UUID.randomUUID().toString());
			String salt = ShiroUtil.createSalt();
			user.setSalt(salt);
			user.setPassword(ShiroUtil.saltEncryption(user.getPassword(), salt));
			user.setCreatePerson(((User)SecurityUtils.getSubject().getPrincipal()).getRealName());
			user.setCreateDateTime(new Date());
			if(userDao.insertSelective(user)>0) {
				jsonData = new JsonData(0,"成功","");
			}else {
				jsonData = new JsonData(1,"失败","");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 根据userId删除用户信息
	 * @param userId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData deleteUser(String[] userId) {
		JsonData jsonData = null;
		if(userId.length<=0) {
			return new JsonData(1,"没有要删除的用户","");
		}
		try {
			for(String user:userId) {
				if(userDao.deleteByPrimaryKey(user)<0) {
					return new JsonData(1,"删除失败","");
				}
			}
			return new JsonData(0,"成功","");
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 根据用户userId修改用户信息
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData updateUser(User user) {
		JsonData jsonData = null;
		if(user==null) {
			return new JsonData(1,"用户信息不能为空","");
		}
		try {
			// 判断密码是否需要修改
			if(!user.getPassword().trim().equals("")) {
				String salt = ShiroUtil.createSalt();
				user.setSalt(salt);
				user.setPassword(ShiroUtil.saltEncryption(user.getPassword(), salt));
			}
			if(userDao.updateByPrimaryKeySelective(user)>0) {
				jsonData = new JsonData(0,"成功","");
			}else {
				jsonData = new JsonData(1,"失败","");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}

	/**
	 * 锁屏密码验证
	 * @param password
	 * @return
	 */
	public JsonData lockScreenPasswordVerify(String password){
		JsonData jsonData = null;
		try{
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			String pwd = ShiroUtil.saltEncryption(password,user.getSalt());
			if(user.getPassword().equals(pwd)){
				jsonData = new JsonData(0,"成功","验证成功！");
			}else{
				jsonData = new JsonData(1,"失败","密码错误，请重新输入！");
			}
			return jsonData;
		}catch (Exception e){
			log.error(e.getMessage());
		}
		return jsonData;
	}

}
