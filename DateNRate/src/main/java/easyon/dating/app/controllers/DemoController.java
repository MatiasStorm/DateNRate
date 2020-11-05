package easyon.dating.app.controllers;

import easyon.dating.app.models.Message;
import easyon.dating.app.models.User;
import easyon.dating.app.services.UserService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.util.List;

@Controller
public class DemoController {
    private final UserService userService;

    public DemoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<User> user = userService.getUsers();
        model.addAttribute("user", user.get(0));
        return "index";
    }

    @GetMapping("/createUser")
    public String createUser(Model model, User user){
        return "createUser";
    }

    @PostMapping("/createUser/submit")
    public String createUserSubmit(User user){
        userService.createUser(user);
        return "redirect:/";
    }

    @GetMapping("/createMessage")
    public String createMessage(Message message){
        return "createMessage";
    }

    @PostMapping("/createMessage/submit")
    public String createMessageSubmit(Message message){
        return "redirect:/messages";
    }

    @GetMapping("/messages")
    public String messages(@RequestParam Integer id){
        return "messages";
    }

}
