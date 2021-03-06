package com.demo.DTO;

import com.demo.model.Category;
import com.demo.model.PriceHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDTO implements Serializable {

    public static class CategoryForProductDTO implements Serializable {
        public Long id;
        public String name;
        public String img;
        public Long shopeeCategoryID;

        private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public Long getShopeeCategoryID() {
            return shopeeCategoryID;
        }

        public void setShopeeCategoryID(Long shopeeCategoryID) {
            this.shopeeCategoryID = shopeeCategoryID;
        }
    }

    public Long id;
    public String name;
    public List<PriceHistoryDTO> histories = new ArrayList<>();
    public String description;
    public String url;
    public Boolean isHidden = false;
    public Boolean isDeleted = false;
    public Long shopeeID;
    public Long shopeeShopID;
    public String brand;
    public String type;
    public String UUID;
    public Long currentPrice;
    public Long lowestPrice;
    public Long highestPrice;
    public Double rating;
    public Long stock;
    public int status;
    public List<CategoryForProductDTO> categories;
    public List<String> images;
    public String productAvatar;

    public String getProductAvatar() {
        return productAvatar;
    }

    public void setProductAvatar(String productAvatar) {
        this.productAvatar = productAvatar;
    }

    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

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

    public List<PriceHistoryDTO> getHistories() {
        return histories;
    }

    public void setHistories(List<PriceHistoryDTO> histories) {
        this.histories = histories;
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

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Long getShopeeID() {
        return shopeeID;
    }

    public void setShopeeID(Long shopeeID) {
        this.shopeeID = shopeeID;
    }

    public Long getShopeeShopID() {
        return shopeeShopID;
    }

    public void setShopeeShopID(Long shopeeShopID) {
        this.shopeeShopID = shopeeShopID;
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

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Long getCurrentPrice() {
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

    public Long getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(Long highestPrice) {
        this.highestPrice = highestPrice;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<CategoryForProductDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryForProductDTO> categories) {
        this.categories = categories;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
