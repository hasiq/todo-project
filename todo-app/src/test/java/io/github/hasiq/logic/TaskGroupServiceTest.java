package io.github.hasiq.logic;

import io.github.hasiq.model.TaskGroup;
import io.github.hasiq.model.TaskGroupRepository;
import io.github.hasiq.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("should throw when undone tasks")
    void toggleGroup_undoneTasks_throwsIllegalStateException(){
        //given
        var mockTaskRepository = setupTaskRepository(true);
        //system under tests
        var toTest = new TaskGroupService(null,mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Group has undone tasks, Done all the tasks first");
    }



    @Test
    @DisplayName("should throw when no task group found")
    void toggleGroup_noTaskGroupFound_throwsIllegalStateException(){
        //given
        var mockTaskRepository = setupTaskRepository(false);
        var taskGroupRepository = setupTaskGroupRepository(Optional.empty());
        //system under tests
        var toTest = new TaskGroupService(taskGroupRepository,mockTaskRepository);
        //when
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        //then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("TaskGroup with given id does not exist");
    }

    @Test
    @DisplayName("should toggle group")
    void toggleGroup_toggleGroup_success(){
        //given
        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();
        var mockTaskRepository = setupTaskRepository(false);
        var taskGroupRepository = setupTaskGroupRepository(Optional.of(taskGroup));
        //system under tests
        var toTest = new TaskGroupService(taskGroupRepository,mockTaskRepository);
        //when
        toTest.toggleGroup(0);
        //then
        assertThat(taskGroup.isDone()).isEqualTo(!beforeToggle);
    }

    private static TaskGroupRepository setupTaskGroupRepository(Optional<TaskGroup> t) {
        var taskGroupRepository = mock(TaskGroupRepository.class);
        when(taskGroupRepository.findById(anyInt())).thenReturn(t);
        return taskGroupRepository;
    }

    private static TaskRepository setupTaskRepository(boolean t) {
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(t);
        return mockTaskRepository;
    }
}
