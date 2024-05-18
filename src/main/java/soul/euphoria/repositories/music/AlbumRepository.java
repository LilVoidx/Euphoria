package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.music.Album;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistArtistId(Long artistId);
}
