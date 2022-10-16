package com.banking.projectserver.controller;

import cn.hutool.jwt.JWTUtil;
import com.banking.projectserver.entity.User;
import com.banking.projectserver.entity.unityUser;
import com.banking.projectserver.response.Response;
import com.banking.projectserver.service.UnityService;
import com.banking.projectserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ program: ProjectServer
 * @ description: for unity
 * @ author: Ling Wang
 * @ date: 2021-12-28 20:31:53
 */
@RestController
@RequestMapping("user")
public class UnityController {

    @Autowired
    private UnityService unityService;

    @Autowired
    private UserService userService;

    @GetMapping("unity/{username}/{oldname}/{relationship}")
    public Response addUnity(@PathVariable String username, @PathVariable String oldname,@PathVariable String relationship){
        boolean f = unityService.addUnity(username,oldname,relationship);
        if(f) return Response.OK();
        else return Response.unknownError();
        }
    @GetMapping("unityverify/{username}")
    public Response verifyUnity(@PathVariable String username){
        boolean f = unityService.verifyUnity(username);
        if(f) return Response.OK();
        else return Response.unknownError();
    }
    @GetMapping("deleteverify/{username}")
    public Response deleteUnity(@PathVariable String username){
        boolean f = unityService.deleteUnity(username);
        if(f) return Response.OK();
        else return Response.unknownError();
    }
}
