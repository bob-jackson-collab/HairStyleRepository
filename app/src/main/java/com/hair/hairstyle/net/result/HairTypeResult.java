package com.hair.hairstyle.net.result;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by yunshan on 17/9/4.
 */

public class HairTypeResult {

    private int limit;
    private List<HairstyleListBean> hairstyle_list;

    public static HairTypeResult objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), HairTypeResult.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<HairstyleListBean> getHairstyle_list() {
        return hairstyle_list;
    }

    public void setHairstyle_list(List<HairstyleListBean> hairstyle_list) {
        this.hairstyle_list = hairstyle_list;
    }

    public static class HairstyleListBean {
        /**
         * id : 59
         * name : 从赛场到职场
         * hairsytle_label : 职场必备
         * english_sign : BUSINESS
         * like_num : 3
         * imgurl : http://cdn.xingkeduo.com/imgs/59/25.jpg?v=7
         */

        private String id;
        private String name;
        private String hairsytle_label;
        private String english_sign;
        private String like_num;
        private String imgurl;

        public static HairstyleListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), HairstyleListBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHairsytle_label() {
            return hairsytle_label;
        }

        public void setHairsytle_label(String hairsytle_label) {
            this.hairsytle_label = hairsytle_label;
        }

        public String getEnglish_sign() {
            return english_sign;
        }

        public void setEnglish_sign(String english_sign) {
            this.english_sign = english_sign;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }
}
