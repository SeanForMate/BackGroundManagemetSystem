package com.znh.dao;

import com.znh.model.FirstLevelMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FirstLevelMenuMapper {
    int deleteByPrimaryKey(String firstMenuId);

    int insert(FirstLevelMenu record);

    int insertSelective(FirstLevelMenu record);

    FirstLevelMenu selectByPrimaryKey(String firstMenuId);

    int updateByPrimaryKeySelective(FirstLevelMenu record);

    int updateByPrimaryKey(FirstLevelMenu record);

    List<FirstLevelMenu> selectByRoleId(String role_Id);

    List<FirstLevelMenu> selectByPageIndexAndOther(@Param(value="firstMenuId")String firstMenuId, @Param(value="secondMenuId")String secondMenuId);

    @Select("select firstMenuId,firstMenuName,firstMenuIndex from firstLevelMenu ORDER BY firstMenuIndex ASC")
    List<FirstLevelMenu> selectFirstLevelMenu();
}