package com.example.music1.entity;

import lombok.Data;

/**
 * @Author 黄烨
 * @Date 2023-05-21 1:02
 */
@Data
public class Music {
    private Integer id;
    private String title;
    private String singer;
    private String time;
    private String url;
    private Integer userid;
}
