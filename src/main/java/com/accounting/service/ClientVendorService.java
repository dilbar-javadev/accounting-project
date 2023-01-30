package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.ClientVendorDto;
import com.accounting.enums.ClientVendorType;

import java.util.List;

public interface ClientVendorService {

    //List<ClientVendorDto> findAllByClientVendorType(ClientVendorType clientVendorType);

    ClientVendorDto findById(long id) throws AccountingAppException;
    List<ClientVendorDto> findAllByClientVendorTypeBelongsToCompany(ClientVendorType vendor);
    List<ClientVendorDto> listAll();
    void update(ClientVendorDto clientVendorDto) throws AccountingAppException;
    void deleteById(Long id) throws AccountingAppException;
    void save(ClientVendorDto clientVendorDto);

    boolean isClientVendorCanBeDeleted(Long id);

    List<ClientVendorDto> findAllByCompany();







}
