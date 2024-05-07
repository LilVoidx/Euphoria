package soul.euphoria.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.user.UserService;

import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Retrieve user by userId
        User user = userService.getUserById(userDetails.getUserId());
        if (user != null) {
            // Convert User entity to UserDTO
            UserDTO userDTO = UserDTO.from(user);
            model.addAttribute("user", userDTO);
            return "user_account/home_page";
        } else {
            // User not found, handle error
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }
    @GetMapping("/files/img/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response){
        fileStorageService.writeFileToResponse(fileName, response);
    }
}
