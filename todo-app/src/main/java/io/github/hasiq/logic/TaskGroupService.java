package io.github.hasiq.logic;

import io.github.hasiq.model.*;
import io.github.hasiq.model.projection.GroupReadModel;
import io.github.hasiq.model.projection.GroupWriteModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//@Service
public class TaskGroupService {
   private TaskGroupRepository repository;
   private TaskRepository taskRepository;

     TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository ) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    GroupReadModel createGroup(GroupWriteModel source, Project project) {
        TaskGroup result = repository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        return createGroup(source, null);
    }

    public List<GroupReadModel> readAll() {
         return repository.findAll()
                 .stream()
                 .map(GroupReadModel::new)
                 .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
       if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
           throw new IllegalStateException("Group has undone tasks, Done all the tasks first");
       }
       TaskGroup result = repository.findById(groupId)
               .orElseThrow(() -> new IllegalArgumentException("TaskGroup with given id does not exist"));
       result.setDone(!result.isDone());
       repository.save(result);
    }



}
