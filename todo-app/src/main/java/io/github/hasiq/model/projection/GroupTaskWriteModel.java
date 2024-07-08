package io.github.hasiq.model.projection;

import io.github.hasiq.model.Task;
import io.github.hasiq.model.TaskGroup;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GroupTaskWriteModel {
    private String description;
    private LocalDateTime deadline;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Task toTask(final TaskGroup group) {
        return new Task(description, deadline, group);
    }
}
