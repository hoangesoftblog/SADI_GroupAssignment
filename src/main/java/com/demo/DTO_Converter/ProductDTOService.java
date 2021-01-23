package com.demo.DTO_Converter;

import com.demo.DTO.ProductDTO;
import com.demo.model.Product;
import com.demo.service.ProductService;
import com.demo.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductDTOService {
    @Autowired
    ModelMapper modelMapper;

    public ProductDTO convert(Product product) {
        ProductDTO p = modelMapper.map(product, ProductDTO.class);

        return p;
    }

    @Autowired
    ProductService productService;

    public Page<ProductDTO> getAllWithPage(int page_number){
        Page<Product> productPage = productService.getAllWithPage(page_number);

        Page<ProductDTO> productDTOs = productPage.map(this::convert);
        return productDTOs;
    }

    public ProductDTO get(Long id) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.get(id));
        ProductDTO productDTO = optionalProduct.map(this::convert).orElse(null);
        return productDTO;
    }

    public List<ProductDTO> getSome(List<Long> ids) {
        List<Product> products =  productService.getSome(ids);
        List<ProductDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }

    public ProductDTO get(Long shopeeID, Long shopeeShopID) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.get(shopeeID, shopeeShopID));
        ProductDTO productDTO = optionalProduct.map(this::convert).orElse(null);
        return productDTO;
    }

    public List<ProductDTO> find(String params) {
        List<Product> products =  productService.find(params);
        List<ProductDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }

    public List<ProductDTO> getAll() {
        List<Product> products =  productService.getAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }

    public Page<ProductDTO> findByCategoryId(Long categoryId, int page_number){
        Page<Product> products = productService.findByCategoryId(categoryId, page_number);
        Page<ProductDTO> productDTOS = products
                .map(this::convert);
        return productDTOS;
    }



    @Autowired
    SearchService searchService;

    public List<ProductDTO> search(String keywords,
                                   Long price_higher_than, Long price_lower_than,
                                   Double rating_higher_than, Double rating_lower_than,
                                   List<String> brands, List<String> categories,
                                   String by, String order){
        List<Product> products = searchService.search(keywords,
                price_higher_than, price_lower_than,
                rating_higher_than, rating_lower_than,
                brands, categories,
                by, order);
        List<ProductDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }
}
