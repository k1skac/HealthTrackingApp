package com.greenfoxacademy.hta.repositories;

import com.greenfoxacademy.hta.models.roles.Role;
import com.greenfoxacademy.hta.models.roles.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role,Integer> {
    Role findByRoleName(RoleName roleName);
}
