package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CompanyDto;
import com.accounting.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityService extends UserDetailsService {

    UserDTO getLoggedInUser();
    CompanyDto getLoggedInCompany() throws AccountingAppException;
}
