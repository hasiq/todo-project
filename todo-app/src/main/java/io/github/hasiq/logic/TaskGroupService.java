package io.github.hasiq.logic;

import io.github.hasiq.model.TaskGroup;
import io.github.hasiq.model.TaskGroupRepository;
import io.github.hasiq.model.TaskRepository;
import io.github.hasiq.model.projection.GroupReadModel;
import io.github.hasiq.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {
   private TaskGroupRepository repository;
   private TaskRepository taskRepository;

     TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository ) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
         TaskGroup result = repository.save(source.toGruop());
         return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
         return repository.findAll()
                 .stream()
                 .map(GroupReadModel::new)
                 .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
       if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
           throw new IllegalArgumentException("Group has undone tasks, Done all the tasks first");
       }
       TaskGroup result = repository.findById(groupId)
               .orElseThrow(() -> new IllegalArgumentException("TaskGroup not found"));
       result.setDone(!result.isDone());
       repository.save(result);
    }
}
