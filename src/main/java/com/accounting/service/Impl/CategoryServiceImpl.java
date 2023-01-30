package com.accounting.service.Impl;

import com.accounting.repository.CategoryRepository;
import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CategoryDto;
import com.accounting.dto.CompanyDto;
import com.accounting.dto.ProductDTO;
import com.accounting.entity.Category;
import com.accounting.mapper.MapperUtil;
import com.accounting.service.CategoryService;
import com.accounting.service.CompanyService;
import com.accounting.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;
    private final ProductService productService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, CompanyService companyService, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
        this.productService = productService;
    }

    @Override
    public List<CategoryDto> listCategories() {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        List<CategoryDto> categoryList = categoryRepository.listCategoriesByAscOrder().stream()
                .filter(category -> category.getCompany().getId().equals(companyDto.getId()))
                .map(category -> mapperUtil.convert(category, new CategoryDto())).toList();

        for (CategoryDto category : categoryList) {
            if(getQuantityInStockByCategoryId(category.getId()) > 0) category.setHasProduct(true);
        }
        return categoryList;
    }

    @Override
    public CategoryDto findById(Long id) throws AccountingAppException {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AccountingAppException("Category not found"));
        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) throws AccountingAppException {
        Category category = categoryRepository.findById(categoryDto.getId()).orElseThrow(()-> new AccountingAppException("Category not found"));
        CategoryDto toBeConverted = mapperUtil.convert(category, new CategoryDto());
        toBeConverted.setId(category.getId());
        toBeConverted.setDescription(categoryDto.getDescription());
        Category convertedCategory = mapperUtil.convert(toBeConverted, new Category());
        if(!ifCategoryExist(convertedCategory.getDescription())) {
            categoryRepository.save(convertedCategory);
            return null;
        }
        else return categoryDto;
    }

    @Override
    public void deleteCategory(Long id) throws AccountingAppException {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new AccountingAppException("Category not found"));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        categoryDto.setCompany(companyDto);
        Category category = mapperUtil.convert(categoryDto, new Category());
        if(!ifCategoryExist(category.getDescription())) {
            categoryRepository.save(category);
            return null;
        }
        else return categoryDto;
    }

    @Override
    public boolean ifCategoryExist(String description) {
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();
        if(categoryRepository.findByDescriptionAndCompanyId(description, companyDto.getId())==null)return false;
        return categoryRepository.findByDescriptionAndCompanyId(description, companyDto.getId()).getCompany().getId().equals(companyDto.getId());
    }

    @Override
    public CategoryDto checkAndSetProductStatus(Long id) throws AccountingAppException {
        CategoryDto categoryDto = findById(id);
        categoryDto.setHasProduct(getQuantityInStockByCategoryId(id) > 0);
        return categoryDto;
    }

    @Override
    public int getQuantityInStockByCategoryId(Long id) {
        List<ProductDTO> productDTOList = productService.getAllProductsByCategoryId(id);
        int sumOfQuantity = 0;
        for (ProductDTO productDTO : productDTOList) {
            sumOfQuantity += productDTO.getQuantityInStock();
        }
        return sumOfQuantity;
    }


}
