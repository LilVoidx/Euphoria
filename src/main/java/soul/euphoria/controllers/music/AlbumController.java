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
import javax.validation.ValidationException;
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
                              Model model,
                              HttpServletRequest request) {
        try {
            albumService.createAlbum(albumForm, coverImage, userId);
            UserDTO userDTO = userService.getUserById(userId);
            return "redirect:/albums/" + userDTO.getUsername();
        } catch (ValidationException e) {
            logger.error("Failed to create album due to validation error: {}", e.getMessage());
            model.addAttribute("error", "Invalid album data. Please check your input.");
            return "music/album_create_page";
        } catch (Exception e) {
            logger.error("Failed to create album", e);
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }

    @GetMapping("/albums/{username}/album/{albumId}")
    public String showAlbumDetails(@PathVariable Long albumId,
                                   @PathVariable String username,
                                   Model model,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails,
                                   HttpServletRequest request) {
        try {
            // Fetch album details
            AlbumDTO album = albumService.getAlbumDetails(albumId);
            if (album == null) {
                throw new NotFoundException("Album not found with ID: " + albumId);
            }

            // Fetch user details
            Optional<UserDTO> optionalUser = userService.findByUserName(username);
            if (optionalUser.isEmpty()) {
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                return "forward:/error";
            }

            // Fetch current user
            User user = userService.getCurrentUser(userDetails.getUserId());
            if (user.getArtist() == null) {
                request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
                return "forward:/error";
            }

            // Fetch album songs and artist's songs not associated with any album
            UserDTO userDTO = optionalUser.get();
            List<SongDTO> albumSongs = albumService.getAlbumSongs(albumId);
            List<SongDTO> artistSongDTOs = songService.getSongsByArtistAlbumNull(user.getArtist());

            // Populate model attributes
            model.addAttribute("user", userDTO);
            model.addAttribute("album", album);
            model.addAttribute("albumSongs", albumSongs);
            model.addAttribute("artistSongDTOs", artistSongDTOs);

            return "music/album_page";
        } catch (NotFoundException e) {
            logger.error("NotFoundException: ", e);
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 404);
            return "forward:/error";
        } catch (Exception e) {
            logger.error("Exception: ", e);
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, 500);
            return "forward:/error";
        }
    }


    @PostMapping("/albums/{username}/album/{albumId}/addSong")
    public String addSongToAlbum(@RequestParam("songId") Long songId,
                                 @PathVariable String username,
                                 @PathVariable Long albumId,
                                 HttpServletRequest request) {
        try {
            albumService.addSongToAlbum(songId, albumId);
            return "redirect:/albums/" + albumId;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to add song to album: {}", e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());
            return "forward:/error";
        } catch (Exception e) {
            logger.error("Failed to add song to album: {}", e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "forward:/error";
        }
    }

    @PostMapping("/albums/{username}/album/{albumId}/removeSong")
    public String removeSongFromAlbum(@RequestParam("songId") Long songId,
                                      @PathVariable String username,
                                      @PathVariable Long albumId,
                                      HttpServletRequest request) {
        try {
            albumService.removeSongFromAlbum(songId, albumId);
            return "redirect:/albums/" + albumId;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to remove song from album: {}", e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());
            return "forward:/error";
        } catch (Exception e) {
            logger.error("Failed to remove song from album: {}", e.getMessage());
            request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "forward:/error";
        }
    }

    @DeleteMapping("/albums/{albumId}/delete")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long albumId) {
        try {
            albumService.deleteAlbum(albumId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            logger.error("Album not found with ID: " + albumId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Album not found!");
        } catch (Exception e) {
            logger.error("Failed to delete album with ID: " + albumId, e);
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
