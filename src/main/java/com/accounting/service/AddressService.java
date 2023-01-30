package com.accounting.service;

import com.accounting.dto.AddressDto;

import java.util.List;

public interface AddressService {


    List<String > findAllCountries();

    AddressDto update(Long addressId,AddressDto addressDto);


}
