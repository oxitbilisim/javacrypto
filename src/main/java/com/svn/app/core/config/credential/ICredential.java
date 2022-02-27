package com.svn.app.core.config.credential;

import com.svn.app.core.config.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface ICredential {

    public CustomUserDetails getUserByUserName(String userName) throws UsernameNotFoundException;

}
