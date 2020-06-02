package softuni.workshop.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {

    @GetMapping("/users/register")
    public ModelAndView register(){
        return view("user/register");
    }

    @GetMapping("/users/login")
    public ModelAndView login(){
        return view("user/login");
    }
}
