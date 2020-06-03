package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

import javax.xml.bind.JAXBException;

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
                new boolean[]{
                        this.companyService.areImported(),
                        this.projectService.areImported(),
                        this.employeeService.areImported()
        });

        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView importCompanies() {
        ModelAndView model = new ModelAndView();
        String xmlContent = this.companyService.readCompaniesXmlFile();
        model.addObject("companies", xmlContent);
        return view("xml/import-companies", model);
    }

    @PostMapping("/companies")
    public ModelAndView companiesInserted() throws JAXBException {
        if (!this.companyService.areImported()) {
            this.companyService.importCompanies();
        }
        return redirect("/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView importProjects() {
        ModelAndView modelAndView = new ModelAndView();
        String xmlProjects = this.projectService.readProjectsXmlFile();
        modelAndView.addObject("projects", xmlProjects);
        return view("xml/import-projects", modelAndView);
    }

    @PostMapping("/projects")
    public ModelAndView projectsInserted() throws JAXBException {
        if (!this.projectService.areImported()) {
            this.projectService.importProjects();
        }
        return redirect("/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView importEmployees() {
        ModelAndView modelAndView = new ModelAndView();
        String xmlEmployees = this.employeeService.readEmployeesXmlFile();
        modelAndView.addObject("employees", xmlEmployees);
        return view("xml/import-employees", modelAndView);
    }

    @PostMapping("/employees")
    public ModelAndView employeesInserted() throws JAXBException {
        if (!this.employeeService.areImported()) {
            this.employeeService.importEmployees();
        }
        return redirect("/import/xml");
    }


}
