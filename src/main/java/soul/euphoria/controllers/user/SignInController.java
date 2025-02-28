package soul.euphoria.controllers.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public String showSignInForm(Model model, HttpServletRequest request) {
        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }
        return "auth/sign_in_page";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/signIn?logout";
    }
}
