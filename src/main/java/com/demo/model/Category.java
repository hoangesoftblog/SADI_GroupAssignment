package com.demo.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(indexes = {
        @Index(name = "Shopee_Category", columnList = "shopeeCategoryID"),
        @Index(name = "category_name", columnList = "name")
})
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category implements Serializable {

    private static final Long serialVersionUID = Double.valueOf(Math.PI * Math.pow(10, 6)).longValue();

    @Transient
    private Long id;

    @Column
    @Field(analyze = Analyze.NO)
    private String name;

    @Column
    private int img;

    @Id
    private Long shopeeCategoryID;

    
    public Long getShopeeCategoryID() {
        return shopeeCategoryID;
    }

    public void setShopeeCategoryID(Long shopeeCategoryID) {
        this.shopeeCategoryID = shopeeCategoryID;
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return (String.format("Category: \n[name: %s,\nid: %s,\nShopeeID: %s]", name, id, shopeeCategoryID));
    }

    
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    Set<Product> products = new HashSet<>();

//    @JsonBackReference
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        else if (!(obj instanceof Category)) return false;
        else {
            Category p = (Category) obj;
            if (this.id != null && p.id != null) return p.id.equals(this.id);
            else return p.shopeeCategoryID.equals(this.shopeeCategoryID);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shopeeCategoryID);
    }

    @Override
    public Category clone() {
        Category c;
        try {
            c = (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Automatic copy of Category failed. Manual copy instead\n");

            c = new Category();
            c.setId(this.getId());
            c.setImg(this.getImg());
            c.setName(this.getName());
            c.setShopeeCategoryID(this.getShopeeCategoryID());
        }

        return c;
    }
}
