package com.driftdirect.repository;

import com.driftdirect.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Paul on 11/10/2015.
 */
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("Select u From User u where u.username=:username or u.email=:username")
    public User findUser(@Param("username") String username);
}
