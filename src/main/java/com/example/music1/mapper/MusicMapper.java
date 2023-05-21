package com.example.music1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.music1.entity.Music;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author 黄烨
 * @Date 2023-05-21 1:09
 */
@Mapper
public interface MusicMapper extends BaseMapper<Music> {
//    Integer insert(@Param("title") String title,@Param("singer") String single,
//                   @Param("time") String time,@Param("url") String url,
//                   @Param("userid") Integer userid);
}
