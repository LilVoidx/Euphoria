package soul.euphoria.services.music;

import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;

import java.util.List;
import java.util.Optional;

public interface SongService {

    void uploadSong(SongForm songForm, MultipartFile songFile, MultipartFile imageFile, Long artistId);

    SongDTO favorite(Long userId, Long songId);

    SongDTO findById(Long songId);

    List<SongDTO> getSongsByArtist(Artist artist);

    List<SongDTO> getAllSongs();

    List<SongDTO> search(String query);

    Song getCurrentSong(Long songId);

    SongDTO getTrendingSong();

    void deleteSong(Long songId) throws NotFoundException;
}
