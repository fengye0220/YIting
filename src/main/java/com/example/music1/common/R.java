package com.example.music1.common;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(T object)
    {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 200;
        return r;
    }

    public static <T> R<T> errorpassword(String msg){
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 502;
        return r;
    }

    public static <T> R<T> errornumber(String msg){
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 400;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
