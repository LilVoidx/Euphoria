package soul.euphoria.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.UserForm;
import soul.euphoria.services.user.RegisterService;

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
                               BindingResult bindingResult,
                               @RequestParam("profilePicture") MultipartFile profilePicture) {
        if (bindingResult.hasErrors()) {
            return "auth/sign_up_page";
        }
        try {
            logger.info("User registration details: {}", userForm.toString());
            //Save Profile Picture
            userForm.setProfilePicture(profilePicture);
            // Register the user
            registerService.registerUser(userForm);
            logger.info("User registered successfully");
            return "redirect:/confirm-account";

        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("confirmPassword", "error.userForm", e.getMessage());
            return "auth/sign_up_page";

        } catch (Exception e) {
            logger.error("Error occurred during user registration", e);
            return "redirect:/error";
        }
    }

    @GetMapping("/confirm-account")
    public String confirmAccount() {
        return "auth/confirm_account_page";
    }
}