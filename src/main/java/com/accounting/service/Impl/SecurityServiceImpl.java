package com.accounting.service.Impl;


import com.accounting.repository.UserRepository;
import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.CompanyDto;
import com.accounting.dto.UserDTO;
import com.accounting.entity.User;
import com.accounting.entity.common.UserPrincipal;
import com.accounting.service.CompanyService;
import com.accounting.service.SecurityService;
import com.accounting.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    private final UserService userService;
    private final CompanyService companyService;

    public SecurityServiceImpl(UserRepository userRepository, @Lazy UserService userService, @Lazy CompanyService companyService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.companyService = companyService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }

    @Override
    public UserDTO getLoggedInUser() {
        var currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUserName(currentUsername);
    }

    @Override
    public CompanyDto getLoggedInCompany() throws AccountingAppException {
        CompanyDto company = getLoggedInUser().getCompany();
        return companyService.findById(company.getId());
    }


}
