package com.fin.test.dimin.Repository;

import com.fin.test.dimin.Entity.Crowds;
import com.fin.test.dimin.Entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrowdsRepository extends JpaRepository<Crowds,String> {
}
