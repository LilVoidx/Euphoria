package soul.euphoria.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private UserService userService;

    @GetMapping("/artistregistration")
    public String showArtistRegistrationForm(Model model) {
        model.addAttribute("artistForm", new ArtistForm());
        model.addAttribute("genres", Genre.values());
        return "artist-registration";
    }


    @PostMapping("/artistregistration")
    public ResponseEntity<String> registerAsArtist(@AuthenticationPrincipal UserDetailsImpl userDetails, @ModelAttribute ArtistForm artistForm) {
        User user = userService.getUserById(userDetails.getUserId());
        userService.registerAsArtist(user, artistForm);
        return new ResponseEntity<>("Registered as artist successfully", HttpStatus.CREATED);
    }
}
