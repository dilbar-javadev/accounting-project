package com.accounting.converter;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CompanyDto;
import com.accounting.service.CompanyService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDtoConverter implements Converter<String, CompanyDto> {

    private final CompanyService companyService;

    public CompanyDtoConverter(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDto convert(String source) {

        try {
            return companyService.findById(Long.parseLong(source));
        } catch (AccountingAppException e) {
            throw new RuntimeException(e);
        }
    }
}
