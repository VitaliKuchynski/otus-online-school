package com.otus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final Encoder encoder;

    public SecurityConfig(UserDetailsService userDetailsService, Encoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder.passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/role/create").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/role/*").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/student/save").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/student/*").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/employee/save").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/employee/assign/role").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/employee/*").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
