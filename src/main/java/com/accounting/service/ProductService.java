package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.InvoiceProductDTO;
import com.accounting.dto.ProductDTO;
import com.accounting.entity.InvoiceProduct;
import com.accounting.enums.InvoiceType;

import java.util.List;

public interface ProductService {
    ProductDTO getProductById(Long id) throws AccountingAppException;
    List<ProductDTO> listAllProducts();
    ProductDTO save(ProductDTO productDTO);
    void update(ProductDTO productDTO) throws AccountingAppException;
    void deleteById(Long id) throws AccountingAppException;
    boolean checkAnyProductExist(Long id);
    boolean isInStockEnough(InvoiceProductDTO invoiceProductDTO);
    List<ProductDTO> getAllProductsByCompany();
    boolean checkAnyInvoiceExist(Long id);
    boolean isNameExist(String name, Long id);
    void updateProductQuantity(InvoiceType invoiceType, InvoiceProduct invoiceProduct);
    int getQuantityById(Long id);
    List<ProductDTO> getAllProductsByCategoryId(Long id);
}