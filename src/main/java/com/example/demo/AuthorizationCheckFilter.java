package com.example.demo;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

import com.example.demo.repositories.MemberRepository;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.blackListResposity;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationCheckFilter extends OncePerRequestFilter{


    

    boolean isPathInList(String path, List<String> pathList) {
        for (String pattern : pathList) {
            if (path.startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }
    final String[] AUTH_WHITELIST = {
        "/v3/api-docs/**",
        "v3/api-docs/**",
        "/swagger-ui/",
        "/api-docs",
        "/api-docs/**",
        "/swagger-ui/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/webjars/**",
        "/api/public/**",
        "/actuator/**" ,
        "/swagger-config"
    };

    String[] jump = {
        "/login",
        "/register",
        "/forgotPassword",
        "/logout",
    };

    List<String> jumpList = Arrays.asList(jump);
    List<String> AUTH_WHITELIST_List = Arrays.asList(AUTH_WHITELIST);

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest req,
        jakarta.servlet.http.HttpServletResponse res, jakarta.servlet.FilterChain chain)
        throws jakarta.servlet.ServletException, IOException {
  
            //判斷是否跳過驗證
            if(!jumpList.contains(req.getServletPath()) && !isPathInList(req.getServletPath(), AUTH_WHITELIST_List))
                {

                //獲取token的頭
                System.out.println(req.getServletPath().toString());
                String authorHeader =  req.getHeader(AUTHORIZATION);
                String bearer ="Bearer ";
                //以jjwt驗證token，只要驗證成功就放行
                //驗證失敗會拋exception，直接將錯誤訊息傳回
                //token是否Bearer開頭
                if(authorHeader!= null && authorHeader.startsWith(bearer)){
                    try{

                    String token = authorHeader.substring(bearer.length());

                    Claims claims = Jwts.parser().setSigningKey("ray891108")
                    .parseClaimsJws(token).getBody();

                    System.out.println("JWT payload:"+claims.toString());

                    chain.doFilter(req, res);
                    
                    }catch(Exception e){
                        System.err.println("Error : "+e);
                        res.setStatus(FORBIDDEN.value());
                        
                        Map<String, String> err = new HashMap<>();
                        err.put("jwt_err", e.getMessage());
                        res.setContentType(APPLICATION_JSON_VALUE);
                        new ObjectMapper().writeValue(res.getOutputStream(), err);
                    }
                }else{
                    res.setStatus(UNAUTHORIZED.value());
                }
            }else{
                chain.doFilter(req, res);
            }
    }

}
