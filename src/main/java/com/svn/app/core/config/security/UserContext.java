package com.svn.app.core.config.security;


import com.svn.app.core.entity.User;

public class UserContext {
    private static ThreadLocal<User> activeUser = new ThreadLocal<>();

    public static User getActiveUser() {
        return activeUser.get();
    }

    public static void setActiveUser(User user) {
        activeUser.set(user);
    }
}
