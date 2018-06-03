package com.demo.cmnc.fragments.dummy;

/**
 * Created by zhangfan on 2018/5/20.
 */

public class Product{

    private String title;
    private String from;
    private boolean isRight;
    private int grade;
    private String price;
    private String pic;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String  getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String  getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }



}