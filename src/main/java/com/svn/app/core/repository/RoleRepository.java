package com.svn.app.core.repository;

import com.svn.app.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByRoleName(String roleName);
}
