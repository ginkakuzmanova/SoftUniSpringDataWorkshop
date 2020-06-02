package softuni.workshop.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.workshop.data.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

}
