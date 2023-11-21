package com.jwt.jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        //토큰 : "CORS"를 만들어줘야함. 언제? id,pw 정상적으로 들어와서 로그인이 완료되면 토큰을 만들어주고 그걸 응답을 해준다.
        //        // 요청할 때 마다 header의 authorization에 value값으로 토큰을 가지고 오겠죠?
        //        // 그때 토큰이 넘어오면 이 토큰이 내가 만든 토큰이 맞는지만 검증하면 됨(RSA, HS256)
        if(req.getMethod().equals("POST")) {

            if(req.getHeader("Authorization").equals("CORS")) { //header key: Authorization, value: CORS 일 때 doFilter 작동
                chain.doFilter(req,res);
            } else {//불일치 시 작업 중단
                PrintWriter outPrintWriter = res.getWriter();
                outPrintWriter.println("인증안됨");
            }
        }
    }
}
