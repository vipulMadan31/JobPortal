package com.example.demo.security;

import com.example.demo.service.CustomUserDetailsService;
import com.example.demo.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final String [] publicUrl = {"/register", "/register/**", "/save", "/save/**",
            "/login", "/login/**","/error", "/saveJobSeeker", "/saveRecruiter"};



    @Autowired
    AuthenticationSuccessHandler customSuccessHandler;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private PasswordConfig passwordConfig;

    public SecurityConfig(AuthenticationSuccessHandler customSuccessHandler, CustomUserDetailsService customUserDetailsService, PasswordConfig passwordConfig) {
        this.customSuccessHandler = customSuccessHandler;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordConfig = passwordConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.authorizeHttpRequests(auth-> {
           auth.requestMatchers(publicUrl).permitAll();
           auth.anyRequest().authenticated();

        });

        httpSecurity.formLogin(form-> {
            form.loginPage("/login").permitAll()
                    .loginProcessingUrl("/authenticateTheUser")   // <--- add this
                    .permitAll()
                    .successHandler(customSuccessHandler);
                    })
                .logout(logout-> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/login");
                }).cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());
        //this gives us /logout on its own, we dont need to make it
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordConfig.passwordEncoder());
        return authenticationProvider;
    }

}
