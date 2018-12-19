package com.fin.test.dimin.Repository;

import com.fin.test.dimin.Entity.Friends;
import com.fin.test.dimin.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendsRepository extends JpaRepository<Friends,Integer> {
}
