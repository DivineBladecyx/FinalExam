package com.fin.test.dimin.Repository;

import com.fin.test.dimin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findById(String id);
    void delete(String id);
}
