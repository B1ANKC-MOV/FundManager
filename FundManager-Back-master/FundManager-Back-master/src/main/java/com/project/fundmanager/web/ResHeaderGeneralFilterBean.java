package com.project.fundmanager.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//@Order(5)
@Component
public class ResHeaderGeneralFilterBean extends FilterRegistrationBean<Filter> {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {
        setFilter(new ResHeaderGeneralFilter());
        setUrlPatterns(List.of("/*"));
        setOrder(5);
    }

    class ResHeaderGeneralFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setHeader("Access-Control-Allow-Origin",request.getScheme()+"://"+request.getServerName()+":8080");
            resp.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            chain.doFilter(request, response);
        }
    }
}
