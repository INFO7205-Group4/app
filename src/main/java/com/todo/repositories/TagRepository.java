package com.todo.repositories;

import com.todo.model.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    @Query("select t from Tag t where t.tUsers.userId=?1")
    List<Tag> findTagByUserId(Integer UserId);

    @Query("select t.tag_Name from Tag t where t.tUsers.userId=?1")
    List<String> findTagNameByUserId(Integer UserId);

    @Query("select t from Tag t where t.tag_Id=?1")
    Tag findByTagId(Integer tag_id);

//    @Query("select t from Tag t where t.tTaskTags.task_Id=?1")
//    List<Tag> findTagByTaskId(Integer taskId);
}