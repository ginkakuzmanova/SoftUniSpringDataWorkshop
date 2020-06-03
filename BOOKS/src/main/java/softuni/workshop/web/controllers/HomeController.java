package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.CompanyService;
import softuni.workshop.service.services.EmployeeService;
import softuni.workshop.service.services.ProjectService;

@Controller
public class HomeController extends BaseController {
    private CompanyService companyService;
    private ProjectService projectService;
    private EmployeeService employeeService;

    @Autowired
    public HomeController(CompanyService companyService,
                          ProjectService projectService,
                          EmployeeService employeeService) {
        this.companyService = companyService;
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return view("index");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        boolean areImported = this.companyService.areImported()
                && this.projectService.areImported()
                && this.employeeService.areImported();
        modelAndView.addObject("areImported", areImported);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
