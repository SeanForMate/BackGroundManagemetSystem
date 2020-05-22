package com.znh.service.base;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.VendorMapper;
import com.znh.model.User;
import com.znh.model.Vendor;
import com.znh.util.JsonData;

@Service
public class VendorService {
	
	private static final Logger log = LoggerFactory.getLogger(VendorService.class);
	
	@Autowired
	VendorMapper vendorMapper;
	
	/**
	 * 根据查询条件返回供应商信息
	 * @param pageIndex 页码
	 * @param limit 分页数
	 * @param vendor 供应商
	 * @return
	 */
	public JsonData selectVendorByPageIndexAndOther(Integer pageIndex,Integer limit,Vendor vendor) {
		JsonData jsonData = null;
		if(pageIndex<=0) {
			return new JsonData("1","失败","页码不能小于0");
		}
		try {
			PageHelper.startPage(pageIndex,limit);
			List<Vendor> vendorList = vendorMapper.selectVendorByPageIndexAndOther(vendor);
			PageInfo<Vendor> result = new PageInfo<>(vendorList);
			if(result.getList().size()>0) {
				jsonData = new JsonData(0,"成功",result.getList(),result.getTotal());
			}else {
				jsonData = new JsonData(1,"没有数据！","",0);
			}
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 获取全部的供应商名称和ID
	 * @return
	 */
	public JsonData selectAll() {
		JsonData jsonData = null;
		try {
			List<Vendor> vendorList = vendorMapper.selectAll();
			if(vendorList.size()>0) {
				jsonData = new JsonData(0,"成功",vendorList);
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
	 * 添加供应商信息
	 * @param vendor
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData insertVendor(Vendor vendor) {
		JsonData jsonData = null;
		if(vendor==null) {
			return new JsonData("1","失败","数据为空，请重新填写");
		}
		try {
			User user = (User)SecurityUtils.getSubject().getPrincipal();
			vendor.setVendorId(UUID.randomUUID().toString());
			vendor.setCreatePerson(user.getRealName());
			vendor.setCreateDateTime(new Date());
			if(vendorMapper.insertSelective(vendor)>0) {
				jsonData = new JsonData("0","成功","添加成功");
			}else {
				jsonData = new JsonData("1","失败","添加失败，请联系管理员！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 修改供应商信息
	 * @param vendor
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData updateVendor(Vendor vendor) {
		JsonData jsonData = null;
		if(vendor==null) {
			return new JsonData("1","失败","数据为空，请重新填写");
		}
		try {
			if(vendorMapper.updateByPrimaryKeySelective(vendor)>0) {
				jsonData = new JsonData("0","成功","修改成功");
			}else {
				jsonData = new JsonData("1","失败","修改失败，请联系管理员！");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 删除供应商信息
	 * @param vendorId
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public JsonData deleteVendor(String[] vendorId) {
		JsonData jsonData = null;
		if(vendorId.length<=0) {
			return new JsonData("1","失败","数据为空，请重新填写");
		}
		try {
			for(String data:vendorId) {
				if(vendorMapper.deleteByPrimaryKey(data)<=0) {
					return new JsonData("1","失败","数据为空，请重新填写");
				}
			}
			return jsonData = new JsonData("0","成功","删除成功");
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
	/**
	 * 根据供应商ID查询供应商信息
	 * @param vendorId
	 * @return
	 */
	public JsonData searchVendor(String vendorId) {
		JsonData jsonData = null;
		if(vendorId.trim().equals("")) {
			return new JsonData("1","失败","数据为空，请重新填写");
		}
		try {
			Vendor vendor = vendorMapper.selectByPrimaryKey(vendorId);
			if(vendor!=null) {
				jsonData = new JsonData("0","成功",vendor);
			}else {
				jsonData = new JsonData("1","失败","查询的数据不存在，请检查供应商");
			}
			return jsonData;
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
		}
		return jsonData;
	}
	
}
