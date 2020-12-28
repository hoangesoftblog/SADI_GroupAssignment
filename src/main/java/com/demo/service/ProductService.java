package com.demo.service;

import com.demo.model.Product;
import com.demo.repository.ProductStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductService {
    @Autowired
    public ProductStore store;

    // When add,
    // If no ID, write new.
    // Else, DO NOT OVERWRITE existing details except adding new Histories (That is for update)
    // Lowest price is automatically added based on List of Histories
    private Product maintainProductInfo(Product product) {
        Product p = store.findById(Optional.ofNullable(product.getId()).orElse(0L)).orElse(product);
        p.setHistories(product.getHistories());

        return p;
    }

    /**
     * To allow Hibernate to store property with OneToMany relationship, Product p's History must contain a reference of p <br/>
     * https://stackoverflow.com/a/5217052
     * @param p - The Product to apply
     * @return p - The same Product after applying the function
     */
    private Product setRelationshipForHistory(Product p) {
        p.getHistories().forEach(priceHistory -> priceHistory.setProduct(p));
        return p;
    }

    private Product setNewLowestPrice(Product product) {
        if (!product.getHistories().isEmpty()) {
            //Find min price in the History array, and set new Lowest Price
            Long min = Collections.min(product.getHistories(), (history, t1) -> (int) (history.getPrice() - t1.getPrice())).getPrice();
            product.setLowestPrice(Optional.ofNullable(product.getLowestPrice()).filter(lowest -> lowest < min).orElse(min));
        }
        else {
            product.setLowestPrice(Optional.ofNullable(product.getLowestPrice()).orElse(product.getCurrentPrice()));
        }

        return product;
    }

    private Product prepareToAdd(Product product) {
        Product p = setRelationshipForHistory(maintainProductInfo(product));
        Product p1 = setNewLowestPrice(p);
        return p1;
    }

    public void add(Product product) {
        Product p = prepareToAdd(product);
        store.save(p);
    }

    // Not completely optimized
    public void addAll(List<Product> products){
        List<Product> productList = products.stream().map(this::prepareToAdd).collect(Collectors.toList());
        store.saveAll(productList);
    }

    public Product get(Long id) {
        return store.findById(id).orElse(null);
    }

    public List<Product> getAll() {
        return store.findAll();
    }

    public List<Product> getSome(List<Long> ids) {
        return store.findAllById(ids);
    }


    // Must have ID to allow for update
    // Update doesn't allow to add new Product or add new Histories of Product
    public void update(Product product) {
        if (product.getId() != null) {
            Optional<Product> productOptional = store.findById(product.getId());

            if (!(productOptional.isPresent())) {
                store.save(product);
            }
        }
    }

    public List<Product> find(String params) {
        return store.findByNameOrUrl(params, params);
    }

    public void delete(Long id) {
        Optional<Product> productOptional = store.findById(id);

        if (productOptional.isPresent()) {
            Product p = productOptional.get();
            p.setDeleted(true);
            update(p);
        }
    }

    public List<Product> findAllNotHidden() {
        return store.findAllByIsHiddenFalse();
    }
}


//        if (product.getId() == null) {
//            p = product;
//        }
//        else {
//            // Prevent overwriting existing Product information
//            Optional<Product> o = store.findById(product.getId());
//            p = o.orElse(product);
//
//        }
