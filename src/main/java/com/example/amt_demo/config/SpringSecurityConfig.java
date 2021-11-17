package com.example.amt_demo.config;

import com.example.amt_demo.auth.AuthProvider;
import com.example.amt_demo.auth.JwtRequestFilter;
import com.example.amt_demo.service.CustomUserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration()
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthProvider authProvider;
    private final WebApplicationContext applicationContext;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtRequestFilter jwtRequestFilter;
    private CustomUserDetailsServiceImpl userDetailsService;


    @Autowired
    public SpringSecurityConfig(AuthProvider authProvider, WebApplicationContext applicationContext, JwtRequestFilter jwtRequestFilter) {
        this.authProvider = authProvider;
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
        userDetailsService = applicationContext.getBean(CustomUserDetailsServiceImpl.class);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
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