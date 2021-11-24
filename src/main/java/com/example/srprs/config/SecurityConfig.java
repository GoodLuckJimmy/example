package com.example.srprs.config;

import com.example.srprs.config.filter.ApiTokenCheckFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String CUSTOMER_ROLE = "CUSTOMER";
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/admins/login").permitAll()
                .antMatchers("/admins/**/*").hasRole(ADMIN_ROLE)

                .antMatchers("/booking/histories/*").hasRole(CUSTOMER_ROLE)
                .antMatchers(HttpMethod.DELETE, "/booking").hasRole(CUSTOMER_ROLE)
                .antMatchers(HttpMethod.POST, "/booking").hasRole(CUSTOMER_ROLE)
                .antMatchers(HttpMethod.POST, "/booking/schedule").hasRole(ADMIN_ROLE)

                .antMatchers("/members/login/*").permitAll()
                .antMatchers("/members/login").permitAll()
                .antMatchers("/members/klogin").permitAll() // todo: 삭제 예정
                .antMatchers(HttpMethod.GET, "/members").hasRole(CUSTOMER_ROLE)
                .antMatchers(HttpMethod.PUT, "/members").hasRole(CUSTOMER_ROLE)
                .antMatchers(HttpMethod.POST, "/members").permitAll()

                .antMatchers(HttpMethod.GET, "/schedules/*").hasRole(CUSTOMER_ROLE)

                .antMatchers("/docs/**").permitAll()
                .antMatchers("/v3/api-docs").permitAll()

                .antMatchers( "/payments/**/*").permitAll()
                .antMatchers( "/pg/**/*").permitAll()

                .antMatchers(HttpMethod.GET, "/items/calendars/**").permitAll()

                .anyRequest().authenticated();

        http
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .formLogin().disable();

        http.addFilterBefore(apiTokenCheckFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApiTokenCheckFilter apiTokenCheckFilter() {
        return new ApiTokenCheckFilter(new JwtTokenProvider());
    }
}
