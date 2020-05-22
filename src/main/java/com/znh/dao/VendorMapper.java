package com.znh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.znh.model.Vendor;
@Mapper
public interface VendorMapper {
	
	List<Vendor> selectVendorByPageIndexAndOther(Vendor vendor);
	
	List<Vendor> selectAll();
	
    int deleteByPrimaryKey(String vendorId);

    int insert(Vendor record);

    int insertSelective(Vendor record);

    Vendor selectByPrimaryKey(String vendorId);

    int updateByPrimaryKeySelective(Vendor record);

    int updateByPrimaryKey(Vendor record);
}