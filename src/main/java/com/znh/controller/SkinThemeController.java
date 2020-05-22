package com.znh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.znh.model.SkinTheme;
import com.znh.service.SkinThemeService;
import com.znh.util.JsonData;

@RestController
@RequestMapping("/api/v1/skinTheme")
public class SkinThemeController {
	
	@Autowired
	SkinThemeService skinThemeService;
	
	/**
	 * 获取全部的皮肤主题
	 * @return
	 */
	@GetMapping("/selectSkinTheme")
	public JsonData selectSkinTheme() {
		return skinThemeService.selectSkinTheme();
	}
	
	/**
	 * 根据ID获取皮肤主题
	 * @param skinThemeId
	 * @return
	 */
	@GetMapping("/selectSkinThemeById")
	public JsonData selectSkinThemeById() {
		return skinThemeService.selectByPrimaryKey();
	}
	
	/**
	 * 根据当前登录的用户修改皮肤主题
	 * @return skinThemeId 主题ID
	 */
	@PostMapping("/updateSkinThemeById")
	public JsonData updateSkinThemeById(String skinThemeId) {
		return skinThemeService.updateSkinThemeById(skinThemeId);
	}
	
	/**
	 * 添加皮肤主题
	 * @return
	 */
	@PostMapping("/insertSkinTheme")
	public JsonData insertSkinTheme(SkinTheme skinTheme) {
		return skinThemeService.insertSkinTheme(skinTheme);
	}
	
}
