package soul.euphoria.controllers.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.AlbumForm;
import soul.euphoria.dto.infos.AlbumDTO;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.music.Album;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.music.AlbumService;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.user.ArtistService;
import soul.euphoria.services.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class AlbumController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumService albumService;

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping("/albums/create")
    public String showAlbumCreationPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
        Optional<User> optionalUser = userService.findByUserName(userDetails.getUsername());
        logger.info("User: " + userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDTO userDTO = UserDTO.from(user);
            // Check if the user is an artist
            if (user.getArtist() != null) {
                //ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                model.addAttribute("userId", userDetails.getUserId());
                model.addAttribute("albumForm", new AlbumForm());

                return "music/album_create_page";
            }else {
                // Forward the request to the error controller
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                return "forward:/error";
            }
        } else {
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @PostMapping("/albums/create")
    public String createAlbum(@Valid @ModelAttribute AlbumForm albumForm,
                              @RequestParam("coverImage") MultipartFile coverImage,
                              @RequestParam("userId") Long userId,
                              Model model) {
        try {
            System.out.println("USER" + userId.toString() );
            System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
            Album album = albumService.createAlbum(albumForm, coverImage, userId);
            model.addAttribute("userId", userId);
            System.out.println("HELPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
            System.out.println("Album Id: " + album.getAlbumId());
            return "redirect:/albums/" + album.getAlbumId();
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/albums/{albumId}")
    public String showAlbumDetails(@PathVariable Long albumId, Model model) {
        AlbumDTO album = albumService.getAlbumDetails(albumId);
        List<SongDTO> songs = albumService.getAlbumSongs(albumId);
        model.addAttribute("album", album);
        model.addAttribute("songs", Objects.requireNonNullElse(songs, ""));
        return "music/album_page";
    }
}
