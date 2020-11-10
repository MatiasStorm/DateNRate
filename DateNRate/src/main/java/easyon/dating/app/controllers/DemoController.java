package easyon.dating.app.controllers;

import easyon.dating.app.models.*;
import easyon.dating.app.repository.TagDAO;
import easyon.dating.app.services.*;
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
    private final RatingService ratingService;
    private final UserRatingService userRatingService;
    private final FavoriteService favoriteService;
    private final TagService tagService;

    public DemoController(UserService userService, RatingService ratingService, MessageService messageService, UserRatingService userRatingService, FavoriteService favoriteService, TagService tagService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.messageService = messageService;
        this.userRatingService = userRatingService;
        this.favoriteService = favoriteService;
        this.tagService = tagService;
    }

    @GetMapping("/")
    public String index(Model model, User user) {
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(WebRequest request) {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("username");
        String pwd = request.getParameter("password");

        User user = userService.login(email, pwd);
        setSessionInfo(request, user);

        // Go to to page dependent on role
        return "redirect:/userProfile?userId=" + user.getUserId();
    }

    @PostMapping("/createUser")
    public String createUserSubmit(User user, WebRequest request) {
        userService.createUser(user);
        // TODO should redirect to user profile!
        setSessionInfo(request, user);
        return "redirect:/userProfile?userId=" + user.getUserId();
    }


    @PostMapping("/createMessage")
    public String createMessageSubmit(Message message) {
        messageService.createMessage(message);
        return "redirect:/messages?active=" + message.getRecieverId();
    }

    @GetMapping("/messages")
    public String messages(@RequestParam(required = false, name = "active") Integer activeUserId, WebRequest request, Model model) {
        // TODO Move most of this to service and create a class which will contain all conversation information.
        User loggedInUser = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        int recieverId = loggedInUser.getUserId();

        List<User> conversationUsers;
        if (activeUserId == null) {
            conversationUsers = messageService.getSenders(recieverId);
            activeUserId = conversationUsers.get(0).getUserId();
        } else {
            conversationUsers = messageService.getSenders(recieverId, activeUserId);
        }

        if (conversationUsers.size() == 0) {
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
    public String userProfile(@RequestParam int userId, Model model, Favorite favorite) {
        List<Rating> ratings = ratingService.getRatings();
        favorite.setFavoriteUserId(userId);
        favorite.setUserId(1);
        model.addAttribute("favorite", favorite);
        model.addAttribute("ratings", ratings);
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("userRatings", userRatingService.getEmptyUserRatingArray(ratings.size()));
        return "userProfile";
    }

    @PostMapping("/userProfile/createUserRating")
    public String createUserRating(UserRating userRating) {
        userRatingService.createUserRating(userRating);
        return "redirect:/userProfile?userId=4";
    }


    private void setSessionInfo(WebRequest request, User user) {
        // Place user info on session
        request.setAttribute("user", user, WebRequest.SCOPE_SESSION);
    }

    @PostMapping("/postSearch")
    public String postSearch(WebRequest request, Model model) {

        String search = request.getParameter("searchParameter");
        List<User> searchList = userService.searchUser(search);
        model.addAttribute("searchList", searchList);
        return "/search";

    }

    @GetMapping("/search")
    public String search() {
        return "/search";
    }

    @GetMapping("/favorite")
    public String favorite(Model model) {
        int currentUserId = 1;
        List<Favorite> favoritesList = favoriteService.getFavoriteList(currentUserId);
        List<User> favoriteAsUserList = favoriteService.getFavoritesAsUsers(currentUserId);
        model.addAttribute("favoritesList", favoritesList);
        model.addAttribute("favoritesAsUsersList", favoriteAsUserList);
        return "favorite";
    }

    @PostMapping("/postFavorite")
    public String postFavorite(Favorite favorite){
        favoriteService.addToFavorites(favorite);
        return "redirect:/userProfile?userId=" + favorite.getFavoriteUserId();
    }

    @GetMapping("/test")
    public String test(Model model){
        return "";
    }



@GetMapping ("/ratingTest")
public String ratingTest(Model model){
        int currentUserId = 1;
        List<Rating> ratingList = ratingService.getRatings();
        model.addAttribute("ratingList", ratingList);

    return "/ratingTest";
}

@PostMapping("/postRating")
    public String postRating(Rating rating) {
        ratingService.addToUserRating(rating);
        return "/ratingTest";
}

}