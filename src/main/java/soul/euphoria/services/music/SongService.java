package soul.euphoria.services.music;

import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.SongForm;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;

import java.util.List;

public interface SongService {

    void uploadSong(SongForm songForm, MultipartFile songFile, MultipartFile imageFile, Long artistId);

    SongDTO favorite(Long userId, Long songId);

    boolean isSongFavoritedByCurrentUser(Long songId, String currentUsername);

    SongDTO findById(Long songId);

    List<SongDTO> getSongsByArtist(Artist artist);

    List<SongDTO> getSongsByArtistAlbumNull(Artist artist);

    List<SongDTO> getAllSongs();

    List<SongDTO> search(String query);

    List<SongDTO> getAllUserFavorites(String username);

    Song getCurrentSong(Long songId);

    SongDTO getTrendingSong();

    void deleteSong(Long songId) throws NotFoundException;

}
