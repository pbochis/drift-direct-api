package com.driftdirect.repository;

import com.driftdirect.domain.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Paul on 11/16/2015.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("Select r From Role r where r.authority=:authority")
    public Role findRoleByAuthority(@Param("authority") String authority);

    @Query("Select r From Role r where r.authority in :visibleAuthorities")
    public List<Role> findVisibleRoles(@Param("visibleAuthorities")List<String> authorities);
}
