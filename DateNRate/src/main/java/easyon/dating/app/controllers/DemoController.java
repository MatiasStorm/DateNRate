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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class DemoController {
    private final UserService userService;
    private final MessageService messageService;
    private final RatingService ratingService;
    private final UserRatingService userRatingService;
    private final FavoriteService favoriteService;
    private final TagService tagService;
    private final UserTagService userTagService;

    public DemoController(UserService userService, RatingService ratingService, MessageService messageService, UserRatingService userRatingService, FavoriteService favoriteService, TagService tagService, UserTagService userTagService) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.messageService = messageService;
        this.userRatingService = userRatingService;
        this.favoriteService = favoriteService;
        this.tagService = tagService;
        this.userTagService = userTagService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String loginUser(WebRequest request) {
        //Retrieve values from HTML form via WebRequest
        String email = request.getParameter("username");
        String pwd = request.getParameter("password");

        User user = userService.login(email, pwd);
        setSessionInfo(request, user);

        return "redirect:/userProfile?userId=" + user.getUserId();
    }

    @PostMapping("/createUser/submit")
    public String createUserSubmit(User user, Model model, WebRequest request) {
        UserFormError userFormError = userService.getUserFormError(user);
        if(userFormError.containsErrors()){
            model.addAttribute("errors", userFormError);
            model.addAttribute("user", user);
            model.addAttribute("title", "Opret Bruger");
            model.addAttribute("postEndpoint", "/createUser/submit");
            return "/createUser";
        }
        User newUser = userService.createUser(user);
        setSessionInfo(request, newUser);
        return "redirect:/userProfile?userId=" + newUser.getUserId();
    }

    @GetMapping("/createUser")
    public String createUser(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("errors", new UserFormError());
        model.addAttribute("title", "Opret Bruger");
        model.addAttribute("postEndpoint", "/createUser/submit");
        model.addAttribute("passwordError", true);
        return "createUser";
    }


    @PostMapping("/createMessage")
    public String createMessageSubmit(Message message) {
        messageService.createMessage(message);
        return "redirect:/messages?active=" + message.getRecieverId();
    }

    @GetMapping("/messages")
    public String messages(@RequestParam(required = false, name = "active") Integer activeUserId, WebRequest request, Model model) {
        User loggedInUser = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (loggedInUser == null) { // If your aren't logged in, redirect to index.html
            return "redirect:/";
        }
        int recieverId = loggedInUser.getUserId();

        List<User> conversationUsers;
        conversationUsers = messageService.getConversationUsers(recieverId);
        if (conversationUsers.size() == 0 && activeUserId == null) { // If you don't have any messages, and not send any, display No messges page.
            return "noMessages";
        }
        if (activeUserId == null) { // Set activeUserId as the first user, as default.
            activeUserId = conversationUsers.get(0).getUserId();
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
    public String userProfile(@RequestParam int userId, UserTag userTag, WebRequest request, Model model, Favorite favorite, UserRating userRating) {
        User loggedInUser = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (loggedInUser == null) { // If your aren't logged in, redirect to index.html
            return "redirect:/";
        }
        List<Rating> ratings = ratingService.getRatings();
        favorite.setFavoriteUserId(userId);
        favorite.setUserId(loggedInUser.getUserId());
        model.addAttribute("isMyProfile", loggedInUser.getUserId() == userId);
        model.addAttribute("currentUser", loggedInUser);
        model.addAttribute("favorite", favorite);
        model.addAttribute("ratings", ratings);
        model.addAttribute("user", userService.getUser(userId));
        model.addAttribute("userRatings", userRatingService.getEmptyUserRatingArray(ratings.size()));
        model.addAttribute("userRating", userRating);
        List<Rating> ratingList = ratingService.getRatings();
        model.addAttribute("ratingList", ratingList);

        model.addAttribute("userTag", userTag);
        model.addAttribute("tagsList", tagService.getListOfTags());
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
    public String postFavorite(Favorite favorite) {
        favoriteService.addToFavorites(favorite);
        return "redirect:/userProfile?userId=" + favorite.getFavoriteUserId();
    }

    @GetMapping("/test")
    public String test(Model model, UserTag userTag) {
        int userId = 13;
        model.addAttribute("activeTags", userTagService.getActiveTagList(userId));
//        model.addAttribute("inactiveTags", userTagService.getInactiveTagList(userId));
        return "/test";
    }

    @PostMapping("/userTagPost")
    public String addTagToUser(UserTag userTag) {
        int userId = userTag.getUserId();
        userTagService.addTagToUser(userTag, userId);
        return "redirect:/userProfile?userId=" + userTag.getUserId();
    }

    @GetMapping("/ratingTest")
    public String ratingTest(UserRating userRating, Model model) {
        int currentUserId = 1;
        model.addAttribute("userRating", userRating);
        List<Rating> ratingList = ratingService.getRatings();
        model.addAttribute("ratingList", ratingList);

        return "/ratingTest";
    }

    @PostMapping("/postRating")
    public String postRating(UserRating userRating, Model model) {
        int currentUserId = 1;
        model.addAttribute("userRating", userRating);
        userRatingService.createUserRating(userRating);
        return "redirect:/userProfile?userId=" + userRating.getTargetUserId();
    }

    @GetMapping("/fileTest")
    public String fileTest(){
        return "fileTest";
    }

    @PostMapping("/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("file") MultipartFile file, WebRequest request) throws IOException {
        User loggedInUser = (User) request.getAttribute("user", WebRequest.SCOPE_SESSION);
        if (loggedInUser == null) { // If your aren't logged in, redirect to index.html
            return "redirect:/";
        }
        User user = userService.uploadProfilePicture(file, loggedInUser);
        setSessionInfo(request, user);
        return "redirect:/userProfile?userId=" + user.getUserId();
    }

}