package easyon.dating.app.controllers;

import easyon.dating.app.models.Message;
import easyon.dating.app.models.Rating;
import easyon.dating.app.models.User;
import easyon.dating.app.models.UserRating;
import easyon.dating.app.services.MessageService;
import easyon.dating.app.services.RatingService;
import easyon.dating.app.services.UserRatingService;
import easyon.dating.app.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class DemoController {
    private final UserService userService;
    private final MessageService messageService;
    private  final RatingService ratingService;
    private final UserRatingService userRatingService;

    public DemoController(UserService userService, RatingService ratingService, MessageService messageService, UserRatingService userRatingService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.messageService = messageService;
        this.userRatingService = userRatingService;
    }

    @GetMapping("/")
    public String index(Model model){
        List<User> user = userService.getListOfUsers();
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


    @PostMapping("/createMessage")
    public String createMessageSubmit(Message message){
        messageService.createMessage(message);
        return "redirect:/messages?active=" + message.getRecieverId();
    }

    @GetMapping("/messages")
    public String messages(@RequestParam(required = false, name = "active") Integer activeUserId, Model model){
        // TODO Move most of this to service and create a class which will contain all conversation information.

        int recieverId = 1; // CHANGE THIS, when implementing sessions, use loggedin users id

        List<User> conversationUsers;
        if(activeUserId == null){
            conversationUsers = messageService.getSenders(recieverId);
            activeUserId = conversationUsers.get(0).getUserId();
        }
        else {
            conversationUsers = messageService.getSenders(recieverId, activeUserId);
        }

        if(conversationUsers.size() == 0){
            return "noMessages";
        }

        List<Message> activeConversation = messageService.getConversation(recieverId, activeUserId);
        User activeUser = userService.getUser(activeUserId);
        model.addAttribute("conversationUsers", conversationUsers);
        model.addAttribute("activeConversation", activeConversation);
        model.addAttribute("myId", recieverId);
        model.addAttribute("activeUser", activeUser);

        Message newMessage = new Message();
        newMessage.setRecieverId(activeUserId);
        newMessage.setSenderId(recieverId);
        model.addAttribute("newMessage", newMessage);

        return "messages";
    }


    @GetMapping("/userProfile")
    public String userProfile(@RequestParam int userId, Model model){
        List<Rating> ratings = ratingService.getRatings();
        model.addAttribute("ratings", ratings);
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("userRatings",userRatingService.getEmptyUserRatingArray(ratings.size()));
        return "userProfile";
    }

    @PostMapping("/userProfile/createUserRating")
    public String createUserRating(UserRating userRating) {
        userRatingService.createUserRating(userRating);
        return "redirect:/userProfile?userId=4";
    }


    @PostMapping("/search")
    public String postSearch(WebRequest request, Model model){

        String search = request.getParameter("searchParameter");
       List<User> searchList = userService.searchUser(search);
       model.addAttribute("searchList", searchList);
                return "/search";


    }

    @GetMapping ("/search")
    public String search(){
        return "/search";
    }




}
