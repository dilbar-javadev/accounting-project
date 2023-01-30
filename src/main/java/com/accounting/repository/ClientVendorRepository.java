package com.accounting.repository;

import com.accounting.entity.ClientVendor;
import com.accounting.entity.Company;
import com.accounting.enums.ClientVendorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository < ClientVendor, Long > {


    List<ClientVendor> findAllByClientVendorTypeAndCompany(ClientVendorType clientVendorType, Company company);

    List<ClientVendor> findAllByCompany(Company company);




}
