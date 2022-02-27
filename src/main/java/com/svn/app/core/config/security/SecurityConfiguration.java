package com.svn.app.core.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //@Autowired
    //private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    //@Autowired
    //private CustomAuthenticationFailurHandler customAuthenticationFailurHandler;
    //@Autowired
    //private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    };
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin().and().authorizeRequests()
                .antMatchers("/admin/login").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/account/**").authenticated()
                .anyRequest().permitAll().and()
                .formLogin()
                .loginPage("/admin/login").loginProcessingUrl("/admin/login-process")

                //.successHandler(customAuthenticationSuccessHandler)
                //.failureHandler(customAuthenticationFailurHandler)
                .and()
                //.logout().logoutUrl("/logout")
                //.logoutSuccessHandler(customLogoutSuccessHandler)
        ;
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/admin/assets/**");

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
