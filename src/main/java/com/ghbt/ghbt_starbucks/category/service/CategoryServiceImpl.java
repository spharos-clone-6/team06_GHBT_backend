package com.ghbt.ghbt_starbucks.category.service;

import com.ghbt.ghbt_starbucks.category.model.Category;
import com.ghbt.ghbt_starbucks.category.repository.ICategoryRepository;
import com.ghbt.ghbt_starbucks.category.vo.RequestCategory;
import com.ghbt.ghbt_starbucks.category.vo.ResponseCategory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor

public class CategoryServiceImpl implements ICategoryService{

    private final ICategoryRepository iCategoryRepository;


    @Override
    public ResponseCategory addCategory(RequestCategory requestCategory) {
        Category category = Category.builder()
                .name(requestCategory.getName())
                .type(requestCategory.getType())
                .build();
        Category resCategory = iCategoryRepository.save(category);

        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(resCategory.getId())
                .name(resCategory.getName())
                .type(resCategory.getType())
                .build();
        return responseCategory;
    }

    @Override
    public ResponseCategory getCategory(Long id) {
        Category category = iCategoryRepository.findById(id).get();
        ResponseCategory responseCategory = ResponseCategory.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .build();
        return responseCategory;
    }


    @Override
    public List<Category> getAllCategory() {
        List<Category> categoryList = iCategoryRepository.findAll();
        return categoryList;
    }
}