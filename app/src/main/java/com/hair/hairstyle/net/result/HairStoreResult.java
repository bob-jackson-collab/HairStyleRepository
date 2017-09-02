package com.hair.hairstyle.net.result;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by yunshan on 17/8/31.
 */

public class HairStoreResult {

    private int limit;
    private String status;
    private String select_city;
    private List<StoreListBean> store_list;
    private List<String> banner_urls;
    private List<CityBean> city;

    public static HairStoreResult objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), HairStoreResult.class);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelect_city() {
        return select_city;
    }

    public void setSelect_city(String select_city) {
        this.select_city = select_city;
    }

    public List<StoreListBean> getStore_list() {
        return store_list;
    }

    public void setStore_list(List<StoreListBean> store_list) {
        this.store_list = store_list;
    }

    public List<String> getBanner_urls() {
        return banner_urls;
    }

    public void setBanner_urls(List<String> banner_urls) {
        this.banner_urls = banner_urls;
    }

    public List<CityBean> getCity() {
        return city;
    }

    public void setCity(List<CityBean> city) {
        this.city = city;
    }

    public static class StoreListBean {
        /**
         * id : 95
         * status : 0
         * address : 北京市昌平区北清路1号楼永旺国际商城1层超市家品区T-120
         * area_name : 昌平区
         * market_address : 永旺国际商城1层
         * distance : 4.7KM
         * currenttime : 1504152180
         * starttime : 1504141200
         * endtime : 1504186200
         * is_sell_product : 1
         * thumbnail_url : http://cdn.xingkeduo.com/image/coming_soon_new.jpg
         * name : 昌平永旺店
         */

        private String id;
        private String status;
        private String address;
        private String area_name;
        private String market_address;
        private String distance;
        private int currenttime;
        private int starttime;
        private int endtime;
        private String is_sell_product;
        private String thumbnail_url;
        private String name;

        public static StoreListBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), StoreListBean.class);
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getMarket_address() {
            return market_address;
        }

        public void setMarket_address(String market_address) {
            this.market_address = market_address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getCurrenttime() {
            return currenttime;
        }

        public void setCurrenttime(int currenttime) {
            this.currenttime = currenttime;
        }

        public int getStarttime() {
            return starttime;
        }

        public void setStarttime(int starttime) {
            this.starttime = starttime;
        }

        public int getEndtime() {
            return endtime;
        }

        public void setEndtime(int endtime) {
            this.endtime = endtime;
        }

        public String getIs_sell_product() {
            return is_sell_product;
        }

        public void setIs_sell_product(String is_sell_product) {
            this.is_sell_product = is_sell_product;
        }

        public String getThumbnail_url() {
            return thumbnail_url;
        }

        public void setThumbnail_url(String thumbnail_url) {
            this.thumbnail_url = thumbnail_url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class CityBean {
        /**
         * f_id : 52
         * f_name : 北京
         */

        private String f_id;
        private String f_name;

        public static CityBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), CityBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public String getF_id() {
            return f_id;
        }

        public void setF_id(String f_id) {
            this.f_id = f_id;
        }

        public String getF_name() {
            return f_name;
        }

        public void setF_name(String f_name) {
            this.f_name = f_name;
        }
    }
}
