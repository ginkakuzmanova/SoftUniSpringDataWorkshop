package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.services.UserService;
import softuni.workshop.web.models.UserRegisterModel;

@Controller
@RequestMapping(path = "/users")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        return view("user/register");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return view("user/login");
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute UserRegisterModel userRegisterModel) {
        if (!userRegisterModel.getPassword()
                .equals(userRegisterModel.getConfirmPassword())) {
            return redirect("/users/register");
        }

        if(this.userService.userAlreadyExists(userRegisterModel.getUsername())){
            return redirect("/");
        }
        this.userService.registerUser(userRegisterModel);
        return redirect("/users/login");
    }
}
