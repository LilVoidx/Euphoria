package soul.euphoria.controllers.music;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.dto.infos.ArtistDTO;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.User;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping("/song/upload")
    public String showUploadSongForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.getUserById(userDetails.getUserId());
        UserDTO userDTO = UserDTO.from(user);
        if (user.getArtist() != null) {
            ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
            model.addAttribute("user", userDTO);
            model.addAttribute("artist", artistDTO);
            model.addAttribute("genres", Genre.values());
            model.addAttribute("userId", userDetails.getUserId());
            model.addAttribute("genres", Genre.values());
            model.addAttribute("songForm", new SongForm());
        }
        return "music/music_upload_page";
    }


    @PostMapping("/song/upload")
    public ResponseEntity<String> uploadSong(@ModelAttribute("songForm") SongForm songForm,
                             @RequestParam("songFile") MultipartFile songFile,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("userId") Long userId,
                             Model model) {
        try {
            songService.uploadSong(songForm, songFile, imageFile, userId);
            model.addAttribute("userId", userId);
            return ResponseEntity.ok("Song uploaded successfully!");
        } catch (Exception e) {
            logger.error("Failed to upload song",e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload song!");
        }
    }

    @GetMapping("/song/{songId}")
    public String showSongPage(@PathVariable("songId") Long songId, Model model, HttpServletRequest request) {
        Song song = songService.findById(songId);
        if (song != null) {
            SongDTO songDTO = SongDTO.from(song);
            model.addAttribute("song", songDTO);
            return "music/song_page";
        }else {
            // Forward the request to the error controller
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
            return "forward:/error";
        }
    }

    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(Genre.values());
    }
}
