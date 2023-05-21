package com.example.music1.mapper;

import com.example.music1.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music1.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 黄烨
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2023-05-18 13:09:50
 * @Entity generator.domain.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




