package com.hair.hairstyle.test;

import io.reactivex.Observable;

/**
 * Created by yunshan on 17/8/21.
 */

public class ProxyTest {

    public static void main(String[] args) {

//        UserManagerProxy proxy = new UserManagerProxy(new UserManagerImp());
//
//        proxy.addUser();
//
//        UserManager manager = (UserManager) new UserManagerHandler().getProxyInstance(new UserManagerImp());
//        BookManager manager1 = (BookManager) new UserManagerHandler().getProxyInstance(new BookManagerImp());
//        manager.addUser();
//        manager1.addBook();


        Observable.just("11").map(s -> {
            return Integer.parseInt(s);
        }).subscribe(integer -> {
            System.out.println(integer + "");
        });
    }
}
