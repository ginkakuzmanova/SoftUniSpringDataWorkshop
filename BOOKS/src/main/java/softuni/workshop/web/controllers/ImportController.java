package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
@RequestMapping(path = "/import")
public class ImportController extends BaseController {

    private final CompanyService companyService;
    private final EmployeeService employeeService;
    private final ProjectService projectService;

    @Autowired
    public ImportController(CompanyService companyService, EmployeeService employeeService, ProjectService projectService) {
        this.companyService = companyService;
        this.employeeService = employeeService;
        this.projectService = projectService;
    }

    @GetMapping("/xml")
    public ModelAndView importXml() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        modelAndView.addObject("areImported",
                new boolean[]{this.companyService.areImported(),
                        this.projectService.areImported(),
                        this.projectService.areImported()});

        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView importCompanies() {
        return view("xml/import-companies");
    }

    @GetMapping("/projects")
    public ModelAndView importProjects() {
        return view("xml/import-projects");
    }

    @GetMapping("/employees")
    public ModelAndView importEmployees() {
        return view("xml/import-employees");
    }


}
