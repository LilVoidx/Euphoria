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
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.user.Artist;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.user.ArtistService;
import soul.euphoria.services.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class ArtistController {

    private static final Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private SongService songService;

    @GetMapping("/artists/{username}/register")
    public String showArtistRegistrationForm(Model model, @PathVariable String username, HttpServletRequest request) {
        Optional<UserDTO> optionalUser = userService.findByUserName(username);

        if (optionalUser.isPresent()) {
            UserDTO userDTO = optionalUser.get();
            model.addAttribute("user", userDTO);
            model.addAttribute("artistForm", new ArtistForm());
            model.addAttribute("genres", Genre.values());
            logger.info("User: {}", userDTO);
            return "user_account/artist-registration";
        } else {
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
            return "forward:/error";
        }
    }

    @PostMapping("/artists/register")
    public String registerAsArtist(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @ModelAttribute ArtistForm artistForm, HttpServletRequest request) {
        try {
            User user = userService.getCurrentUser(userDetails.getUserId());
            artistService.registerAsArtist(user, artistForm);
            return "redirect:/users/profile/" + user.getUsername();
        } catch (Exception e) {
            logger.error("Error registering artist: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @GetMapping("/artist/{username}")
    public String artistProfile(Model model, @PathVariable String username, HttpServletRequest request,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Optional<UserDTO> optionalUser = userService.findByUserName(username);
            if (optionalUser.isPresent()) {
                User user = userService.getCurrentUser(userDetails.getUserId());
                UserDTO userDTO = optionalUser.get();
                // Check if the user is an artist
                if (user.getArtist() != null) {
                    ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                    // Fetch artist-related songs
                    List<SongDTO> artistSongDTOs = songService.getSongsByArtist(user.getArtist());
                    model.addAttribute("user", userDTO);
                    model.addAttribute("artist", artistDTO);
                    model.addAttribute("artistSongs", artistSongDTOs);
                    return "user_account/artist_page";
                } else {
                    // Forward the request to the error controller
                    request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                    return "forward:/error";
                }
            } else {
                // Forward the request to the error controller
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
                return "forward:/error";
            }
        } catch (Exception e) {
            logger.error("Error fetching artist profile: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @GetMapping("/artist/{username}/edit")
    public String editArtist(Model model, @PathVariable String username, HttpServletRequest request,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Optional<UserDTO> optionalUser = userService.findByUserName(username);
            if (optionalUser.isPresent()) {
                UserDTO userDTO = optionalUser.get();
                User user = userService.getCurrentUser(userDetails.getUserId());
                // Check if the user is an artist
                if (user.getArtist() != null) {
                    ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                    Artist artist = user.getArtist();
                    ArtistForm artistForm = artistService.convertArtistToForm(artist);
                    model.addAttribute("user", userDTO);
                    model.addAttribute("artist", artistDTO);
                    model.addAttribute("genres", Genre.values());
                    model.addAttribute("artistForm", artistForm);
                    return "user_account/edit_artist_page";
                } else {
                    // Forward the request to the error controller
                    request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                    return "forward:/error";
                }
            } else {
                // Forward the request to the error controller
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                return "forward:/error";
            }
        } catch (Exception e) {
            logger.error("Error editing artist: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @PostMapping("/artist/{username}/edit")
    public String updateArtist(@PathVariable String username, @Valid @ModelAttribute ArtistForm artistForm, HttpServletRequest request) {
        try {
            Optional<UserDTO> optionalUser = userService.findByUserName(username);
            if (optionalUser.isPresent()) {
                artistService.updateArtist(username, artistForm);
                return "redirect:/artist/" + username;
            } else {
                // Forward the request to the error controller
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                return "forward:/error";
            }
        } catch (Exception e) {
            logger.error("Error updating artist: {}", e.getMessage());
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }
}
