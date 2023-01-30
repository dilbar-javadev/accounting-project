package com.accounting.converter;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CategoryDto;
import com.accounting.service.CategoryService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter implements Converter<String, CategoryDto> {

    private final CategoryService categoryService;

    public CategoryDtoConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @Override
    public CategoryDto convert(String description) {
        if (description == null || description.equals("")) { // Added by Evgenia. Need this for Validation to work when Category not selected :)
            return null;
        }
        try {
            return categoryService.findById(Long.valueOf(description));
        } catch (AccountingAppException e) {
            throw new RuntimeException(e);
        }
    }
}
