package com.demo.manage;

/**
 * Created by zhangfan on 2018/5/20.
 */

public class Farm {

    private String title;
    private String from;
    private boolean isRight;
    private int grade;
    private String price;
    private int product_grade;
    private int place_grade;

    private int trip_grade;

    private int service_grade;

    private String pic;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProduct_grade() {
        return product_grade;
    }

    public void setProduct_grade(int product_grade) {
        this.product_grade = product_grade;
    }
    public int getPlace_grade () {
        return place_grade;
    }

    public void setPlace_grade(int place_grade) {
        this.place_grade = place_grade;
    }
    public int getTrip_grade () {
        return trip_grade;
    }
    public void setTrip_grade(int trip_grade) {
        this.trip_grade = trip_grade;
    }
    public int getService_grade () {
        return service_grade;
    }
    public void setService_grade(int service_grade) {
        this.service_grade = service_grade;
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