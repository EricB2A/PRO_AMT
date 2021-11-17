package com.example.amt_demo.config;

import com.example.amt_demo.auth.AuthProvider;
import com.example.amt_demo.auth.JwtRequestFilter;
import com.example.amt_demo.service.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

// based on https://github.com/eugenp/tutorials/blob/master/spring-security-modules/spring-security-web-boot-1
@Configuration()
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final WebApplicationContext applicationContext;
    private final Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);
    private final JwtRequestFilter jwtRequestFilter;
    private  CustomUserDetailsService userDetailsService;

    public SpringSecurityConfig(WebApplicationContext applicationContext, JwtRequestFilter jwtRequestFilter) {
        this.applicationContext = applicationContext;
        this.jwtRequestFilter = jwtRequestFilter;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @PostConstruct
    public void completeSetup() {
        userDetailsService = applicationContext.getBean(CustomUserDetailsService.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new AuthProvider(userDetailsService));
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/carpets/new", "/admin/**").permitAll()
            .antMatchers("/", "accueil", "/login*", "/images/**", "/css/**", "/js/**", "/carpets/*").permitAll()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}