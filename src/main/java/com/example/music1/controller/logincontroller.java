package com.example.music1.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.music1.common.R;
import com.example.music1.entity.User;
import com.example.music1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import utils.ValidateCodeUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

/*@author 黄烨
 * */

@Slf4j
@RequestMapping("/user")
@RestController
public class logincontroller {
    @Resource
    private UserService userService;
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    @PostMapping ("/login")
    public R<User> login(@RequestBody User user,HttpSession session){
        return userService.login(user,session);
    }

    @PostMapping("/sign")
    public R<User> sign(@RequestBody Map<String,String> map, HttpSession session){
        return userService.sign(map,session);
    }

    @GetMapping("/sendMsg")
    public R<String> sendMsg(String phone, HttpSession session){
        //获取手机号
        //String phone = user.getPhone();
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User one = userService.getOne(queryWrapper);
        if(one!=null){
            return R.error("该用户已注册");
        }

        if(!(phone.matches(REGEX_MOBILE)))
        {
            return R.error("手机号错误");
        }

        //生成随机的4位验证码
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        log.info("code={}",code);

        session.setAttribute(phone,code);
        //调用阿里云提供的短信服务API完成发送短信
        //SMSUtils.sendMessage("易听音乐","",phone,code);
        return R.success("手机验证码短信发送成功");
    }
}
