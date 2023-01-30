package com.thegogetters.accounting.service.Impl;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CompanyDto;
import com.accounting.dto.TestDocumentInitializer;
import com.accounting.dto.UserDTO;
import com.accounting.entity.Company;
import com.accounting.enums.CompanyStatus;
import com.accounting.mapper.MapperUtil;
import com.accounting.repository.CompanyRepository;

import com.accounting.service.Impl.CompanyServiceImpl;
import com.accounting.service.Impl.SecurityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {


    @Mock
    SecurityServiceImpl securityService;

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyServiceImpl companyService;


    @Spy
    private MapperUtil mapperUtil = new MapperUtil(new ModelMapper());

    @Test
    void GIVEN_LIST_OF_COMPANY_WHEN_LIST_ALL_THEN_SUCCESS() {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        CompanyDto companyDto1 = TestDocumentInitializer.getCompany(CompanyStatus.PASSIVE);

        List<Company> list = Arrays.asList(companyDto, companyDto1).stream()
                .map(p -> mapperUtil.convert(companyDto, new Company())).collect(Collectors.toList());

        when(companyRepository.findByIdIsNot(1L)).thenReturn(list);

        List<CompanyDto> companyDtoList = companyService.listAll();

        assertThat(companyDtoList.size() == 2);

//=========================================================================================================//
    }

    @Test
    void WHEN_FIND_BY_ID_THEN_SUCCESS() throws AccountingAppException {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);
        Company company = mapperUtil.convert(companyDto, new Company());

        when(companyRepository.findById(companyDto.getId())).thenReturn(Optional.of(company));

        CompanyDto returnedCompany = companyService.findById(companyDto.getId());

        assertThat(returnedCompany.getWebsite().equals(company.getWebsite()));


    }

//=========================================================================================================//


    @Test
    void WHEN_FIND_BY_ID_THEN_FAIL() {

        //when(companyRepository.findById(anyLong())).thenThrow(RuntimeException.class);
        doThrow(AccountingAppException.class).when(companyRepository).findById(anyLong());
        assertThrows(AccountingAppException.class, () -> companyService.findById(anyLong()));


    }


//=========================================================================================================//

    @Test
    void GIVEN_COMPANY_DTO_WHEN_SAVED_SUCCESS() {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(null);

        Company company = mapperUtil.convert(companyDto, new Company());

        companyService.save(companyDto);

        verify(companyRepository).save(any(Company.class));

    }

//=========================================================================================================//


    @Test
    void WHEN_FIND_ID_COMPANY_STATUS_CHANGE_SUCCESS() throws AccountingAppException {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.PASSIVE);

        Company company = mapperUtil.convert(companyDto,new Company());

        when(companyRepository.findById(companyDto.getId())).thenReturn(Optional.of(company));

        companyService.changeCompanyStatusById(companyDto.getId());

        assertThat(companyDto.getCompanyStatus().equals(CompanyStatus.ACTIVE));
    }

//=========================================================================================================//

    @Test
    void GIVEN_COMPANY_DTO_WHEN_UPDATE_THEN_SUCCESS() throws AccountingAppException {

        CompanyDto companyDto = TestDocumentInitializer.getCompany(CompanyStatus.ACTIVE);

        Company company = mapperUtil.convert(companyDto,new Company());

        CompanyDto UpdatedCompanyDto = TestDocumentInitializer.getCompany(CompanyStatus.PASSIVE);

        Company UpdatedCompany = mapperUtil.convert(companyDto,new Company());

        doReturn(Optional.of(company)).when(companyRepository).findById(anyLong());
        doReturn(UpdatedCompany).when(companyRepository).save(any(Company.class));

        CompanyDto result = companyService.update(UpdatedCompanyDto);

        assertThat(result.getCompanyStatus().equals(CompanyStatus.PASSIVE));


    }

//=========================================================================================================//

    @Test
    void Given_Logged_In_USER_WHEN_GET_COMPANY_SUCCESS() {

        UserDTO userDTO = TestDocumentInitializer.getUser("Admin");

        when(securityService.getLoggedInUser()).thenReturn(userDTO);

        CompanyDto companyDto1 = companyService.getCompanyOfLoggedInUser();

        assertThat(userDTO.getCompany().equals(companyDto1));


    }
//=========================================================================================================//

    @Test
    void listAllByUser() {
    }
}