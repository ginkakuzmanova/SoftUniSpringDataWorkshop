package softuni.workshop.data.entities;

import com.google.gson.annotations.Expose;
import softuni.workshop.data.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company extends BaseEntity {
    private String name;
    private Set<Project> projects;

    public Company() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "company", targetEntity = Project.class, fetch = FetchType.EAGER)
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }
}
