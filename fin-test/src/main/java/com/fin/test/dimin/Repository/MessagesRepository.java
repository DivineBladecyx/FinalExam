package com.fin.test.dimin.Repository;

import com.fin.test.dimin.Entity.Messages;
import com.fin.test.dimin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagesRepository extends JpaRepository<Messages,String> {
}
