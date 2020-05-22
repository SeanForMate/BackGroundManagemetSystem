package com.znh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.znh.model.Custom;
import com.znh.model.CustomCharacter;
import com.znh.model.CustomType;
import com.znh.model.CustomZone;
import com.znh.model.list.CustomList;

@Mapper
public interface CustomMapper {
	
	// 根据给的参数查询客户信息
	List<CustomList> selectByPageIndexAndOther(Custom custom);
	
	// 获取客户类型
	@Select("select * from customtype")
	List<CustomType> getCustomType();
	
	// 获取客户性质
	@Select("select * from customcharacter")
	List<CustomCharacter> getCustomCharacter();
	
	// 获取客户分区
	@Select("select * from customzone")
	List<CustomZone> getCustomZone();
	
	// 添加客户信息
	int insert(Custom custom);
	
	// 删除客户信息
	@Delete("delete from custom where customerId = #{customerId}")
	int delete(String customId);
	
	// 修改客户信息
	int update(Custom custom);
	
	// 根据customId查询客户信息
	CustomList selectCustomByCustomId(String customId);
}
