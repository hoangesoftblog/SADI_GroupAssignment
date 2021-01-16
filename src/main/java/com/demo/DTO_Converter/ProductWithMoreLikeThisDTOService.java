package com.demo.DTO_Converter;

import com.demo.DTO.ProductDTO;
import com.demo.DTO.ProductWithMoreLikeThisDTO;
import com.demo.model.Product;
import com.demo.service.ProductService;
import com.demo.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class ProductWithMoreLikeThisDTOService {
    @Autowired
    ModelMapper modelMapper;

    public ProductWithMoreLikeThisDTO convertToProduct(Product product) {
        ProductWithMoreLikeThisDTO p = modelMapper.map(product, ProductWithMoreLikeThisDTO.class);
        return p;
    }

    public ProductWithMoreLikeThisDTO.MoreLikeThisProduct convertToProductLikeThis(Product product) {
        ProductWithMoreLikeThisDTO.MoreLikeThisProduct p = modelMapper.map(product, ProductWithMoreLikeThisDTO.MoreLikeThisProduct.class);
        return p;
    }

    @Autowired
    ProductService productService;

    @Autowired
    SearchService searchService;

    public ProductWithMoreLikeThisDTO get(Long id) {
        Optional<Product> optionalProduct = Optional.ofNullable(productService.get(id));
        ProductWithMoreLikeThisDTO productWithMoreLikeThisDTO = optionalProduct.map(product -> {
            ProductWithMoreLikeThisDTO p = convertToProduct(product);
            List<Product> likeThis = searchService.getMoreLikeThis(product);
            List<ProductWithMoreLikeThisDTO.MoreLikeThisProduct> moreLikeThis = likeThis
                    .stream().map(this::convertToProductLikeThis).collect(Collectors.toList());
            p.setMoreLikeThisProducts(moreLikeThis);
            return p;
        }).orElse(null);

        return productWithMoreLikeThisDTO;
    }
}
