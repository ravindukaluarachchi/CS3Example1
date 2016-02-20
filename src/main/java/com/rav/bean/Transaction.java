/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.rav.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ravklk
 */
public class Transaction {
    private Integer id;
    private String date;
    private String time;
    private String user;
    private String item;
    private Integer quantity;
    private Float   price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Transaction() {
        this.date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        this.time = new SimpleDateFormat("hh:mm aa").format(new Date());
    }

    public Transaction(Integer id, String user, String item, Integer quantity, Float price) {
        this();
        this.id = id;
        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Transaction{" + "id=" + id + ", date=" + date + ", time=" + time + ", user=" + user + ", item=" + item + ", quantity=" + quantity + ", price=" + price + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.user != null ? this.user.hashCode() : 0);
        hash = 71 * hash + (this.item != null ? this.item.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if ((this.user == null) ? (other.user != null) : !this.user.equals(other.user)) {
            return false;
        }
        if ((this.item == null) ? (other.item != null) : !this.item.equals(other.item)) {
            return false;
        }
        return true;
    }
    
    
    
}
