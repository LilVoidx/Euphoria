package soul.euphoria.controllers.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soul.euphoria.dto.forms.ArtistForm;
import soul.euphoria.dto.infos.ArtistDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.user.UserService;

import java.util.Optional;


@Controller
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/artists/{username}/register")
    public String showArtistRegistrationForm(Model model, @PathVariable String username) {
        Optional<User> optionalUser = userService.findByUserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = UserDTO.from(user);
            model.addAttribute("user", userDTO);
            model.addAttribute("artistForm", new ArtistForm());
            model.addAttribute("genres", Genre.values());
            logger.info("User: {}", userDTO);
            return "user_account/artist-registration";
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }



    @PostMapping("/artists/register")
    public String registerAsArtist(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute ArtistForm artistForm) {
        try {
            User user = userService.getUserById(userDetails.getUserId());
            userService.registerAsArtist(user, artistForm);
            return "redirect:/profile/" + user.getUsername();
        } catch (Exception e) {
            logger.error("Error registering artist: {}", e.getMessage());
            return "redirect:/error";
        }
    }
    @GetMapping("/artist/{username}")
    public String artistProfile(Model model, @PathVariable String username) {
        Optional<User> optionalUser = userService.findByUserName(username);

        //TODO: CHECK WHY ARTIST PROFILE IS RETURNING ERROR 500 BECAUSE OF DTO AFTER SONG CREATION

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = UserDTO.from(user);
            // Check if the user is an artist
            if (user.getArtist() != null) {
                ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                model.addAttribute("user", userDTO);
                model.addAttribute("artist", artistDTO);
                return "user_account/artist_page";
            } else {
                model.addAttribute("errorMessage", "User is not an artist");
                return "error/error_page";
            }
        } else {
            model.addAttribute("errorMessage", "User not found");
            return "error/error_page";
        }
    }
}
