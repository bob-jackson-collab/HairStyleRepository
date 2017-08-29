package com.hair.hairstyle.test;

/**
 * Created by yunshan on 17/8/21.
 */

public class UserManagerImp implements UserManager {

    @Override
    public void addUser() {
        System.out.println("add User");
    }

    @Override
    public void deleteUser() {
        System.out.println("delete User");
    }
}
