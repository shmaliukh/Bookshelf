package com.vshmaliukh.spring_web_app_module;

import com.vshmaliukh.spring_shelf_core.shelf.user.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //todo fix

    @Autowired
    public WebSecurityConfig(CustomOAuth2UserService oauthUserService) {
        this.oauthUserService = oauthUserService;
    }

    private final CustomOAuth2UserService oauthUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/log_in", "/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/log_in")
                .userInfoEndpoint()
                .userService(oauthUserService);
    }

}