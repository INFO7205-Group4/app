package com.todo.repositories;

import com.todo.model.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ListRepository extends JpaRepository<List, Integer> {

  @Query("select l FROM List l WHERE l.mUsers.userId = ?1")
  java.util.List<List> findList(Integer UserId);

}
