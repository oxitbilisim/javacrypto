package com.svn.app.core.controller;

import com.svn.app.core.config.security.UserContext;
import com.svn.app.core.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {


    @PostMapping("/current-user")
    public User getCurrentUser(){
        return UserContext.getActiveUser();
    }
}
