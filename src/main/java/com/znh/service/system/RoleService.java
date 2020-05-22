package com.znh.service.system;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.RoleMapper;
import com.znh.model.Role;
import com.znh.model.User;
import com.znh.util.JsonData;
import com.znh.util.MessageText;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class RoleService {

	private static final Logger log = LoggerFactory.getLogger(RoleService.class);
	
	@Autowired
	RoleMapper roleMapper;
	
	/**
	 * 获取所有用户角色信息并且分页
	 * @return
	 */
	public JsonData selectByPageIndex(Integer pageIndex,Integer limit) {
		JsonData jsonData;
		try {
			PageHelper.startPage(pageIndex, limit);
			List<Role> roleList = roleMapper.select();
			PageInfo<Role> result = new PageInfo<>(roleList);
			if(result.getList().size()>0) {
				jsonData = new JsonData(0,"success",result.getList(),result.getTotal());
			}else {
				jsonData = new JsonData(1,"fail","没有数据！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail",MessageText.serverError);
		}
		return jsonData;
	}

	/**
	 * 获取所有用户角色信息
	 * @return
	 */
	public JsonData selectRole(){
		JsonData jsonData;
		try{
			List<Role> roleList = roleMapper.select();
			if(roleList.size()>0){
				jsonData = new JsonData(0,"success",roleList);
			}else{
				jsonData = new JsonData(1,"fail","没有数据");
			}
		}catch (Exception e){
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail",MessageText.serverError);
		}
		return jsonData;
	}

	/**
	 * 添加角色信息
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData insertRole(Role role){
		JsonData jsonData;
		if(role == null){
			return new JsonData(1,"fail","参数不能为空！");
		}
		try{
			role.setRoleId(UUID.randomUUID().toString());
			role.setCreatePerson(((User)SecurityUtils.getSubject().getPrincipal()).getRealName());
			role.setCreateDateTime(new Date());
			if(roleMapper.insertSelective(role)>0){
                jsonData = new JsonData(0,"success","添加成功");
			}else{
                jsonData = new JsonData(1,"fail","添加失败");
			}
			return jsonData;
		}catch (Exception e){
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail",MessageText.serverError);
		}
		return jsonData;
	}

	/**
	 * 根据角色ID删除角色信息
	 * @param roleId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData deleteRole(String roleId){
		List<String> roleList = JSON.parseArray(roleId,String.class);
		if(roleList.size()==0){
			return new JsonData(1,"fail","没有数据！");
		}
		JsonData jsonData;
		try{
			for (String data:roleList){
				if(roleMapper.deleteByPrimaryKey(data)<=0){
					return new JsonData(1,"fail","删除失败");
				}
			}
			jsonData = new JsonData(0,"success","删除成功");
			return jsonData;
		}catch (Exception e){
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail",MessageText.serverError);
		}
		return jsonData;
	}

	/**
	 * 根据角色ID修改角色信息
	 * @param role
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData updateRole(Role role){
		if(role == null){
			return new JsonData(1,"fail","参数不能为空！");
		}
		JsonData jsonData;
		try{
			if(roleMapper.updateByPrimaryKeySelective(role)>0){
				jsonData = new JsonData(0,"success","修改成功");
			}else{
				jsonData = new JsonData(1,"fail","修改失败！");
			}
			return jsonData;
		}catch (Exception e){
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail",MessageText.serverError);
		}
		return jsonData;
	}

	/**
	 * 根据角色ID查询角色信息
	 * @param roleId
	 * @return
	 */
	public JsonData searchRole(String roleId){
		if(roleId.trim().equals("") || roleId.trim()==""){
			return new JsonData(1,"fail","参数不能为空！");
		}
		JsonData jsonData;
		try{
			Role role = roleMapper.selectByPrimaryKey(roleId);
			if(role != null){
				jsonData = new JsonData(0,"success",role);
			}else{
				jsonData = new JsonData(1,"fail",role);
			}
			return jsonData;
		}catch (Exception e){
			log.error(e.getMessage());
			jsonData = new JsonData(2,"fail", MessageText.serverError);
		}
		return jsonData;
	}
}
