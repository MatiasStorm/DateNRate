package easyon.dating.app.controllers;

import easyon.dating.app.models.Message;
import easyon.dating.app.models.User;
import easyon.dating.app.services.MessageService;
import easyon.dating.app.services.RatingService;
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
    private final MessageService messageService;
    private  final RatingService ratingService;

    public DemoController(UserService userService, RatingService ratingService, MessageService messageService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.messageService = messageService;
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
    public String createMessage(@RequestParam int from, @RequestParam int to, Message message){
        message.setRecieverId(to);
        message.setSenderId(from);
        return "createMessage";
    }

    @PostMapping("/createMessage/submit")
    public String createMessageSubmit(Message message){
        messageService.createMessage(message);
        return "redirect:/messages?id=4";
    }

    @GetMapping("/messages")
    public String messages(@RequestParam Integer userId, Model model){
        model.addAttribute("messages", messageService.getUserMessages(userId));
        return "messages";
    }

    @GetMapping("/userProfile")
    public String userProfile(Model model){
        model.addAttribute("ratings", ratingService.getRatings());
        return "userProfile";
    }

}
