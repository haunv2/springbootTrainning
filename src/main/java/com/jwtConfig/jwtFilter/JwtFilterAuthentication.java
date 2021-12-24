package com.jwtConfig.jwtFilter;

import com.jwtConfig.jwtUtil.JwtTokenUtil;
import com.model.ResponseData;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilterAuthentication extends OncePerRequestFilter {

    private UserDetailsService userService;

    private JwtTokenUtil jwtTokenUtil;

    private RequestMatcher authenticateUrl;

    public JwtFilterAuthentication(RequestMatcher requiresAuthenticationRequestMatcher) {
        this.authenticateUrl = requiresAuthenticationRequestMatcher;
    }

    public void setJwtUserDetailsService(UserDetailsService userService) {
        this.userService = userService;
    }

    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("jwt filter load");
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }

            if(username != null){
                // Once we get the token validate it.
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userService.loadUserByUsername(username);

                    // if token is valid configure Spring Security to manually set
                    // authentication
                    if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // After setting the Authentication in the context, we specify
                        // that the current user is authenticated. So it passes the
                        // Spring Security Configurations successfully.
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        System.out.println("filter roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
                        filterChain.doFilter(request, response);
                    }
                    else{
                        invalidToken(response);
                    }
                }
                else{
                    invalidToken(response);
                }
            }else{
                invalidToken(response);
            }
        } else {
            logger.warn("JWT does not begin with Bearer String");

            ResponseData res = new ResponseData("error", "miss authorization header");
            res.setCode(HttpStatus.FORBIDDEN);
            response.setStatus(res.getCode().value());
            response.getWriter().println(res.toJson());
        }


    }
    public void invalidToken(HttpServletResponse response) throws IOException {
        ResponseData res = new ResponseData("error", "auth token invalid or mailware");
        res.setCode(HttpStatus.UNAUTHORIZED);
        response.setStatus(res.getCode().value());
        response.getWriter().println(res.toJson());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (this.authenticateUrl.matches(request))
            return false;
        else return true;
    }
}
