package com.example.music1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.music1.common.BaseContext;
import com.example.music1.common.R;
import com.example.music1.config.Constant;
import com.example.music1.entity.Music;
import com.example.music1.entity.User;
import com.example.music1.service.MusicService;
import com.example.music1.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author 黄烨
 * @Date 2023-05-21 1:07
 */
@RestController
@Slf4j
@Transactional
@RequestMapping("/music")
public class MusicController {
    @Resource
    private MusicService musicService;

    @Resource
    private UserService userService;

    @Value("${music.local.path}")
    private String SAVE_PATH;

    @RequestMapping(value = "/upload")
    public R<String> insertMusic(@RequestParam String singe,
                            @RequestParam("filename") MultipartFile file,
                            HttpServletRequest request) throws IOException {


        String filenameAndType = file.getOriginalFilename();
        File file1 =new File(SAVE_PATH+filenameAndType);

        //不存在则创建
        if(!file1.exists()){
            file1.mkdir();
        }

        try {
            file.transferTo(new File(SAVE_PATH+filenameAndType));
            //return R.success("上传成功");
        } catch (IOException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error("上传失败");
        }

        //获取当前是时间
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        //System.out.println(dateFormat.format(date));
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY));
        User user = userService.getOne(lambdaQueryWrapper);


         String title = filenameAndType.substring(filenameAndType.lastIndexOf('-')+1,filenameAndType.length()-1);
         Integer userid = user.getUserid() ;
         String url = "music/get?path="+title;
        String time = dateFormat.format(date);
        Music music = new Music();
        music.setTime(time);
        music.setSinger(singe);
        music.setUrl(url);
        music.setUserid(userid);
        music.setTitle(title);

        musicService.save(music);
        return R.success("上传成功");
    }

//    @Test
//    public void test()
//    {
//        Date date = new Date();
//        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        System.out.println(dateFormat.format(date));
//    }
}


