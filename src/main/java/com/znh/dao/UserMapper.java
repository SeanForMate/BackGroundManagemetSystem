package com.znh.dao;

import com.znh.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface UserMapper {
	
	// 登录验证
	User selectUserByUserName(String userName);
	
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    // 更新登录时间
    @Update("update user set lastLoginDateTime = NOW() where userId = #{userId}")
    int updateLoginDateTime(String userId);
    
    // 更改用户主题ID
    @Update("update user set skinTheme_Id = #{skinTheme_Id} where userId = #{userId}")
    int updateUserSkinTheme(@Param("skinTheme_Id")String skinTheme_Id, @Param("userId")String userId);
    
    // 查询当前用户的主题
    @Select("select skinTheme_Id from user where userId = #{userId}")
    String selectSkinThemeIdByUserId(String userId);
    
    // 根据条件查询用户信息并分页
    List<User> selectByPageIndexAndOther(@Param("user")User user, @Param("roleId") String roleId);
    
    // 根据ID查询用户信息
    User searchUser(String userId);
    
    // 验证用户名是否重复
    @Select("select count(1) from user where userName = #{userName}")
    int userNameVerify(String userName);
    
    @Select("select userId,realName from user where userId != #{userId}")
    List<User> selectUserList(String userId);
}