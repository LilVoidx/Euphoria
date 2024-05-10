package soul.euphoria.controllers.music;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.Enum.Genre;
import soul.euphoria.models.music.Song;
import soul.euphoria.security.details.UserDetailsImpl;
import soul.euphoria.services.music.SongService;

@Controller
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping("/upload-song")
    public String showUploadSongForm(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("userId", userDetails.getUserId());
        model.addAttribute("genres", Genre.values());
        model.addAttribute("successMessage", "");
        model.addAttribute("errorMessage", "");
        model.addAttribute("songForm", new SongForm());
        return "music/music_upload_page";
    }


    @PostMapping("/upload-song")
    public String uploadSong(@ModelAttribute("songForm") SongForm songForm,
                             @RequestParam("songFile") MultipartFile songFile,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @RequestParam("userId") Long userId,
                             Model model) {
        try {
            songService.uploadSong(songForm, songFile, imageFile, userId);
            model.addAttribute("successMessage", "Song uploaded successfully!");
            model.addAttribute("userId", userId);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Failed to upload song: " + e.getMessage());
        }

        return "music/music_upload_page";
    }

    @GetMapping("/song/{songId}")
    public String showSongPage(@PathVariable("songId") Long songId, Model model) {
        Song song = songService.findById(songId);
        if (song == null) {
            // Handle song not found
            model.addAttribute("errorMessage", "Song not found");
            return "error"; // Assuming you have an error page
        }
        SongDTO songDTO = SongDTO.from(song);
        model.addAttribute("song", songDTO);
        return "music/song_page";
    }

    @GetMapping("/genres")
    public ResponseEntity<?> getAllGenres() {
        return ResponseEntity.ok(Genre.values());
    }
}
