package softuni.workshop.service.services;

import com.google.gson.Gson;
import softuni.workshop.web.models.EmployeeViewModel;

import javax.xml.bind.JAXBException;
import java.util.Set;

public interface EmployeeService {

    void importEmployees() throws JAXBException;

    boolean areImported();

    String readEmployeesXmlFile();

    String exportEmployeesWithAgeAbove();

    Set<EmployeeViewModel> findAllByGivenAge();

    Set<EmployeeViewModel> findAll();

    String employeeToJson(Gson gson);
}
