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
import soul.euphoria.dto.forms.AlbumForm;
import soul.euphoria.dto.infos.AlbumDTO;
import soul.euphoria.dto.infos.ArtistDTO;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.models.music.Album;
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
import java.util.Optional;

@Controller
public class AlbumController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private SongService songService;


    private static final Logger logger = LoggerFactory.getLogger(AlbumController.class);

    @GetMapping("/albums/create")
    public String showAlbumCreationPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
        Optional<UserDTO> optionalUser = userService.findByUserName(userDetails.getUsername());
        logger.info("User: " + userDetails.getUsername());
        if (optionalUser.isPresent()) {
            User user = userService.getCurrentUser(userDetails.getUserId());
            UserDTO userDTO = optionalUser.get();
            // Check if the user is an artist
            if (user.getArtist() != null) {
                ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                model.addAttribute("user",userDTO);
                model.addAttribute("userId", userDTO.getUserId());
                model.addAttribute("artist",artistDTO);
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
            Album album = albumService.createAlbum(albumForm, coverImage, userId);
            model.addAttribute("userId", userId);
            System.out.println("Album Id: " + album.getAlbumId());
            return "redirect:/albums/" + album.getAlbumId();
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @GetMapping("/albums/{username}/album/{albumId}")
    public String showAlbumDetails(@PathVariable Long albumId,
                                   @PathVariable String username,
                                   Model model,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails,
                                   HttpServletRequest request) {
        AlbumDTO album = albumService.getAlbumDetails(albumId);
        Optional<UserDTO> optionalUser = userService.findByUserName(username);
        if (optionalUser.isPresent()) {
        User user = userService.getCurrentUser(userDetails.getUserId());
        if (user.getArtist() != null) {
            UserDTO userDTO = optionalUser.get();
            List<SongDTO> albumSongs = albumService.getAlbumSongs(albumId);
            List<SongDTO> artistSongDTOs = songService.getSongsByArtistAlbumNull(user.getArtist());
            model.addAttribute("user",userDTO);
            model.addAttribute("album", album);
            model.addAttribute("albumSongs", albumSongs);
            model.addAttribute("artistSongDTOs", artistSongDTOs);
            return "music/album_page";
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

    @PostMapping("/albums/{username}/album/{albumId}/addSong")
    public String addSongToAlbum(@RequestParam("songId") Long songId,
                                 @PathVariable String username,
                                 @PathVariable Long albumId) {
        try {
            albumService.addSongToAlbum(songId, albumId);
            return "redirect:/albums/" + albumId;
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PostMapping("/albums/{username}/album/{albumId}/removeSong")
    public String removeSongFromAlbum(@RequestParam("songId") Long songId,
                                 @PathVariable String username,
                                 @PathVariable Long albumId) {
        try {
            albumService.removeSongFromAlbum(songId, albumId);
            return "redirect:/albums/" + albumId;
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PostMapping("/albums/{albumId}/delete")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long albumId) {
        try {
            albumService.deleteAlbum(albumId);
            return ResponseEntity.ok("Album deleted successfully!");
        } catch (NotFoundException e) {
            logger.error("Album not found with ID: " + albumId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Album not found!");
        } catch (Exception e) {
            logger.error("Failed to delete song with ID: " + albumId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Album!");
        }
    }

    @GetMapping("/albums/{username}")
    public String showAllAlbumsByUser(@PathVariable String username, @AuthenticationPrincipal UserDetailsImpl userDetails, Model model, HttpServletRequest request) {
        Optional<UserDTO> optionalUser = userService.findByUserName(username);
        logger.info("Retrieving albums for user: " + username);

        if (optionalUser.isPresent()) {
            UserDTO userDTO = optionalUser.get();
            User user = userService.getCurrentUser(userDetails.getUserId());
            if (user.getArtist() != null) {
                ArtistDTO artistDTO = ArtistDTO.from(user.getArtist());
                List<AlbumDTO> albums = albumService.findAllAlbumsByArtist(artistDTO.getArtistId());

                model.addAttribute("user", userDTO);
                model.addAttribute("artist", artistDTO);
                model.addAttribute("albums", albums);
                return "music/all_artist_albums_page";
            } else {
                logger.warn("User: " + username + " is not an artist.");
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
                return "forward:/error";
            }
        } else {
            logger.error("User: " + username + " not found.");
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
            return "forward:/error";
        }
    }

}
