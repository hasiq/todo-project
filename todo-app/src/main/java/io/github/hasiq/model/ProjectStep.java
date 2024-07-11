package io.github.hasiq.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "project_steps")
public class ProjectStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Project step's description must not be empty")
    private String description;

    private int daysToDeadline;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDaysToDeadLine() {
        return daysToDeadline;
    }

    public void setDaysToDeadLine(int daysToDeadLine) {
        this.daysToDeadline = daysToDeadLine;
    }

    Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
