package com.demo.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
//@Table(name = "cities")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    // orphanRemoval means when delete Product, automatically delete all PriceHistories
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<PriceHistory> histories = new ArrayList<>();

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column
    private Boolean isHidden = false;

    @Column
    private Boolean isDeleted = false;

//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Comment> comments;

    @Column
    private String UUID;

    @Column
    private Long basePrice;

    @Column
    private Long lowestPrice;


    // Methods

    public long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
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

//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }

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
