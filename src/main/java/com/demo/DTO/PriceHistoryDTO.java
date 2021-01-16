package com.demo.DTO;

import com.demo.model.Product;

import java.io.Serializable;
import java.util.Date;

public class PriceHistoryDTO implements Serializable {
    public java.util.Date date;
    public Long price;
//    public Long id;

    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}
