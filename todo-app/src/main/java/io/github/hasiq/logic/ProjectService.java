package io.github.hasiq.logic;

import io.github.hasiq.TaskConfigurationPropertries;
import io.github.hasiq.model.*;
import io.github.hasiq.model.projection.GroupReadModel;
import io.github.hasiq.model.projection.GroupTaskWriteModel;
import io.github.hasiq.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class ProjectService {

    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService service;
    private TaskConfigurationPropertries config;

    public ProjectService(ProjectRepository repository, TaskGroupRepository taskGroupRepository, TaskGroupService service, TaskConfigurationPropertries config) {
        this.repository = repository;
        this.taskGroupRepository = taskGroupRepository;
        this.service = service;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(Project toSave) {
        return repository.save(toSave);
    }

    public GroupReadModel createGroup(LocalDateTime deadline, int projectId){
        if(!config.getTemplate().isAllowMultipleTasksFromTemplate() && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(step -> {
                                       var task = new GroupTaskWriteModel();
                                       task.setDescription(step.getDescription());
                                       task.setDeadline(deadline.plusDays(step.getDaysToDeadLine()));
                                       return task;
                                    }
                                    ).collect(Collectors.toSet())
                    );
                   return service.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }
}
