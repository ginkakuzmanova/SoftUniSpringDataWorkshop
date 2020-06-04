package softuni.workshop.service.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.data.dtos.EmployeeDto;
import softuni.workshop.data.dtos.EmployeeRootDto;
import softuni.workshop.data.entities.Employee;
import softuni.workshop.data.entities.Project;
import softuni.workshop.data.repositories.EmployeeRepository;
import softuni.workshop.data.repositories.ProjectRepository;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.util.XmlParser;
import softuni.workshop.web.models.EmployeeViewModel;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final String XML_PATH = "src\\main\\resources\\files\\xmls\\employees.xml";

    private final EmployeeRepository employeeRepository;
    private final XmlParser xmlParser;
    private final ProjectRepository projectRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               XmlParser xmlParser,
                               ProjectRepository projectRepository,
                               ModelMapper mapper) {
        this.employeeRepository = employeeRepository;
        this.xmlParser = xmlParser;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.unmarshalling(EmployeeRootDto.class, XML_PATH);
        for (EmployeeDto emp : employeeRootDto.getEmployeeDtoList()) {
            Employee employee = this.mapper.map(emp, Employee.class);
            Project empProject = this.projectRepository.findByName(emp.getProjectDto().getName());
            employee.setProject(empProject);

            this.employeeRepository.saveAndFlush(employee);
        }

    }

    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() {
        String xml = "";
        try {
            xml = String.join("\n", Files.readAllLines(Path.of(XML_PATH)));
        } catch (IOException e) {
            return "There is something wrong with the file.";
        }
        return xml;
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        return findAllByGivenAge().stream().
                map(EmployeeViewModel::toString)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public Set<EmployeeViewModel> findAllByGivenAge() {
        return this.employeeRepository.findAllByAgeGreaterThan(25)
                .stream().map(employee ->
                        this.mapper.map(employee, EmployeeViewModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<EmployeeViewModel> findAll() {
        return this.employeeRepository.findAll().stream()
                .map(e-> this.mapper.map(e,EmployeeViewModel.class)
                ).collect(Collectors.toSet());
    }

    @Override
    @Autowired
    public String employeeToJson(Gson gson) {
        return gson.toJson(this.findAll());
    }


}

