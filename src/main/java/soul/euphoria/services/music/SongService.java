package soul.euphoria.services.music;

import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.models.music.Song;

public interface SongService {

    void uploadSong(SongForm songForm, MultipartFile songFile, MultipartFile imageFile, Long artistId);

    Song findById(Long songId);
}
