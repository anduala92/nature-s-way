package com.example.naturesway.web.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FaviconInterceptor extends HandlerInterceptorAdapter {
    private static final String FAVICON_LINK = "https://res.cloudinary.com/anduala/image/upload/v1615247873/wallpapers/nature_s_way_logo_dhsibw.png";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("favicon", FAVICON_LINK);
        }
    }
}
