package com.driftdirect.repository;

import com.driftdirect.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Paul on 11/16/2015.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
