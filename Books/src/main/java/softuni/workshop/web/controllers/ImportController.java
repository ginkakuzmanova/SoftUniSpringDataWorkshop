package softuni.workshop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/import")
public class ImportController extends BaseController {

    @GetMapping("/xml")
    public ModelAndView importXml() {
        ModelAndView modelAndView = new ModelAndView("xml/import-xml");
        modelAndView.addObject("areImported",
                new boolean[]{false, false, false});
        return modelAndView;
    }

    @GetMapping("/companies")
    public ModelAndView importCompanies(){
        return view("xml/import-companies");
    }
    @GetMapping("/projects")
    public ModelAndView importProjects(){
        return view("xml/import-projects");
    }
    @GetMapping("/companies")
    public ModelAndView importEmployees(){
        return view("xml/import-employees");
    }


}
