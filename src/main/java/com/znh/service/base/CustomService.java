package com.znh.service.base;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.CustomMapper;
import com.znh.model.Custom;
import com.znh.model.User;
import com.znh.model.list.CustomList;
import com.znh.util.JsonData;

@Service
public class CustomService {
	
	private static final Logger log = LoggerFactory.getLogger(CustomService.class);
	
	@Autowired
	CustomMapper customDao;
	
	/**
	 * 根据给的参数查询客户信息并且分页
	 * @param custom 客户信息
	 * @param pageIndex 页码
	 * @param limit 分页数
	 * @return
	 */
	public JsonData selectByPageIndexAndOther(Custom custom,Integer pageIndex,Integer limit) {
		JsonData jsonData = null;
		if(pageIndex<=0) {
			return  jsonData = new JsonData("1","失败","页码不能小于0！");
		}
		if(limit<=0) {
			return  jsonData = new JsonData("1","失败","分页数不能小于0！");
		}
		try {
			PageHelper.startPage(pageIndex,limit);
			List<CustomList> customList = customDao.selectByPageIndexAndOther(custom);
			PageInfo<CustomList> result = new PageInfo<>(customList);
			if(result.getList().size()>0) {
				jsonData = new JsonData(0,"成功",result.getList(),result.getTotal());
			}else {
				jsonData = new JsonData(1,"没有数据","",0);
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 获取客户分类数据
	 * @return
	 */
	public JsonData getCustomType() {
		JsonData jsonData = null;
		try {
			if(customDao.getCustomType().size()>0) {
				jsonData = new JsonData(0,"成功",customDao.getCustomType());
			}else {
				jsonData = new JsonData(1,"失败","没有数据");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 获取客户性质数据
	 * @return
	 */
	public JsonData getCustomCharacter() {
		JsonData jsonData = null;
		try {
			if(customDao.getCustomCharacter().size()>0) {
				jsonData = new JsonData(0,"成功",customDao.getCustomCharacter());
			}else {
				jsonData = new JsonData(1,"失败","没有数据");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 获取客户分区数据
	 * @return
	 */
	public JsonData getCustomZone() {
		JsonData jsonData = null;
		try {
			if(customDao.getCustomZone().size()>0) {
				jsonData = new JsonData(0,"成功",customDao.getCustomZone());
			}else {
				jsonData = new JsonData(1,"失败","没有数据");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 添加客户信息数据
	 * @param custom
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData insertCustom(Custom custom) {
		JsonData jsonData = null;
		if(custom==null) {
			return jsonData = new JsonData(1,"失败","数据不能为空");
		}
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			custom.setCreatePerson(user.getRealName());
			custom.setCreateDateTime(new Date());
			custom.setCustomerId(UUID.randomUUID().toString());
			if(customDao.insert(custom)>0) {
				jsonData = new JsonData(0,"成功","添加成功");
			}else {
				jsonData = new JsonData(1,"失败","添加失败，请联系管理员！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 删除客户信息
	 * @param customId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData deleteCustom(String[] customId) {
		JsonData jsonData = null;
		if(customId.length<=0) {
			return jsonData = new JsonData(1,"失败","没有数据");
		}
		try {
			for(String data:customId) {
				if(customDao.delete(data)<=0) {
					return jsonData = new JsonData(1,"失败","删除失败，请联系管理员！");
				}
			}
			return jsonData = new JsonData(0,"成功","");
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 查询客户信息
	 * @param customId
	 * @return
	 */
	public JsonData searchCustom(String customId) {
		JsonData jsonData = null;
		if(customId.trim().equals("")) {
			return jsonData = new JsonData(1,"失败","查找的客户不存在！");
		}
		try {
			CustomList custom = customDao.selectCustomByCustomId(customId);
			if(custom!=null) {
				jsonData = new JsonData(0,"成功",custom);
			}else {
				jsonData = new JsonData(1,"失败","没有数据");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 修改客户信息数据
	 * @param custom
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData updateCustom(Custom custom) {
		JsonData jsonData = null;
		if(custom==null) {
			return jsonData = new JsonData(1,"失败","填写的信息有误！");
		}
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			custom.setUpdatePerson(user.getRealName());
			if(customDao.update(custom)>0) {
				jsonData = new JsonData(0,"成功","");
			}else {
				jsonData = new JsonData(1,"失败","修改失败，请联系管理员！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
}
