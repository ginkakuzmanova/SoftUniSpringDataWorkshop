package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
