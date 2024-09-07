package com.nrv.SpringSecurityJWT.repository;

import com.nrv.SpringSecurityJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {

    User findByUserName(String userName);

}
