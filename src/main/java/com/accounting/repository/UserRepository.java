package com.accounting.repository;

import com.accounting.entity.Company;
import com.accounting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    List<User> findAll();
    boolean existsByUsername (String username);

    //------------------------------------------------------------------------
    List<User> findAllByRoleDescriptionAndCompanyOrderByCompanyTitleAscRoleDescription(String role, Company company);

    List<User> findAllByCompanyOrderByRoleDescription(Company company);

    List<User> findAllByRoleDescriptionOrderByCompanyTitle(String role);






}
