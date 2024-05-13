package soul.euphoria.repositories.music;

import org.springframework.data.jpa.repository.JpaRepository;
import soul.euphoria.models.music.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}
