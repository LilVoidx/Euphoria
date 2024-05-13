package soul.euphoria.services.music;

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
}
