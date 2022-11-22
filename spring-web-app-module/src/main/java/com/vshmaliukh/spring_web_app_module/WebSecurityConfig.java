package com.vshmaliukh.spring_web_app_module;

import com.vshmaliukh.spring_shelf_core.shelf.user.CustomOAuth2UserService;
import com.vshmaliukh.spring_shelf_core.shelf.user.UserGoogleAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { //todo fix

    @Autowired
    public WebSecurityConfig(CustomOAuth2UserService oauthUserService,
                             UserGoogleAuthService googleAuthService) {
        this.oauthUserService = oauthUserService;
        this.googleAuthService = googleAuthService;
    }

    private final CustomOAuth2UserService oauthUserService;
    private final UserGoogleAuthService googleAuthService;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().antMatcher("/**").authorizeRequests()
                .antMatchers("/login/oauth2/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
//                .loginPage("/log_in")
                .permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/log_in")
        ;
    }


}

