package io.github.hasiq.adapter;

import io.github.hasiq.model.TaskGroup;
import io.github.hasiq.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SqlTaskGroupRepository extends TaskGroupRepository, JpaRepository<TaskGroup, Integer> {
    @Override
    @Query("from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();

    @Override
    boolean existsByDoneIsFalseAndProject_Id(Integer projectId);
}
