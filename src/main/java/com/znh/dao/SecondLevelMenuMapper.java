package com.znh.dao;

import com.znh.model.SecondLevelMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SecondLevelMenuMapper {
    int deleteByPrimaryKey(String secondMenuId);

    int insert(SecondLevelMenu record);

    int insertSelective(SecondLevelMenu record);

    SecondLevelMenu selectByPrimaryKey(String secondMenuId);

    int updateByPrimaryKeySelective(SecondLevelMenu record);

    int updateByPrimaryKey(SecondLevelMenu record);

    @Select("select secondMenuId,firstMenu_Id,secondMenuName from secondlevelmenu where firstMenu_Id = #{firstMenuId}")
    List<SecondLevelMenu> selectSecondLevelMenu(String firstMenuId);
}