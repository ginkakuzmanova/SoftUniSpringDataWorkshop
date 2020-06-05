package softuni.workshop.web.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    public ExportController(ProjectService projectService, EmployeeService employeeService, CompanyService companyService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping("/project-if-finished")
    public ModelAndView finishedProjects() {
        ModelAndView modelAndView = new ModelAndView();
        String finishedProjects = this.projectService.exportFinishedProjects();
        modelAndView.addObject("projectsIfFinished", finishedProjects);

        return view("/export/export-project-if-finished", modelAndView);
    }

    @GetMapping("/employees-above")
    public ModelAndView employeesAbove(){
        ModelAndView model = new ModelAndView();
        String employeesWithAgeAbove25 =
                this.employeeService.exportEmployeesWithAgeAbove();
        model.addObject("employeesAbove", employeesWithAgeAbove25);
        return view("/export/export-employees-with-age",model);
    }

    @GetMapping("/employees-json")
    public ModelAndView employeesJson(){
        ModelAndView model = new ModelAndView();
        String empToJson = this.employeeService.employeeToJson();
        model.addObject("allEmployees",empToJson);
        return view("export/export-employees-as-json",model);
    }

    @GetMapping("/companies-json")
    public ModelAndView companiesJson(){
        ModelAndView model = new ModelAndView();
        String compToJson = this.companyService.companyToJson();
        model.addObject("allCompanies",compToJson);
        return view("export/export-companies-as-json",model);
    }

}
