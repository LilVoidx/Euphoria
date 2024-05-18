package soul.euphoria.services.music;

import javassist.NotFoundException;
import org.springframework.web.multipart.MultipartFile;
import soul.euphoria.dto.forms.AlbumForm;
import soul.euphoria.dto.infos.AlbumDTO;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.music.Album;

import java.util.List;

public interface AlbumService {
    Album createAlbum(AlbumForm albumForm, MultipartFile imageFile, Long userId);

    AlbumDTO getAlbumDetails(Long albumId);

    List<SongDTO> getAlbumSongs(Long albumId);

    void addSongToAlbum(Long songId, Long albumId);

    List<AlbumDTO> findAllAlbumsByArtist(Long artistId);

    void deleteAlbum(Long albumId) throws NotFoundException;

    void removeSongFromAlbum(Long songId, Long albumId);
}
