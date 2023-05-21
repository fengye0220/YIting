package com.example.music1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.music1.common.BaseContext;
import com.example.music1.common.R;
import com.example.music1.config.Constant;
import com.example.music1.entity.User;
import com.example.music1.mapper.UserMapper;
import com.example.music1.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

/**
* @author 黄烨
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-05-18 13:09:50
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";

    @Override
    public R<User> login(User user,HttpSession session) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,user.getPhone());
        User user1 = getOne(queryWrapper);

        if(user1==null){
            return R.errornumber("用户名错误");
        }

        if(!Objects.equals(user1.getPassword(), user.getPassword()))
        {
            return R.errorpassword("密码错误");
        }

        session.setAttribute(Constant.USERINFO_SESSION_KEY,user.getPhone());
        //BaseContext.setCurrentId(Long.valueOf(user.getUserid()));
        return R.success(user);
    }

    @Override
    public R<User> sign(Map<String,String> map, HttpSession session) {
        //获取手机号
        String phone = map.get("phone");

        //获取验证码
        String code = map.get("code");
        if(!(phone.matches(REGEX_MOBILE))){
            return R.error("手机号错误");
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone,phone);
        User one = getOne(queryWrapper);
        if(one!=null){
            return R.error("该用户已注册");
        }

        String code1 = (String) session.getAttribute(phone);

        if(!(code.equals(code1)))
        {
            return R.error("验证码错误");
        }

        User user = new User();
        user.setPassword("123456");
        user.setPhone(phone);
        this.save(user);
        session.setAttribute(Constant.USERINFO_SESSION_KEY,user.getPhone());
        //BaseContext.setCurrentId(Long.valueOf(user.getUserid()));
        return  R.success(user);
    }
}




