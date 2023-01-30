package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CompanyDto;

import java.util.List;

public interface CompanyService {


    //=========================================================================//
    List<CompanyDto> listAll();

    //=========================================================================//
    CompanyDto findById(Long id) throws AccountingAppException;

    //=========================================================================//
    void save(CompanyDto companyDto);

    //=========================================================================//
    void changeCompanyStatusById(Long id) throws AccountingAppException;

    //=========================================================================//
    CompanyDto update(CompanyDto companyDto) throws AccountingAppException;

    CompanyDto getCompanyOfLoggedInUser();


    // Boban wrote this method here to get companies By User
    List<CompanyDto> listAllByUser();

}
