package com.fin.test.dimin.Repository;

import com.fin.test.dimin.Entity.UserImpression;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImpressionRepository extends JpaRepository<UserImpression,String> {
}
