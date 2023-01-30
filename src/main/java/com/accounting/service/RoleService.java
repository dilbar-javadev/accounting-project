package com.accounting.service;

import com.accounting.custom.exception.AccountingAppException;
import com.accounting.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id) throws AccountingAppException;
    List<RoleDTO> listRolesByLoggedUser();
}
