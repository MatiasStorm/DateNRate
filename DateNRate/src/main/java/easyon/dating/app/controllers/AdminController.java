package easyon.dating.app.controllers;

import easyon.dating.app.models.User;
import easyon.dating.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AdminController {
    private final String adminPassword = "admin";
    private final String adminUsername = "admin";
    private final UserService userService;

    public AdminController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/admin/login")
    public String adminLogin(){
        return "admin/adminLogin";
    }

    @PostMapping("/admin/login/submit")
    public String adminLoginSubmit(WebRequest request){
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        if(username.equals(adminUsername) && password.equals(adminPassword)){
            request.setAttribute("admin", true, WebRequest.SCOPE_SESSION);
            return "redirect:/admin";
        }
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(Model model, WebRequest request){
        if(request.getAttribute("admin", WebRequest.SCOPE_SESSION) == null){
            return "redirect:/admin/login";
        }
        model.addAttribute("users", userService.getListOfUsers());
        return "admin/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(WebRequest request){
        if(request.getAttribute("admin", WebRequest.SCOPE_SESSION) == null){
            return "redirect:/";
        }

        int userId = Integer.parseInt(request.getParameter("userId"));
        userService.deleteUser(userId);

        return "redirect:/admin";
    }
}
