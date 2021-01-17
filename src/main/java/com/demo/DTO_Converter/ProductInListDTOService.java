package com.demo.DTO_Converter;

import com.demo.DTO.ProductInListDTO;
import com.demo.model.Product;
import com.demo.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductInListDTOService {
    @Autowired
    ModelMapper modelMapper;

    public ProductInListDTO convert(Product product) {
        ProductInListDTO p = modelMapper.map(product, ProductInListDTO.class);
        return p;
    }

    @Autowired
    ProductService productService;

    public Page<ProductInListDTO> getAllWithPage(int page_number){
        Page<Product> productPage = productService.getAllWithPage(page_number);
        Page<ProductInListDTO> productDTOs = productPage.map(this::convert);
        return productDTOs;
    }

    public List<ProductInListDTO> getSome(List<Long> ids) {
        List<Product> products =  productService.getSome(ids);
        List<ProductInListDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }

    public List<ProductInListDTO> find(String params) {
        List<Product> products =  productService.find(params);
        List<ProductInListDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }

//    public List<ProductInListDTO> getAll() {
//        List<Product> products =  productService.getAll();
//        List<ProductInListDTO> productDTOS = products.stream()
//                .map(this::convert)
//                .collect(Collectors.toList());
//        return productDTOS;
//    }

    public List<ProductInListDTO> findByCategoryId(Long categoryId){
        List<Product> products = productService.findByCategoryId(categoryId);
        List<ProductInListDTO> productDTOS = products.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return productDTOS;
    }
}
