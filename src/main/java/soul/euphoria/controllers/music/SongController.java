package soul.euphoria.controllers.music;

import javassist.NotFoundException;
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
import soul.euphoria.services.user.ArtistService;
import soul.euphoria.services.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @Autowired
    private ArtistService artistService;

    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping("/song/upload")
    public String showUploadSongForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails,HttpServletRequest request) {
        Optional<UserDTO> optionalUser = Optional.ofNullable(userService.getUserById(userDetails.getUserId()));
        if (optionalUser.isPresent()){
            UserDTO userDTO = optionalUser.get();
            User user = userService.getCurrentUser(userDetails.getUserId());
        if (user.getArtist() != null) {
            ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
            model.addAttribute("user", userDTO);
            model.addAttribute("artist", artistDTO);
            model.addAttribute("genres", Genre.values());
            model.addAttribute("userId", userDetails.getUserId());
            model.addAttribute("genres", Genre.values());
            model.addAttribute("songForm", new SongForm());
            return "music/music_upload_page";

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
    public String showSongPage(@PathVariable("songId") Long songId,
                               Model model,
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               HttpServletRequest request) {
        try {
            // Get current user
            Optional<UserDTO> optionalUser = Optional.ofNullable(userService.getUserById(userDetails.getUserId()));
            if (optionalUser.isPresent()) {
                UserDTO userDTO = optionalUser.get();

                // Get current song
                Song song = songService.getCurrentSong(songId);
                if (song == null) {
                    throw new NotFoundException("Song not found with ID: " + songId);
                }
                SongDTO songDTO = songService.findById(songId);

                // Get current song's artist
                ArtistDTO artistDTO = artistService.getArtistBySongId(songId);
                if (artistDTO == null) {
                    throw new NotFoundException("Artist not found for song with ID: " + songId);
                }

                // Get current song's artist's user info
                UserDTO artistUserDTO = userService.getUserDTOByArtistId(artistDTO.getArtistId());
                if (artistUserDTO == null) {
                    throw new NotFoundException("User not found for artist with ID: " + artistDTO.getArtistId());
                }

                // Get artist's songs
                List<SongDTO> artistSongDTOs = songService.getSongsByArtist(song.getArtist());

                // Add attributes to the model
                model.addAttribute("userArtist", artistUserDTO);
                model.addAttribute("songsArtist", artistSongDTOs);
                model.addAttribute("songArtist", artistDTO);
                model.addAttribute("song", songDTO);
                model.addAttribute("user", userDTO);
                return "music/song_page";
            }
        } catch (NotFoundException e) {
            logger.error("Error while fetching song data: " + e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
            return "forward:/error";
        } catch (Exception e) {
            logger.error("Error while processing request: " + e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
        return "forward:/error";
    }

    @PostMapping("/song/{songId}/delete")
    public ResponseEntity<String> deleteSong(@PathVariable Long songId) {
        try {
            songService.deleteSong(songId);
            return ResponseEntity.ok("Song deleted successfully!");
        } catch (NotFoundException e) {
            logger.error("Song not found with ID: " + songId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found!");
        } catch (Exception e) {
            logger.error("Failed to delete song with ID: " + songId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete song!");
        }
    }


    @PostMapping("/song/{song-id}/favorite")
    @ResponseBody
    public ResponseEntity<Object> favorite(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                           @PathVariable("song-id") Long songId) {
        Long userId = userDetails.getUserId();
        SongDTO result = songService.favorite(userId, songId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to favorite/unfavorite the song.");
        }
    }

    @GetMapping("/song/data/{songId}")
    @ResponseBody
    public SongDTO getSongData(@PathVariable Long songId) {
        return songService.findById(songId);
    }

    @GetMapping("/song/trending")
    @ResponseBody
    public SongDTO getTrendingSong() {
        return songService.getTrendingSong();
    }



    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(Genre.values());
    }
}
