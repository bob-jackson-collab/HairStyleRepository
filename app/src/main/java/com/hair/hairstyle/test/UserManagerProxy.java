package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/21.
 */

public class UserManagerProxy implements UserManager {

    private UserManager userManager;

    public UserManagerProxy(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void addUser() {
        System.out.println("start");
        userManager.addUser();
        System.out.println("end");
    }

    @Override
    public void deleteUser() {
        userManager.deleteUser();
    }
}
