package com.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PriceHistory implements Serializable {
    private static final Long serialVersionUID = Double.valueOf(Math.E * Math.pow(10, 6)).longValue();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    @Column
    public Long price;

    @Temporal(value = TemporalType.TIMESTAMP)
    public java.util.Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    public Product product;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonBackReference
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
