package com.demo.service;

import com.demo.model.Category;
import com.demo.model.Product;
import com.demo.repository.CategoryStore;
import com.demo.repository.ProductStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductService implements RemoveDependency<Product> {
    @Autowired
    public ProductStore productStore;

    @Autowired
    public CategoryStore categoryStore;

//    @Autowired
//    public CategoryService categoryService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // When add: If no ID, write new.
    // Else, DO NOT OVERWRITE existing details except adding new Histories (That is for update)
    // Lowest price is automatically added based on List of Histories
    private Product maintainProductIntegrity(Product product) {
        Product p;
        if (Optional.ofNullable(product.getId()).isPresent()) {
            p = productStore.findById(product.getId()).orElse(product);
        }
        else {
            p = productStore.findFirstByShopeeIDAndShopeeShopID(product.getShopeeID(), product.getShopeeShopID()).orElse(product);
        }

        p.setHistories(product.getHistories());
        p.setCategories(product.getCategories());

//        System.out.println("Found products with ID: " + p.getId());
//        System.out.println(p.getCategories());
//        System.out.println();
        return p;
    }

    /**
     * To allow Hibernate to store property with OneToMany relationship, Product p's History must contain a reference of p <br/>
     * https://stackoverflow.com/a/5217052
     * @param p - The Product to apply
     * @return p - The same Product after applying the function
     */
    private Product setProductForHistories(Product p) {
        p.getHistories().forEach(priceHistory -> priceHistory.setProduct(p));
        return p;
    }

    private Product setNewLowestPrice(Product product) {
        if (!product.getHistories().isEmpty()) {
            //Find min price in the History array, and set new Lowest Price
            Long min = Collections.min(product.getHistories(), (history, t1) -> (int) (history.getPrice() - t1.getPrice())).getPrice();
            product.setLowestPrice(Optional.ofNullable(product.getLowestPrice()).filter(lowest -> lowest < min).orElse(min));
        } else {
            product.setLowestPrice(Optional.ofNullable(product.getLowestPrice()).orElse(product.getCurrentPrice()));
        }

        return product;
    }

    private Product setNewHighestPrice(Product product) {
        if (!product.getHistories().isEmpty()) {
            //Find max price in the History array, and set new Lowest Price
            Long max = Collections.max(product.getHistories(), (history, t1) -> (int) (-(history.getPrice() - t1.getPrice()))).getPrice();
            product.setHighestPrice(Optional.ofNullable(product.getHighestPrice()).filter(highest -> highest > max).orElse(max));
        } else {
            product.setHighestPrice(Optional.ofNullable(product.getHighestPrice()).orElse(product.getCurrentPrice()));
        }

        return product;
    }

    private Product maintainCategoryIntegrity(Product product){
//        System.out.println("Category again");

        Set<Category> categories = Optional.ofNullable(product.getCategories()).orElse(new LinkedHashSet<>())
                .stream()
                .map(category -> {
//                    System.out.println("Category before: ");
//                    System.out.println(category);
                    Optional<Category> c = categoryStore.findFirstByShopeeCategoryID(category.getShopeeCategoryID());
//                    System.out.println(c.isPresent() ? c : "Can not find in the DB");
//                    System.out.println();
                    return c.orElse(category);
                })
                .collect(Collectors.toSet());

//        categories.forEach(category -> {
//            Optional.ofNullable(category.getProducts()).orElse(new LinkedHashSet<>()).add(product);
//        });

        product.setCategories(categories);

        return product;
    }

    private Product prepareToAdd(Product product) {
        Product p_init = maintainCategoryIntegrity(product);
        Product p3 = maintainProductIntegrity
                (p_init);

        Product p = setProductForHistories(p3);
        Product p1 = setNewLowestPrice(p);
        Product p2 = setNewHighestPrice(p1);

        Product p_final = (p2);

        return p_final;
    }

    public void add(Product product) {
        Product p = prepareToAdd(product);
        productStore.save(p);
    }

    // Not completely optimized
    public void addAll(List<Product> products){
        List<Product> productList = products.stream().map(this::prepareToAdd).collect(Collectors.toList());
        productStore.saveAll(productList);
    }






    // Remove dependency for GET methods
    @Override
    public Product GetMethod_RemoveDependency(Product product) {
        product.getCategories().forEach(category -> {
            category.setProducts(null);
        });
        return product;
    }
    public Product removeDependency(Product product) {
        return GetMethod_RemoveDependency(product);
    }

    public Product get(Long id) {
        Optional<Product> optionalProduct = productStore.findById(id);
        optionalProduct.ifPresent(this::removeDependency);
        return optionalProduct.orElse(null);
    }

    public List<Product> getSome(List<Long> ids) {
        List<Product> products =  productStore.findAllById(ids);
        products.forEach(this::removeDependency);
        return products;
    }

    public Product get(Long shopeeID, Long shopeeShopID) {
        Optional<Product> optionalProduct = productStore.findFirstByShopeeIDAndShopeeShopID(shopeeID, shopeeShopID);
        optionalProduct.ifPresent(this::removeDependency);
        return optionalProduct.orElse(null);
    }

    public List<Product> find(String params) {
        List<Product> products =  productStore.findByNameOrUrl(params, params);
        products.forEach(this::removeDependency);
        return products;
    }

    public List<Product> getAllNotHidden() {
        List<Product> products = productStore.findAllByIsHiddenFalse();
        products.forEach(this::removeDependency);
        return products;
    }

    public List<Product> findByCategoryId(Long categoryId){
        List<Product> products = productStore.findAllByCategories_Id(categoryId);
        products.forEach(this::removeDependency);
        return products;
    }

    public Page<Product> getAllNotHiddenWithPage(int page_number) {
        Pageable pageRequest = PageRequest.of(page_number, 1);
        Page<Product> products = productStore.findAll(pageRequest);
        products.forEach(this::removeDependency);
        return products;
    }



    // Must have ID to allow for update
    // Update doesn't allow to add new Product or add new Histories of Product
    public void update(Product product) {
        if (product.getId() != null) {
            Optional<Product> productOptional = productStore.findById(product.getId());

            if (!(productOptional.isPresent())) {
                productStore.save(product);
            }
        }
    }

    public void delete(Long id) {
        productStore.findById(id).ifPresent(product -> {
            product.setDeleted(true);
            update(product);
        });
    }
}
