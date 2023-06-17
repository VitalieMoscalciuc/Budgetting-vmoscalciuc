package com.vmoscalciuc.budget.repository;

import com.vmoscalciuc.budget.model.Role;
import com.vmoscalciuc.budget.model.User;

import java.util.Optional;

public interface RoleRepository {

    Optional<Role> findByName(String name);
}
