/**
 * @team AMT - Silkyroad
 * @authors Bousbaa Eric, Fusi Noah, Goujgali Ilias, Maillefer Dalia, Teofanovic Stefan
 * @file SpringSecurityConfig.java
 *
 * @brief TODO
 */

package com.example.amt_demo.config;

import com.example.amt_demo.auth.AuthProvider;
import com.example.amt_demo.auth.JwtRequestFilter;
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

    /**
     *
     * @param authProvider
     * @param applicationContext
     * @param jwtRequestFilter
     */
    @Autowired
    public SpringSecurityConfig(AuthProvider authProvider, WebApplicationContext applicationContext, JwtRequestFilter jwtRequestFilter) {
        this.authProvider = authProvider;
        this.applicationContext = applicationContext;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

       /**
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);

    }

    /**
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("admin")
            .antMatchers("/signout", "/payement/**", "/purchase").authenticated()
            .antMatchers("/login","/signup").anonymous()
            .antMatchers("/", "/image/**", "/carpets", "/accueil", "/images/**", "/css/**", "/js/**", "/catalog/**", "/cart/**","/carpet-photos/**").permitAll()
            .anyRequest().denyAll(); // Grant access for endpoint to nobody
        http.csrf().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}