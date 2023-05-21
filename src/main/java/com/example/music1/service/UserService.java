package com.example.music1.service;

import com.example.music1.common.R;
import com.example.music1.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
* @author 黄烨
* @description 针对表【user】的数据库操作Service
* @createDate 2023-05-18 13:09:50
*/
@Service
public interface UserService extends IService<User> {

    R<User> login(User user,HttpSession session);

    R<User> sign(Map<String,String> map, HttpSession session);
}
