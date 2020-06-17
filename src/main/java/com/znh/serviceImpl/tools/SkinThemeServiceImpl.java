package com.znh.serviceImpl.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.znh.dao.SkinThemeMapper;
import com.znh.dao.UserMapper;
import com.znh.model.SkinTheme;
import com.znh.model.User;
import com.znh.util.JsonData;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SkinThemeServiceImpl {
	
	private static final Logger log = LoggerFactory.getLogger(SkinThemeServiceImpl.class);
	
	@Autowired
	SkinThemeMapper skinThemeMapper;
	
	@Autowired
	UserMapper userMapper;
	
	/**
	 * 获取全部皮肤主题
	 * @return
	 */
	public JsonData selectSkinTheme() {
		 JsonData jsonData = null;
		 try {
			 List<SkinTheme> dataList = new ArrayList<SkinTheme>();
				dataList = skinThemeMapper.selectSkinTheme();
				if(dataList.size()>0) {
					jsonData = new JsonData(0,"成功",dataList);
				}else {
					jsonData = new JsonData(1,"失败","暂时没数据");
				}
				return jsonData;
		 }catch (Exception e) {
			// TODO: handle exception
			 log.error(e.getMessage());
			 jsonData = new JsonData(2,"失败","服务器访问出错");
		}
		return jsonData;
	}
	
	/**
	 * 获取皮肤
	 * @param skinThemeId
	 * @return
	 */
	public JsonData selectByPrimaryKey() {
		JsonData jsonData = null;
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			String skinThemeId = userMapper.selectSkinThemeIdByUserId(user.getUserId());
			SkinTheme skinTheme = skinThemeMapper.selectByPrimaryKey(skinThemeId);
			if(skinTheme!=null) {
				jsonData = new JsonData(0,"成功",skinTheme);
			}else {
				jsonData = new JsonData(1,"失败","没有数据！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 根据当前登录的用户修改皮肤主题
	 * @param skinTheme_Id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData updateSkinThemeById(String skinTheme_Id) {
		JsonData jsonData = null;
		if(skinTheme_Id.trim().equals("") || skinTheme_Id==null) {
			return new JsonData(2,"失败","请求参数为空，请检查请求！");
		}
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			System.out.println(skinTheme_Id+"===="+user.getUserId());
			// 判断修改是否成功
			if(userMapper.updateUserSkinTheme(skinTheme_Id, user.getUserId())>0) {
				jsonData = new JsonData(0,"成功",null);
			}else{
				jsonData = new JsonData(1,"失败","修改失败，请联系管理员。");
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			jsonData = new JsonData(2,"失败","服务器异常");
		}
		return jsonData;
	}

	@Transactional(rollbackFor = Exception.class)
	public JsonData insertSkinTheme(SkinTheme skinTheme) {
		JsonData jsonData = null;
		if((skinTheme.getSkinName().trim().equals("") || skinTheme.getSkinName()==null) && (skinTheme.getTopColor().trim().equals("") || skinTheme.getTopColor()==null) && (skinTheme.getLeftColor().trim().equals("") || skinTheme.getLeftColor()==null)) {
			return new JsonData(2,"失败","请求参数为空，请检查请求！");
		}
		try {
			skinTheme.setSkinThemeId(UUID.randomUUID().toString());
			if(skinThemeMapper.insert(skinTheme)>0) {
				 jsonData = new JsonData(0,"成功",null);
			}else {
				 jsonData = new JsonData(1,"失败","数据添加失败，请联系管理员！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			jsonData = new JsonData(3,"失败","服务器异常");
		}
		return jsonData;
	}
}
