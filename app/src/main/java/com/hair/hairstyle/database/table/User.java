package com.hair.hairstyle.database.table;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yunshan on 17/7/28.
 */

@DatabaseTable(tableName = "hair_user")
public class User {

    @DatabaseField(id = true, columnName = "Id")
    private int id;
    @DatabaseField(columnName = "userName")
    private String userName;
    @DatabaseField(columnName = "userPassWd")
    private String userPassWd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassWd() {
        return userPassWd;
    }

    public void setUserPassWd(String userPassWd) {
        this.userPassWd = userPassWd;
    }
}
