package com.znh.controller.system;

import com.znh.service.system.PermissionsService;
import com.znh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionsController {

    @Autowired
    PermissionsService permissionsService;

    /**
     * 根据一级、二级菜单ID并且分页返回权限信息
     * @param firstMenuId 一级菜单ID
     * @param secondMenuId 二级菜单ID
     * @param page 页码
     * @param limit 分页数
     * @return
     */
    @GetMapping("/selectByPageIndexAndOther")
    public JsonData selectByPageIndexAndOther(@RequestParam(name="firstMenuId",required = false) String firstMenuId,@RequestParam(name="secondMenuId",required = false) String secondMenuId, Integer page, Integer limit){
        return permissionsService.selectByPageIndexAndOther(firstMenuId,secondMenuId,page,limit);
    }

    /**
     * 获取一级菜单数据
     * @return
     */
    @GetMapping("/selectFirstLevelMenu")
    public JsonData selectFirstLevelMenu(){
        return permissionsService.selectFirstLevelMenu();
    }

    /**
     * 根据一级菜单ID查询旗下的二级菜单列表
     * @param firstMenuId 一级菜单ID
     * @return
     */
    @GetMapping("/selectSecondLevelMenu")
    public JsonData selectSecondLevelMenu(String firstMenuId){
        return permissionsService.selectSecondLevelMenu(firstMenuId);
    }

}
