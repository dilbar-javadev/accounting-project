package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listCategories();

    CategoryDto findById(Long id) throws AccountingAppException;

    CategoryDto updateCategory(CategoryDto categoryDto) throws AccountingAppException;

    void deleteCategory(Long id) throws AccountingAppException;

    CategoryDto createCategory(CategoryDto categoryDto);

    boolean ifCategoryExist(String description);

    CategoryDto checkAndSetProductStatus(Long id) throws AccountingAppException;

    int getQuantityInStockByCategoryId(Long id);
}
