package com.znh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.znh.model.SkinTheme;
@Mapper
public interface SkinThemeMapper {
	
	List<SkinTheme> selectSkinTheme();
	
    int deleteByPrimaryKey(String skinThemeId);

    int insert(SkinTheme record);

    int insertSelective(SkinTheme record);

    SkinTheme selectByPrimaryKey(String skinThemeId);

    int updateByPrimaryKeySelective(SkinTheme record);

    int updateByPrimaryKey(SkinTheme record);
    
}