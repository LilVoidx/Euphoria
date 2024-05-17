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
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.services.mail.ConfirmationService;
import soul.euphoria.services.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/password")
public class PasswordController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationService confirmationService;

    @GetMapping("/forgot")
    public String showForgotPasswordForm(Model model) {
        model.addAttribute("forgotPasswordMessage", "");
        model.addAttribute("forgotPasswordErrorMessage", "");
        return "auth/password/forgot_password";
    }

    @PostMapping("/forgot")
    public String submitForgotPasswordForm(@RequestParam("email") String email, Model model, HttpServletRequest request) {
        try {
            // Check if email exists in the database
            Optional<UserDTO> user = userService.findByEmail(email);
            if (user.isEmpty()) {
                model.addAttribute("forgotPasswordErrorMessage", "Email not found. Please enter a valid email address.");
                model.addAttribute("forgotPasswordMessage", "");
            } else {
                // Email exists, proceed with sending reset password email
                userService.resetPassword(email);
                model.addAttribute("forgotPasswordMessage", "A password reset email has been sent to your email address.");
                model.addAttribute("forgotPasswordErrorMessage", "");
            }
        } catch (Exception e) {
            logger.error("Error processing forgot password form: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
        return "auth/password/forgot_password";
    }

    @GetMapping("/reset")
    public String showResetPasswordForm(@RequestParam("code") String code, Model model) {
        // Check if code is valid, show reset password form if valid
        model.addAttribute("resetPasswordMessage", "");
        model.addAttribute("resetPasswordErrorMessage", "");

        model.addAttribute("code", code);
        return "/auth/password/reset_password";
    }

    @PostMapping("/reset")
    public String submitResetPasswordForm(@RequestParam("code") String code, @RequestParam("password") String password, Model model, HttpServletRequest request) {
        try {
            confirmationService.resetPassword(code, password);
            model.addAttribute("resetPasswordMessage", "Your password has been reset successfully.");
            model.addAttribute("resetPasswordErrorMessage", "");
            model.addAttribute("code", "");
        } catch (Exception e) {
            logger.error("Error processing reset password form: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
        return "/auth/password/reset_password";
    }
}
