package com.znh.dao;

import com.znh.model.Commodity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommodityMapper {
    int deleteByPrimaryKey(String commodityId);

    int insert(Commodity record);

    int insertSelective(Commodity record);

    Commodity selectByPrimaryKey(String commodityId);

    int updateByPrimaryKeySelective(Commodity record);

    int updateByPrimaryKey(Commodity record);
    
    List<Commodity> selectByPageIndexAndOther(Commodity record);
}