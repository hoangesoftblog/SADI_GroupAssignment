package com.demo.model;

import com.demo.config.AppConfig;
import com.fasterxml.jackson.annotation.*;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Index;
import java.io.Serializable;
import java.util.*;

@Entity
@Indexed
@Table(indexes = {
        @Index(name = "ShopeeProductIndex", columnList = "shopeeID, shopeeShopID"),
        @Index(name = "productBrand", columnList = "brand"),

})
@Where(clause = "is_deleted = false and is_hidden = false and stock > 0")
public class Product implements Serializable {
    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Field(termVector = TermVector.YES)
    private String name;

    // orphanRemoval means when delete Product, automatically delete all PriceHistories


    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PriceHistory> histories = new ArrayList<>();

    // @Column(columnDefinition = "TEXT")
    @Column(length = AppConfig.CONSTANT.Product.max_length)
    private String description;

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
    @Field(analyze = Analyze.NO)
    private String brand;

    @Column
    private String type;

    @Column
    private String UUID;

    @Column
    @Field
    @SortableField
    private Long currentPrice;

    // Lowest and Highest price ever
    @Column
    @Field
    @SortableField
    private Long lowestPrice;

    @Column
    @Field
    @SortableField
    private Long highestPrice;

    @Field
    @SortableField
    @Column
    private Double rating;

    @Column
    private Long stock;

    @Column
    // Status 8 is Hidden
    private int status;

    @Column
    private String productAvatar;


    @Fetch(FetchMode.JOIN)
    @ElementCollection
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    // https://cf.shopee.vn/file/<image_id>
    private List<String> images;


    // Methods


    public String getProductAvatar() {
        return productAvatar;
    }

    public void setProductAvatar(String productAvatar) {
        this.productAvatar = productAvatar;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = Optional.ofNullable(rating).orElse(-1D);
    }

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
//        System.out.println(description);
//        int min_length = Math.min(AppConfig.CONSTANT.Product.max_length + 1, description.length());
//        System.out.println(min_length);
        this.description = description;
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
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"),
            indexes = {
                    @Index(name = "products", columnList = "product_id"),
                    @Index(name = "categories", columnList = "category_id"),
            }
    )
    @IndexedEmbedded
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
