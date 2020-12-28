package com.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(indexes = @Index(name = "ShopeeProductIndex", columnList = "ShopeeID, ShopeeShopID"))
public class Product implements Serializable {
    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    // orphanRemoval means when delete Product, automatically delete all PriceHistories
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<PriceHistory> histories = new ArrayList<>();

    @Column(length = 65535)
    private String description;

//    @Column(columnDefinition = "TEXT")
    @Column
    private String url;

    @Column
    private Boolean isHidden = false;

    @Column
    private Boolean isDeleted = false;

    @Column
    private Long shopeeID;

    @Column
    private Long shopeeShopID;

    @Column
    private String brand;

    @Column
    private String type;

    @Column
    private String UUID;

    @Column
    private Long currentPrice;

    @Column
    private Long lowestPrice;


    // Methods


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product(Long id) {
        this.id = id;
    }

    public Long getShopeeID() {
        return shopeeID;
    }

    public void setShopeeID(Long shopeeID) {
        this.shopeeID = shopeeID;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public long getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Long currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Long getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(Long lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Long getShopeeShopID() {
        return shopeeShopID;
    }

    public void setShopeeShopID(Long shopeeShopID) {
        this.shopeeShopID = shopeeShopID;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = Optional.ofNullable(deleted).orElse(Boolean.FALSE);
    }

    public Boolean isHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        this.isHidden = Optional.ofNullable(hidden).orElse(Boolean.FALSE);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonManagedReference
    public List<PriceHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<PriceHistory> histories) {
        this.histories = histories;
    }

    public void addHistories(List<PriceHistory> histories) {
        this.histories.addAll(histories);
    }

    @Override
    public String toString() {
        return (String.format("Product: [name - %s,\ndescription: %s, \nURL: %s", name, description, url));
    }
}
