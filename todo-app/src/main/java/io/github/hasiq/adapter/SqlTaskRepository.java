package io.github.hasiq.adapter;

import io.github.hasiq.model.Task;
import io.github.hasiq.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
interface SqlTaskRepository extends TaskRepository,  JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "select count(*) > 0 from tasks where id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupId);

    @Override
    List<Task> findAllByGroup_Id(Integer id);

    @Override
    List<Task> findAllByDeadlineBeforeAndDoneIsFalseOrDeadlineIsNullAndDoneIsFalse(LocalDateTime deadline);
}
