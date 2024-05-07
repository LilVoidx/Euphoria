package soul.euphoria.controllers.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.user.UserService;
import soul.euphoria.services.user.impl.UserServiceImpl;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String userProfile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Retrieve user by userId
        User user = userService.getUserById(userDetails.getUserId());
        if (user != null) {
            // Convert User entity to UserDTO
            UserDTO userDTO = UserDTO.from(user);
            model.addAttribute("user", userDTO);
            return "user_account/profile_page";
        } else {
            // User not found, handle error
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }
}
