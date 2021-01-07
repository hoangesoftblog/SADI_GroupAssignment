package com.demo.model;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Indexed
@Table(indexes = {
        @Index(name = "ShopeeProductIndex", columnList = "shopeeID, shopeeShopID"),
        @Index(name = "productType", columnList = "type")
})
public class Product implements Serializable {

    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    @Field
    private String name;

    // orphanRemoval means when delete Product, automatically delete all PriceHistories


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PriceHistory> histories = new ArrayList<>();

    @Transient
    private static final int max_length = 65535;

    @Column(length = max_length)
    private String description;

    // @Column(columnDefinition = "TEXT")
    @Column
    //Product page for Shopee can be transient,
    // since it can be created with format: https://shopee.vn/product/<shopeeShopID>/<shopeeID>
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
    @Field
    private String type;

    @Column
    private String UUID;

    @Column
    private Long currentPrice;

    // Lowest and Highest price ever
    @Column
    private Long lowestPrice;

    @Column
    private Long highestPrice;


    // Methods

    public Long getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Long highestPrice) {
        this.highestPrice = highestPrice;
    }

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
        this.description = description.substring(0, Math.min(max_length + 1, description.length()));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product() {
    }

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
        this.histories = Optional.ofNullable(histories).orElse(new ArrayList<>());
    }

    public void addHistories(List<PriceHistory> histories) {
        this.histories.addAll(histories);
    }

    @Override
    public String toString() {
        return (String.format("Product: [name - %s,\nid: %s \ncategory: %s]", name, id, categories));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        else if (obj == null) return false;
        else if (!(obj instanceof Product)) return false;
        else {
            Product p = (Product) obj;
            return p.shopeeID.equals(this.shopeeID) && p.shopeeShopID.equals(this.shopeeShopID);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopeeID, shopeeShopID);
    }



    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(name = "products", columnList = "product_id"),
                    @Index(name = "categories", columnList = "category_id"),
            }
    )
    private Set<Category> categories;

//    @JsonManagedReference
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }



    @Override
    public Product clone() {
        Product p;
        try {
            p = (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Automatic copy of Product failed. Manual copy instead\n");

            p = new Product();
            p.setId(this.id);
            p.setName(this.name);
            p.setHistories(this.histories);
            p.setDescription(this.description);
            p.setDeleted(this.isDeleted);
            p.setHidden(this.isHidden);
            p.setHighestPrice(this.highestPrice);
            p.setCurrentPrice(this.currentPrice);
            p.setLowestPrice(this.lowestPrice);
            p.setBrand(this.brand);
            p.setShopeeID(this.shopeeID);
            p.setShopeeShopID(this.shopeeShopID);
            p.setType(this.type);
            p.setUrl(this.url);
            p.setUUID(this.UUID);
        }
        return p;
    }
}
