package com.springdemo.config;

import com.springdemo.securities.UserSecurityDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfigure {
    @Autowired
    private AuthenticationEntryPointConfig authenticationEntryPointConfig;

    @Autowired
    private UserSecurityDetail userSecurityDetail;

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userSecurityDetail);
        authenticationProvider.setPasswordEncoder(applicationConfig.passwordEncoder());
        return authenticationProvider;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityDetail).passwordEncoder(applicationConfig.passwordEncoder());
    }

    @Bean
    public JWTTokenFilter jwtTokenFilter() {
        return new JWTTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Session Management
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

        // Handling exception authentication entry point
        http = http.exceptionHandling().authenticationEntryPoint(authenticationEntryPointConfig).and();

        // Endpoints permition
        /*
         * Un-comment kalo pake basic authentication
        http.authorizeHttpRequests().requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated().and().httpBasic();
         */

        http.authorizeHttpRequests().requestMatchers("/").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .anyRequest().authenticated();

        // Set authentication provider
        http.authenticationProvider(authenticationProvider());

        // Set authentication filter
        // Un-comment kalo pake basic authentication
        // http.addFilterBefore(new CorsConfig(), BasicAuthenticationFilter.class);
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
