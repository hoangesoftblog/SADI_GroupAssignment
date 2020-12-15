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
    // Else, doesn't overwrite existing details except adding new Histories
    // Lowest price is automatically added based on List of Histories
    private Product prepareToAdd(Product product) {
        Product p;
        if (product.getId() == null) {
            p = product;
        }
        else {
            // Prevent overwriting existing Product information
            Optional<Product> o = store.findById(product.getId());
            p = o.orElse(product);
            p.setHistories(product.getHistories());
        }

        if (!p.getHistories().isEmpty()) {
            p.getHistories().forEach(priceHistory -> priceHistory.setProduct(p));

            //Find min price in the previous history
            Long min = Collections.min(p.getHistories(), (history, t1) -> (int) (history.getPrice() - t1.getPrice())).getPrice();
            // And set new lowest record if smaller than current lowest value
            p.setLowestPrice(Optional.ofNullable(p.getLowestPrice()).filter(lowest -> lowest < min).orElse(min));
        }
        else {
            p.setLowestPrice(Optional.ofNullable(p.getLowestPrice()).orElse(p.getBasePrice()));
        }

        return p;
    }

    public void add(Product product) {
        Product p = prepareToAdd(product);
        store.save(p);
    }

    public void addAll(List<Product> products){
        List<Product> productList = products.stream().map(this::prepareToAdd).collect(Collectors.toList());
        store.saveAll(productList);
    }

    public Product get(Long id) {
        return store.findById(id).orElse(null);
    }

    public List<Product> getAll() {
        return new ArrayList<>(store.findAll());
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
}
