package com.jwt.jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter1 implements Filter {
//컨트롤러 진입 전 넘어온 JWT값을 확인해 정확히 일치하면 controller로 보내주고 아닐 시 filter단에서 바로 중단시킴
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("필터1");
        chain.doFilter(request, response);

    }

}
