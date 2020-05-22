package com.znh.controller;

import com.znh.service.system.PermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class URLController {

    @Autowired
    PermissionsService permissionsService;

    @GetMapping(value="/navJson",produces = {"text/html;charset=utf-8"})
    public StringBuffer getUrlJson(){
        return permissionsService.selectByRoleId();
    }

}
