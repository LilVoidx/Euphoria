package soul.euphoria.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.services.user.RegisterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignUpController {

    private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private RegisterService registerService;

    @GetMapping("/signUp")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "auth/sign_up_page";
    }

    @PostMapping("/signUp")
    public String registerUser(@Valid @ModelAttribute("userForm") UserForm userForm,
                               BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "auth/sign_up_page";
        }
        try {
            logger.info("User registration details: {}", userForm.toString());
            // Register the user without profile picture
            Long registeredUserId = registerService.registerUser(userForm).getUserId();
            logger.info("User registered successfully");

            // Redirect to profile picture upload page with the user ID
            return "redirect:/signUp/ProfilePicture?userId=" + registeredUserId;
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("confirmPassword", "error.userForm", e.getMessage());
            return "auth/sign_up_page";
        } catch (Exception e) {
            logger.error("Error occurred during user registration", e);
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @GetMapping("/signUp/ProfilePicture")
    public String showProfilePictureForm(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "auth/profile_picture_page";
    }

    @PostMapping("/signUp/uploadProfilePicture")
    public String uploadProfilePicture(@RequestParam("userId") Long userId,
                                       @RequestParam("profilePicture") MultipartFile profilePicture, HttpServletRequest request) {
        try {
            // Upload the profile picture and associate it with the user
            registerService.uploadProfilePicture(userId, profilePicture);
            return "redirect:/confirm-account";
        } catch (Exception e) {
            logger.error("Error occurred during profile picture upload", e);
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @GetMapping("/confirm-account")
    public String confirmAccount() {
        return "auth/confirm_account_page";
    }
}
