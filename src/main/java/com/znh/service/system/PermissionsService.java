package com.znh.service.system;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znh.dao.FirstLevelMenuMapper;
import com.znh.dao.SecondLevelMenuMapper;
import com.znh.dao.UserRoleMapper;
import com.znh.model.FirstLevelMenu;
import com.znh.model.SecondLevelMenu;
import com.znh.model.User;
import com.znh.model.UserRole;
import com.znh.util.JsonData;
import com.znh.util.MessageText;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionsService {

    private static final Logger log = LoggerFactory.getLogger(PermissionsService.class);

    @Autowired
    FirstLevelMenuMapper firstLevelMenuMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    @Autowired
    SecondLevelMenuMapper secondLevelMenuMapper;

    /**
     * 根据当前的登录用户返回对应的权限信息
     * @return
     */
    public StringBuffer selectByRoleId(){
        StringBuffer sb = new StringBuffer();
        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            // 查询出用户的权限ID
            UserRole userRole = userRoleMapper.selectByUserId(user.getUserId());
            // 查询出用户能访问的URL路径
            List<FirstLevelMenu> dataList = firstLevelMenuMapper.selectByRoleId(userRole.getRole_Id());
            List<StringBuffer> sbList = new ArrayList<StringBuffer>();
            if(dataList.size()>0){
                for(int i=0;i<dataList.size();i++){
                    StringBuffer dataSb = new StringBuffer("");
                    dataSb.append("{\"title\":\""+dataList.get(i).getFirstMenuName()+"\"," +
                            "\"href\":\"\""+","+
                            "\"icon\":\"" +dataList.get(i).getFirstMenuIcon()+"\","+
                            "\"spread\":"+"false,");
                    // 判断是否有二级菜单
                    if(dataList.get(i).getSecondLevelMenuList().size()>0){
                        dataSb.append("\"children\":[");
                        for(int a=0;a<dataList.get(i).getSecondLevelMenuList().size();a++){
                            dataSb.append("{\"title\":\""+dataList.get(i).getSecondLevelMenuList().get(a).getSecondMenuName()+"\","+
                                    "\"href\":\""+dataList.get(i).getSecondLevelMenuList().get(a).getSecondMenuEnglishName()+"\","+
                                    "\"icon\":\""+dataList.get(i).getSecondLevelMenuList().get(a).getSecondMenuIcon()+"\","+
                                    "\"spread\":"+"false}");
                            if(a<dataList.get(i).getSecondLevelMenuList().size()-1){
                                dataSb.append(",");
                            }
                        }
                        dataSb.append("]");
                    }else{
                        dataSb.append("\"children\":[]");
                    }
                    dataSb.append("}");
                    //System.out.print(dataSb);
                    if(i== dataList.size()-1){
                        sbList.add(dataSb);
                    }else{
                        sbList.add(dataSb.append(","));
                    }
                }
            }
            sb.append("[");
            for(int i=0;i<sbList.size();i++){
                sb.append(sbList.get(i));
            }
            sb.append("]");
            return sb;
        }catch (Exception e){
            log.error(e.getMessage());
            sb.append("");
        }
        return sb;
    }

    /**
     * 根据一级、二级菜单ID并且分页返回权限信息
     * @param firstMenuId 一级菜单ID
     * @param secondMenuId 二级菜单ID
     * @param page 页码
     * @param limit 分页数
     * @return
     */
    public JsonData selectByPageIndexAndOther(String firstMenuId, String secondMenuId, Integer page, Integer limit){
        if(page<=0) {
            return new JsonData(1,"失败","页数不能小于1");
        }
        if(limit<=0) {
            return new JsonData(1,"失败","页条数不能小于0");
        }
        JsonData jsonData = null;
        try{
            PageHelper.startPage(page,limit);
            List<FirstLevelMenu> menuDataList = firstLevelMenuMapper.selectByPageIndexAndOther(firstMenuId,secondMenuId);
            if(menuDataList.size()>0){
                PageInfo<FirstLevelMenu> result = new PageInfo<>(menuDataList);
                jsonData = new JsonData(0,"success",result.getList(),result.getTotal());
            }else{
                jsonData = new JsonData(1,"fail","没有数据！");
            }
            return jsonData;
        }catch (Exception e){
            log.error(e.getMessage());
            jsonData = new JsonData(2,"fail", MessageText.serverError);
        }
        return jsonData;
    }

    /**
     * 获取一级菜单数据
     * @return
     */
    public JsonData selectFirstLevelMenu(){
        List<FirstLevelMenu> dataList = firstLevelMenuMapper.selectFirstLevelMenu();
        JsonData jsonData;
        try{
            if(dataList.size()>0){
                jsonData = new JsonData(0,"success",dataList);
            }else{
                jsonData = new JsonData(1,"fail","没有数据！");
            }
            return jsonData;
        }catch (Exception e){
            log.error(e.getMessage());
            jsonData = new JsonData(2,"fail",MessageText.serverError);
        }
        return jsonData;
    }

    /**
     * 根据一级菜单ID查询旗下的二级菜单列表
     * @param firstMenuId
     * @return
     */
    public JsonData selectSecondLevelMenu(String firstMenuId){
        if(firstMenuId.trim().equals("") || firstMenuId.trim() == ""){
            return new JsonData(1,"fail","参数不能为空！");
        }
        JsonData jsonData;
        try{
            List<SecondLevelMenu> dataList = secondLevelMenuMapper.selectSecondLevelMenu(firstMenuId);
            if(dataList.size()>0){
                jsonData = new JsonData(0,"success",dataList);
            }else{
                jsonData = new JsonData(1,"fail","没有数据！");
            }
            return jsonData;
        }catch (Exception e){
            log.error(e.getMessage());
            jsonData = new JsonData(2,"fail",MessageText.serverError);
        }
        return jsonData;
    }
}
