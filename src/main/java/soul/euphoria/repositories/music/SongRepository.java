package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soul.euphoria.dto.infos.SongDTO;
import soul.euphoria.models.music.Song;
import soul.euphoria.models.user.Artist;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findAllByArtist(Artist artist);
    @Query("SELECT s FROM Song s WHERE s.songId IN :ids")
    List<Song> getSongsByIds(List<Long> ids);

    @Query("SELECT s FROM Song s WHERE s.album.albumId = :albumId")
    List<SongDTO> findAllByAlbumId(Long albumId);
}

