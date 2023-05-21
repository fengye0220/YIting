package com.example.music1.config;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.music1.common.BaseContext;
import com.example.music1.common.R;
import com.example.music1.entity.User;
import org.springframework.util.AntPathMatcher;

import javax.annotation.processing.Filer;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.FileAttribute;

/**
 * @Author 黄烨
 * @Date 2023-05-21 10:54
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request  = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURL = request.getRequestURI();

        //不需拦截
        String[] urls = new String[]{
                "/user/login",
                "/user/sendMsg",
                "/user/sign"
        };

        boolean check = check(urls,requestURL);

        if(check){
            filterChain.doFilter(request,response);
            return ;
        }
        if(request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY)!=null)
        {
            //long emp = (long) request.getSession().getAttribute(Constant.USERINFO_SESSION_KEY);
            //BaseContext.setCurrentId(Long.valueOf(emp.getUserid()));

            filterChain.doFilter(request,response);
            return;
        }


        //重复使用？问题？
        response.getWriter().write(R.error("NOTLOGIN").getCode());
    }

    public boolean check (String[] urls,String requestURL)
    {
        for(String url:urls)
        {
            boolean match = PATH_MATCHER.match(url,requestURL);
            if(match){
                return true;
            }
        }
        return false;
    }
}
