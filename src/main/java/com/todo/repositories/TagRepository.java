package com.todo.repositories;

import java.util.List;

import com.todo.model.Tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("select t from Tag t where t.mUsers.userId=?1")
    List<Tag> findTagByUserId(Integer UserId);

    @Query("select t.tagName from Tag t where t.mUsers.userId=?1")
    List<String> findTagNameByUserId(Integer UserId);

    @Query("select t from Tag t where t.mTaskTags.task_Id=?1")
    List<Tag> findTagByTaskId(Integer taskId);
}
