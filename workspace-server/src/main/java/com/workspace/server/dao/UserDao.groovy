package com.workspace.server.dao

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional


/**
 * Created by CHENMA11 on 12/29/2017.
 */
@Transactional
public interface UserDao extends CrudRepository<User, Long> {
    public  User findByEmail(String email);
}