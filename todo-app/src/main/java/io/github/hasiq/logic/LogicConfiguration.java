package io.github.hasiq.logic;

import io.github.hasiq.TaskConfigurationPropertries;
import io.github.hasiq.model.ProjectRepository;
import io.github.hasiq.model.TaskGroupRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogicConfiguration {
    @Bean
    ProjectService projectService(final ProjectRepository projectRepository,
                                  final TaskGroupRepository taskGroupRepository,
                                  final TaskConfigurationPropertries taskConfigurationPropertries
    ) {
        return new ProjectService(projectRepository, taskGroupRepository,taskConfigurationPropertries);
    }
}
