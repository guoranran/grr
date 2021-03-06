package com.fzz.controller;

import com.fzz.dao.UserDao;
import com.fzz.entity.User;
import com.fzz.service.impl.UserServiceImpl;
import com.fzz.util.JxlUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fzz on 2017/3/28.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    //设置查询姓名
    @RequestMapping("/serqueryname")
    public void setQueryName(
            @RequestParam("username")String username,
            HttpSession httpSession
    ){
        httpSession.setAttribute("queryname",username);
    }

    @RequestMapping("alluser")
    public ModelAndView alluser(){
        ModelAndView modelAndView =new ModelAndView("alluser");
        Map<String,Object> map=new HashMap<String,Object>();
        List<User> users=userService.getAll();
        map.put("users",users);
        modelAndView.addAllObjects(map);
        return modelAndView;


    }


    @RequestMapping("/info")//query user info by session "queryname"
    public User info(
            HttpSession httpSession
    ) {
               String username= (String) httpSession.getAttribute("queryname");
               return userService.findByName(username);
    }


    @RequestMapping("info/{username}")
    public User userinfo(
            @PathVariable("username")String username
    ){
                //meishei
                return userService.findByName(username);
    }

    @RequestMapping("/all")//list all user simple info
    public List<User> users() {
        return userService.getAll();
    }

    @RequestMapping("/login")//user login
    public User login(
            @RequestParam("username")String username,
            @RequestParam("password")String password,
            HttpSession httpSession
    ){
        return userService.login(username,password,httpSession);
    }
    @ApiOperation(value = "用户注册")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true,dataType = "String"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String")

    })
    @RequestMapping("register")
    public User register(
            @RequestParam("username")String username,
            @RequestParam("password")String password
    ){
                return userService.register(username,password);
    }

    @RequestMapping("batch_register")
    public boolean batchRegister(
            @RequestParam("users")MultipartFile multipartFile
    ) throws IOException {

        return true;
    }

    @RequestMapping("modify")
    public User modify(
            @RequestParam(required = true, value = "username") String username,
            @RequestParam("password") String password
    ) {
                return userService.modify(username,password);
    }

    @RequestMapping("checkusername")//检查用户名是否存在
    public boolean checkUserExist(@RequestParam("username") String username) {
        return userService.checkUserExist(username);
    }


    @RequestMapping("fanslist")//获取粉丝列表
    public Set<User> fanslist(HttpSession httpSession) {
                return userService.getfanslist(httpSession);
    }

    @RequestMapping("attationlist")
    public Set<User> attationlist(HttpSession httpSession) {
                return userService.getattationlist(httpSession);
    }

    @RequestMapping("attationto/{userid}")//通过ID关注其他用户
    public User attationto(
            @PathVariable("userid") int userid,
            HttpSession httpSession

    ){
                return userService.attationto(userid,httpSession);
    }

    @RequestMapping("currentuser")
    public User currentuser(
            HttpSession httpSession
    ){
        return userService.getCurrentUser(httpSession);
    }
}
