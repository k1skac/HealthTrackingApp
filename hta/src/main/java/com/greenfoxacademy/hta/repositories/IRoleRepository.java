package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.Role;
import com.greenfoxacademy.hta.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);
}
