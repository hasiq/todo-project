package io.github.hasiq.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Page<Task> findAll(Pageable page);
    Optional<Task> findById(Integer i);
    boolean existsById(Integer id);
    Task save(Task entity);
    List<Task> findByDone(boolean done);

    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);
}
