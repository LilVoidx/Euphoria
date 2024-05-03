package soul.euphoria.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import soul.euphoria.services.mail.ConfirmationService;

@Controller
public class ConfirmController {

    @Autowired
    private ConfirmationService confirmationService;

    @GetMapping("/confirm/{code}")
    public String confirmUser(@PathVariable("code") String code, Model model) {
        boolean isConfirmed = confirmationService.confirmUser(code);

        if (isConfirmed) {
            return "redirect:/confirmation-success";
        } else {
            // Redirect to the central error page with an error message
            model.addAttribute("errorMessage", "Failed to confirm account. Please try again.");
            return "redirect:/error";
        }
    }

    @GetMapping("/confirmation-success")
    public String confirmSuccess() {
        return "auth/sign_in_page";
    }
}
