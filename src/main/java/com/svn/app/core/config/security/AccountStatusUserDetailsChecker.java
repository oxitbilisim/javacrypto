package com.svn.app.core.config.security;

import com.svn.app.core.entity.User;
import com.svn.app.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AccountStatusUserDetailsChecker implements UserDetailsChecker {

    protected final MessageSourceAccessor messages = SpringSecurityMessageSource
            .getAccessor();
    @Autowired
    UserRepository userRepository;
    @Override
    public void check(UserDetails user) {
        User myUser = userRepository.findByEmailIgnoreCase(user.getUsername());
        Locale locale = myUser.getUserProfile().getLocale();
        if (!user.isAccountNonLocked()) {
            throw new LockedException(messages.getMessage(
                    "message.user_checker.user_is_lock",locale));
        }

        if (!user.isEnabled()) {
            throw new DisabledException(messages.getMessage(
                    "message.user_checker.user_is_disabled",locale));
        }

        if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(
                    messages.getMessage("AccountStatusUserDetailsChecker.expired",locale));
        }

        if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.credentialsExpired",locale));
        }
    }
}
