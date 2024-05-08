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
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.user.User;
import soul.euphoria.services.user.UserService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);


    @GetMapping("/profile/{username}")
    public String userProfile(Model model, @PathVariable String username) {
        Optional<User> optionalUser = userService.findByUserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Convert User entity to UserDTO
            UserDTO userDTO = UserDTO.from(user);
            model.addAttribute("user", userDTO);
            return "user_account/profile_page";
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }

    @GetMapping("/profile/{username}/edit")
    public String editProfile(Model model, @PathVariable String username) {
        logger.debug("GET request received for profile edit page for username: {}", username);
        Optional<User> optionalUser = userService.findByUserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Convert User entity to UserDTO
            UserDTO userDTO = UserDTO.from(user);
            // Get the current user's form
            UserForm userForm = userService.convertUserToForm(user);
            model.addAttribute("user", userDTO);
            // Pass the userForm to the view
            model.addAttribute("userForm", userForm);
            return "user_account/edit_profile_page";
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }


    @PostMapping("/profile/{username}/edit")
    public String editProfile(@Valid @ModelAttribute("userForm") UserForm userForm,
                              BindingResult bindingResult,
                              @PathVariable String username,
                              @RequestParam("profilePicture") MultipartFile profilePicture) {
        logger.debug("POST request received for profile edit for username: {}", username);
        if (bindingResult.hasErrors()) {
            logger.debug("Form validation failed");
            return "user_account/edit_profile_page";
        }
        try {
            // Update user info
            userService.updateUser(username, userForm, profilePicture);
            logger.debug("User info updated successfully");
            // Redirect to the updated profile page using the new username
            return "redirect:/profile/" + userForm.getUsername();
        } catch (Exception e) {
            logger.error("Error updating user info", e);
            return "redirect:/error";
        }
    }

}
