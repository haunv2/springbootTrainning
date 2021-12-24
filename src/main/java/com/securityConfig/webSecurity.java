package com.securityConfig;

import com.jwtConfig.jwtFilter.JwtFilterAuthentication;
import com.jwtConfig.jwtUtil.JwtTokenUtil;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebSecurity
public class webSecurity extends WebSecurityConfigurerAdapter {
    private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/user/login"),
            new AntPathRequestMatcher("/swagger-ui.html"),
            new RegexRequestMatcher("(\\/api\\/)[a-z]+\\/", HttpMethod.GET.name()),
            new RegexRequestMatcher("(\\/api\\/)[a-z]+\\/getAll/[?\\w\\d=&]*", HttpMethod.GET.name()),
            new RegexRequestMatcher("(\\/api\\/)[a-z]+\\/getTotalPage", HttpMethod.GET.name()),
            new RegexRequestMatcher("(\\/api\\/)[a-z]+\\/filters", HttpMethod.POST.name())
    );

    private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

    private JwtFilterAuthentication jwtFilterAuthentication;

    @Autowired
    public webSecurity( JwtTokenUtil jwtTokenUtil, UserService userDetailService) {
        this.jwtFilterAuthentication = new JwtFilterAuthentication(PROTECTED_URLS);
        this.jwtFilterAuthentication.setJwtTokenUtil(jwtTokenUtil);
        this.jwtFilterAuthentication.setJwtUserDetailsService((UserDetailsService) userDetailService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PUBLIC_URLS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO Auto-generated method stub
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().requestMatchers(PROTECTED_URLS).authenticated();
        http.exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                .addFilterBefore(jwtFilterAuthentication, AnonymousAuthenticationFilter.class);

        http.csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

}
