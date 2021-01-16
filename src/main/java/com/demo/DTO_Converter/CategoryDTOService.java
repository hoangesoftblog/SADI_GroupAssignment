package com.demo.DTO_Converter;

import com.demo.DTO.CategoryDTO;
import com.demo.model.Category;
import com.demo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryDTOService {
    @Autowired
    ModelMapper modelMapper;

    public CategoryDTO convert(Category category) {
        CategoryDTO c = modelMapper.map(category, CategoryDTO.class);
        return c;
    }

    @Autowired
    public CategoryService categoryService;

    public List<CategoryDTO> getAll(){
        List<Category> categories = categoryService.getAll();
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(this::convert)
                .collect(Collectors.toList());
        return categoryDTOS;
    }

    public CategoryDTO findByID(Long id){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryService.findByID(id));
        CategoryDTO categoryDTO = categoryOptional.map(this::convert).orElse(null);
        return categoryDTO;
    }

    public CategoryDTO getCategoryByShopeeID(Long shopeeCategoryID){
        Optional<Category> categoryOptional = Optional.ofNullable(categoryService.getCategoryByShopeeID(shopeeCategoryID));
        CategoryDTO categoryDTO = categoryOptional.map(this::convert).orElse(null);
        return categoryDTO;
    }
}
