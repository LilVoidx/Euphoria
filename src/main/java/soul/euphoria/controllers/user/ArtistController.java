package soul.euphoria.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.user.UserService;

@Controller
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/artistregistration")
    public String showArtistRegistrationForm(Model model) {
        model.addAttribute("artistForm", new ArtistForm());
        model.addAttribute("genres", Genre.values());
        return "user_account/artist-registration";
    }

    @PostMapping("/artistregistration")
    public String registerAsArtist(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute ArtistForm artistForm) {
        try {
            User user = userService.getUserById(userDetails.getUserId());
            userService.registerAsArtist(user, artistForm);
            return "redirect:/";
        } catch (Exception e) {
            logger.error("Error registering artist: {}", e.getMessage());
            return "redirect:/error";
        }
    }
}
