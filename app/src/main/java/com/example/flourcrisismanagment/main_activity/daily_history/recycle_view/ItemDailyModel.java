package com.example.flourcrisismanagment.main_activity.daily_history.recycle_view;

public class ItemDailyModel {
    String name,phoneNo,gender,cardNo,address,date,city,price;

    public String getName() {
        return name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getCity() {
        return city;
    }

    public String getPrice() {
        return price;
    }

    public ItemDailyModel(String name, String phoneNo, String gender, String cardNo, String address, String date, String city, String price) {
        this.name = name;
        this.phoneNo = phoneNo;
        this.gender = gender;
        this.cardNo = cardNo;
        this.address = address;
        this.date = date;
        this.city = city;
        this.price = price;
    }


}
