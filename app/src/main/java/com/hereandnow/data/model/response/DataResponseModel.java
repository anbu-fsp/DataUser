package com.hereandnow.data.model.response;

import java.util.List;

public class DataResponseModel {

    private int page;

    private int per_page;

    private int total;

    private int total_pages;

    private List<Data> data;

    private Support support;

    public void setPage(int page){
        this.page = page;
    }
    public int getPage(){
        return this.page;
    }
    public void setPer_page(int per_page){
        this.per_page = per_page;
    }
    public int getPer_page(){
        return this.per_page;
    }
    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setTotal_pages(int total_pages){
        this.total_pages = total_pages;
    }
    public int getTotal_pages(){
        return this.total_pages;
    }
    public void setData(List<Data> data){
        this.data = data;
    }
    public List<Data> getData(){
        return this.data;
    }
    public void setSupport(Support support){
        this.support = support;
    }
    public Support getSupport(){
        return this.support;
    }

    public class Data
    {
        private int id;

        private String email;

        private String first_name;

        private String last_name;

        private String avatar;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setEmail(String email){
            this.email = email;
        }
        public String getEmail(){
            return this.email;
        }
        public void setFirst_name(String first_name){
            this.first_name = first_name;
        }
        public String getFirst_name(){
            return this.first_name;
        }
        public void setLast_name(String last_name){
            this.last_name = last_name;
        }
        public String getLast_name(){
            return this.last_name;
        }
        public void setAvatar(String avatar){
            this.avatar = avatar;
        }
        public String getAvatar(){
            return this.avatar;
        }
    }

    public class Support
    {
        private String url;

        private String text;

        public void setUrl(String url){
            this.url = url;
        }
        public String getUrl(){
            return this.url;
        }
        public void setText(String text){
            this.text = text;
        }
        public String getText(){
            return this.text;
        }
    }
}
