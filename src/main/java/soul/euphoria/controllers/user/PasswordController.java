package soul.euphoria.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import soul.euphoria.services.mail.ConfirmationService;
import soul.euphoria.services.user.UserService;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationService confirmationService;

    @GetMapping("/forgot")
    public String showForgotPasswordForm() {
        return "/auth/password/forgot_password";
    }

    @PostMapping("/forgot")
    public String submitForgotPasswordForm(@RequestParam("email") String email, Model model) {
        try {
            userService.resetPassword(email);
            model.addAttribute("message", "A password reset email has been sent to your email address.");
            return "/auth/password/forgot_password_confirmation";
        } catch (Exception e) {
            logger.error("Error processing forgot password form: {}", e.getMessage());
            model.addAttribute("errorMessage", "An error occurred while processing your request. Please try again later.");
            return "redirect:/error";
        }
    }

    @GetMapping("/reset")
    public String showResetPasswordForm(@RequestParam("code") String code, Model model) {
        // Check if code is valid, show reset password form if valid
        model.addAttribute("code", code);
        return "/auth/password/reset_password";
    }

    @PostMapping("/reset")
    public String submitResetPasswordForm(@RequestParam("code") String code, @RequestParam("password") String password, Model model) {
        try {
            confirmationService.resetPassword(code, password);
            model.addAttribute("message", "Your password has been reset successfully.");
            return "/auth/password/reset_password_confirmation";
        } catch (Exception e) {
            logger.error("Error processing reset password form: {}", e.getMessage());
            model.addAttribute("errorMessage", "An error occurred while processing your request. Please try again later.");
            return "redirect:/error";
        }
    }
}
