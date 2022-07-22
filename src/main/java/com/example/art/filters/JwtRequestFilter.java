package com.example.art.filters;

import com.example.art.dto.response.BaseResponse;
import com.example.art.utils.JsonUtils;
import com.example.art.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            final String authHeader = request.getHeader("Authorization");

            String userName = null;
            String jwt = null;

            if(authHeader != null && authHeader.startsWith("Bearer ")){
                jwt = authHeader.substring(7);
                userName = jwtUtil.extractUsername(jwt);
            }

            logger.debug("Authenticating user with username= "+userName);

            if(userName!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                if(jwtUtil.validateToken(jwt,userDetails)){
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(token);
                }
            }
        }
        catch (ExpiredJwtException ex){
            BaseResponse errorResponse = BaseResponse.builder()
                    .responseMsg("Login expired, login again!")
                    .status(HttpStatus.BAD_REQUEST)
                    .build();

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(JsonUtils.convertObjectToJsonString(errorResponse));
            return;
        }
        catch (JwtException ex){
            BaseResponse errorResponse = BaseResponse.builder()
                                .responseMsg("error in validating token, "+ex.getLocalizedMessage())
                                .status(HttpStatus.BAD_REQUEST)
                                .build();

            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(JsonUtils.convertObjectToJsonString(errorResponse));
            return;
        }

        filterChain.doFilter(request,response);
    }

}
