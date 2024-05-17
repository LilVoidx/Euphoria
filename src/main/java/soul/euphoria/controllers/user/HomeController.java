package soul.euphoria.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.dto.infos.UserDTO;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.file.FileStorageService;
import soul.euphoria.services.music.SongService;
import soul.euphoria.services.user.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private SongService songService;

    @GetMapping("/")
    public String showIndexPage() {
        return "/index_page";
    }

    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserDTO userDTO = userService.getUserById(userDetails.getUserId());
        if (userDTO != null) {
            // Convert User entity to UserDTO
            model.addAttribute("user", userDTO);

            // Fetch all songs
            List<SongDTO> allSongs = songService.getAllSongs();
            model.addAttribute("allSongs", allSongs);

            // Fetch trending song
            SongDTO trendingSong = songService.getTrendingSong();
            model.addAttribute("trendingSong", trendingSong);
            return "user_account/home_page";

        } else {
            // Redirect to error page
            return "redirect:/error";
        }
    }
    
    @GetMapping("/search")
    @ResponseBody
    public List<SongDTO> searchSongs(@RequestParam("query") String query) {
        return songService.search(query);
    }

    @GetMapping("/files/img/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        fileStorageService.writeFileToResponse(fileName, response);
    }
}
