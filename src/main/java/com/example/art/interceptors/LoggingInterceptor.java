package com.example.art.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("received requestURL= {}" , request.getRequestURL());
        log.info(request.getAuthType());
        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()){
            String header = headers.nextElement();
            String value = request.getHeader(header);
            log.info("{}={}",header,value);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
//        log.info("requestURL= {}, response={}",request.getRequestURL(), response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("sent requestURL= {}, response={}",request.getRequestURL(), response.toString());
    }

}
