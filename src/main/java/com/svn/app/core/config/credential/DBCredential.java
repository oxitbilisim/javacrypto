package com.svn.app.core.config.credential;

import com.svn.app.core.config.security.CustomUserDetails;
import com.svn.app.core.entity.User;
import com.svn.app.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DBCredential implements ICredential {
    @Autowired
    UserRepository userRepository;

    @Override
    public CustomUserDetails getUserByUserName(String userName) throws UsernameNotFoundException {
        User myUser = userRepository.findByEmailIgnoreCase(userName);
        if (myUser == null) throw new UsernameNotFoundException(userName);

        boolean enabled = myUser.isEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        CustomUserDetails user = new CustomUserDetails(myUser,new ArrayList<>(myUser.getRoles()), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked);
        return user;
    }
}
