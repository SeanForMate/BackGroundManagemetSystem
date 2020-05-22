package com.znh.dao;

import com.znh.model.Permissions;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface PermissionsMapper {
    int deleteByPrimaryKey(String permissionsId);

    int insert(Permissions record);

    int insertSelective(Permissions record);

    Permissions selectByPrimaryKey(String permissionsId);

    int updateByPrimaryKeySelective(Permissions record);

    int updateByPrimaryKey(Permissions record);
}