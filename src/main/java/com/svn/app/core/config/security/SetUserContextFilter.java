package com.svn.app.core.config.security;

import com.svn.app.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component
public class SetUserContextFilter extends GenericFilterBean {
    @Autowired
    private final UserRepository userRepository;

    public SetUserContextFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2Authentication) {
//            if (UserContext.getActiveUser() == null) {
                OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
                CustomUserDetails user = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
                UserContext.setActiveUser(userRepository.findByEmailIgnoreCase(user.getUsername()));
//            }
        }
        chain.doFilter(request, response);
    }
}
