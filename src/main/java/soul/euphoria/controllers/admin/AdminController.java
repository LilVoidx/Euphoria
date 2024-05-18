package soul.euphoria.controllers.admin;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.user.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    @GetMapping("/users")
    public String getAllUsers(@RequestParam(defaultValue = "") String query,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserDTO userDTO = userService.getUserById(userDetails.getUserId());
        Page<UserDTO> usersPage = userService.searchUsers(query, page, size);
        model.addAttribute("user",userDTO);
        model.addAttribute("usersPage", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("query", query);
        return "admin/admin_dashboard_page";
    }


    @DeleteMapping("/users/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            userService.deleteUserByUsername(username);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user");
        }
    }

    @GetMapping("/songs")
    public String getAllSongs(@RequestParam(defaultValue = "") String query,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model) {
        UserDTO userDTO = userService.getUserById(userDetails.getUserId());
        Page<SongDTO> songsPage = songService.searchSongs(query, page, size);
        model.addAttribute("user", userDTO);
        model.addAttribute("songsPage", songsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", songsPage.getTotalPages());
        model.addAttribute("query", query);
        return "admin/songs_dashboard_page";
    }

    @GetMapping("/songs/{songId}/delete")
    public ResponseEntity<String> deleteSong(@PathVariable Long songId) {
        try {
            songService.deleteSong(songId);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Song not found");
        }
    }
}
